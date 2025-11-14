package com.example.warframeapp20.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Service for Warframe.Market API integration
 * Provides market data, price tracking, and trading tools
 */
class MarketService {
    
    companion object {
        private const val BASE_URL = "https://api.warframe.market/v1"
        private const val ITEMS_ENDPOINT = "$BASE_URL/items"
        private const val ORDERS_ENDPOINT = "$BASE_URL/items/%s/orders"
        private const val STATISTICS_ENDPOINT = "$BASE_URL/items/%s/statistics"
        
        // Popular items that players search for most
        val POPULAR_ITEMS = listOf(
            "prime_resurgence_pack", "mesa_prime_set", "rhino_prime_set",
            "soma_prime_set", "valkyr_prime_set", "nova_prime_set",
            "frost_prime_set", "mag_prime_set", "ember_prime_set"
        )
    }
    
    /**
     * Get all available market items
     */
    suspend fun getMarketItems(): List<MarketItem> = withContext(Dispatchers.IO) {
        try {
            val json = fetchJsonFromUrl(ITEMS_ENDPOINT)
            parseMarketItems(JSONObject(json))
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Get current orders for a specific item
     */
    suspend fun getItemOrders(itemUrlName: String, includeOffline: Boolean = false): List<MarketOrder> = withContext(Dispatchers.IO) {
        try {
            val endpoint = String.format(ORDERS_ENDPOINT, itemUrlName)
            val json = fetchJsonFromUrl(endpoint)
            parseMarketOrders(JSONObject(json), includeOffline)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Get price statistics for an item
     */
    suspend fun getItemStatistics(itemUrlName: String): MarketStatistics? = withContext(Dispatchers.IO) {
        try {
            val endpoint = String.format(STATISTICS_ENDPOINT, itemUrlName)
            val json = fetchJsonFromUrl(endpoint)
            parseMarketStatistics(JSONObject(json))
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * Get quick price summary for an item
     */
    suspend fun getQuickPrice(itemUrlName: String): QuickPrice? = withContext(Dispatchers.IO) {
        try {
            val orders = getItemOrders(itemUrlName, false)
            if (orders.isEmpty()) return@withContext null
            
            val sellOrders = orders.filter { it.orderType == "sell" && it.user.status != "offline" }
                .sortedBy { it.platinum }
            val buyOrders = orders.filter { it.orderType == "buy" && it.user.status != "offline" }
                .sortedByDescending { it.platinum }
            
            QuickPrice(
                itemName = itemUrlName,
                lowestSell = sellOrders.firstOrNull()?.platinum,
                highestBuy = buyOrders.firstOrNull()?.platinum,
                averageSell = if (sellOrders.isNotEmpty()) {
                    sellOrders.take(5).map { it.platinum }.average().toInt()
                } else null,
                averageBuy = if (buyOrders.isNotEmpty()) {
                    buyOrders.take(5).map { it.platinum }.average().toInt()
                } else null,
                lastUpdated = System.currentTimeMillis()
            )
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * Search for items by name
     */
    suspend fun searchItems(query: String): List<MarketItem> = withContext(Dispatchers.IO) {
        try {
            val allItems = getMarketItems()
            allItems.filter { 
                it.itemName.contains(query, ignoreCase = true) ||
                it.urlName.contains(query, ignoreCase = true)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Get trending items based on price movement
     */
    suspend fun getTrendingItems(limit: Int = 10): List<TrendingItem> = withContext(Dispatchers.IO) {
        try {
            val items = mutableListOf<TrendingItem>()
            for (itemName in POPULAR_ITEMS.take(limit)) {
                val stats = getItemStatistics(itemName)
                val currentPrice = getQuickPrice(itemName)
                
                if (stats != null && currentPrice != null) {
                    val recent = stats.closed48Hours.lastOrNull()
                    val older = stats.closed90Days.takeLast(7).firstOrNull()
                    
                    if (recent != null && older != null) {
                        val priceChange = ((recent.avgPrice - older.avgPrice) / older.avgPrice) * 100
                        items.add(
                            TrendingItem(
                                itemName = itemName,
                                currentPrice = currentPrice.lowestSell ?: 0,
                                priceChange = priceChange,
                                volume = recent.volume
                            )
                        )
                    }
                }
            }
            items.sortedByDescending { kotlin.math.abs(it.priceChange) }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    private fun fetchJsonFromUrl(urlString: String): String {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        
        return try {
            connection.requestMethod = "GET"
            connection.setRequestProperty("User-Agent", "WarframeCompanionApp/1.0")
            connection.setRequestProperty("Platform", "android")
            connection.connectTimeout = 15000
            connection.readTimeout = 15000
            
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuilder()
                var line: String?
                
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                reader.close()
                response.toString()
            } else {
                ""
            }
        } finally {
            connection.disconnect()
        }
    }
    
    private fun parseMarketItems(jsonObject: JSONObject): List<MarketItem> {
        val items = mutableListOf<MarketItem>()
        val payload = jsonObject.getJSONObject("payload")
        val itemsArray = payload.getJSONArray("items")
        
        for (i in 0 until itemsArray.length()) {
            val itemJson = itemsArray.getJSONObject(i)
            
            val subItems = mutableListOf<MarketSubItem>()
            if (itemJson.has("sub_items")) {
                val subItemsArray = itemJson.getJSONArray("sub_items")
                for (j in 0 until subItemsArray.length()) {
                    val subItemJson = subItemsArray.getJSONObject(j)
                    subItems.add(
                        MarketSubItem(
                            id = subItemJson.getString("id"),
                            urlName = subItemJson.getString("url_name"),
                            itemName = subItemJson.getString("item_name"),
                            ducats = subItemJson.optInt("ducats"),
                            tags = parseStringArray(subItemJson.optJSONArray("tags"))
                        )
                    )
                }
            }
            
            items.add(
                MarketItem(
                    id = itemJson.getString("id"),
                    urlName = itemJson.getString("url_name"),
                    itemName = itemJson.getString("item_name"),
                    thumb = itemJson.getString("thumb"),
                    subItems = subItems
                )
            )
        }
        
        return items
    }
    
    private fun parseMarketOrders(jsonObject: JSONObject, includeOffline: Boolean): List<MarketOrder> {
        val orders = mutableListOf<MarketOrder>()
        val payload = jsonObject.getJSONObject("payload")
        val ordersArray = payload.getJSONArray("orders")
        
        for (i in 0 until ordersArray.length()) {
            val orderJson = ordersArray.getJSONObject(i)
            val userJson = orderJson.getJSONObject("user")
            
            val user = MarketUser(
                id = userJson.getString("id"),
                ingameName = userJson.getString("ingame_name"),
                status = userJson.getString("status"),
                region = userJson.getString("region"),
                reputation = userJson.getInt("reputation"),
                avatar = userJson.optString("avatar")
            )
            
            // Skip offline users unless specifically requested
            if (!includeOffline && user.status == "offline") continue
            
            orders.add(
                MarketOrder(
                    id = orderJson.getString("id"),
                    platinum = orderJson.getInt("platinum"),
                    quantity = orderJson.getInt("quantity"),
                    orderType = orderJson.getString("order_type"),
                    platform = orderJson.getString("platform"),
                    region = orderJson.getString("region"),
                    user = user,
                    visible = orderJson.getBoolean("visible"),
                    lastSeen = orderJson.getString("last_seen"),
                    creationDate = orderJson.getString("creation_date")
                )
            )
        }
        
        return orders
    }
    
    private fun parseMarketStatistics(jsonObject: JSONObject): MarketStatistics {
        val payload = jsonObject.getJSONObject("payload")
        val statistics48h = payload.getJSONArray("statistics_closed").getJSONObject(0)
        val statistics90d = payload.getJSONArray("statistics_closed").getJSONObject(1)
        
        return MarketStatistics(
            closed90Days = parseClosedOrders(statistics90d.getJSONArray("closed")),
            closed48Hours = parseClosedOrders(statistics48h.getJSONArray("closed"))
        )
    }
    
    private fun parseClosedOrders(jsonArray: JSONArray): List<MarketClosedOrder> {
        val orders = mutableListOf<MarketClosedOrder>()
        
        for (i in 0 until jsonArray.length()) {
            val orderJson = jsonArray.getJSONObject(i)
            orders.add(
                MarketClosedOrder(
                    datetime = orderJson.getString("datetime"),
                    volume = orderJson.getInt("volume"),
                    minPrice = orderJson.getInt("min_price"),
                    maxPrice = orderJson.getInt("max_price"),
                    avgPrice = orderJson.getDouble("avg_price"),
                    waPrice = orderJson.getDouble("wa_price"),
                    median = orderJson.getDouble("median")
                )
            )
        }
        
        return orders
    }
    
    private fun parseStringArray(jsonArray: JSONArray?): List<String> {
        if (jsonArray == null) return emptyList()
        
        val list = mutableListOf<String>()
        for (i in 0 until jsonArray.length()) {
            list.add(jsonArray.getString(i))
        }
        return list
    }
}

// Data classes moved to WarframeData.kt