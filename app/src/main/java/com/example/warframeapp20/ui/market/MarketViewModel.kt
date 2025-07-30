package com.example.warframeapp20.ui.market

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warframeapp20.data.*
import kotlinx.coroutines.launch

class MarketViewModel : ViewModel() {

    private val marketService = MarketService()
    
    private val _marketItems = MutableLiveData<List<MarketItem>>()
    val marketItems: LiveData<List<MarketItem>> = _marketItems

    private val _selectedItemDetails = MutableLiveData<ItemDetails?>()
    val selectedItemDetails: LiveData<ItemDetails?> = _selectedItemDetails

    private val _trendingItems = MutableLiveData<List<TrendingItem>>()
    val trendingItems: LiveData<List<TrendingItem>> = _trendingItems

    private val _priceAlerts = MutableLiveData<List<PriceAlert>>()
    val priceAlerts: LiveData<List<PriceAlert>> = _priceAlerts

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadPopularItems()
        loadTrendingItems()
        loadPriceAlerts()
    }

    fun searchItems(query: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                val results = marketService.searchItems(query)
                _marketItems.value = results
            } catch (e: Exception) {
                _error.value = "Failed to search items: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadPopularItems() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                val items = marketService.getMarketItems()
                // Filter to popular items for better performance
                _marketItems.value = items.filter { item ->
                    MarketService.POPULAR_ITEMS.any { popular ->
                        item.urlName.contains(popular, ignoreCase = true)
                    }
                }.take(20)
            } catch (e: Exception) {
                _error.value = "Failed to load market items: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadItemDetails(itemUrlName: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                
                val orders = marketService.getItemOrders(itemUrlName)
                val statistics = marketService.getItemStatistics(itemUrlName)
                val quickPrice = marketService.getQuickPrice(itemUrlName)
                
                val details = ItemDetails(
                    itemName = itemUrlName,
                    orders = orders,
                    statistics = statistics,
                    quickPrice = quickPrice,
                    bestSellOrders = orders.filter { it.orderType == "sell" && it.user.status != "offline" }
                        .sortedBy { it.platinum }.take(5),
                    bestBuyOrders = orders.filter { it.orderType == "buy" && it.user.status != "offline" }
                        .sortedByDescending { it.platinum }.take(5)
                )
                
                _selectedItemDetails.value = details
            } catch (e: Exception) {
                _error.value = "Failed to load item details: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadTrendingItems() {
        viewModelScope.launch {
            try {
                val trending = marketService.getTrendingItems(10)
                _trendingItems.value = trending
            } catch (e: Exception) {
                // Silently fail for trending items
            }
        }
    }

    private fun loadPriceAlerts() {
        // Load from local storage
        _priceAlerts.value = getSavedPriceAlerts()
    }

    fun addPriceAlert(itemName: String, targetPrice: Int, alertType: AlertType) {
        val currentAlerts = _priceAlerts.value?.toMutableList() ?: mutableListOf()
        val newAlert = PriceAlert(
            id = generateAlertId(),
            itemName = itemName,
            targetPrice = targetPrice,
            alertType = alertType,
            isActive = true,
            createdAt = System.currentTimeMillis()
        )
        currentAlerts.add(newAlert)
        _priceAlerts.value = currentAlerts
        savePriceAlerts(currentAlerts)
    }

    fun removeAlert(alert: PriceAlert) {
        val currentAlerts = _priceAlerts.value?.toMutableList() ?: mutableListOf()
        currentAlerts.remove(alert)
        _priceAlerts.value = currentAlerts
        savePriceAlerts(currentAlerts)
    }

    fun refreshData() {
        if (_marketItems.value.isNullOrEmpty()) {
            loadPopularItems()
        }
        loadTrendingItems()
        checkPriceAlerts()
    }

    private fun checkPriceAlerts() {
        viewModelScope.launch {
            val alerts = _priceAlerts.value?.filter { it.isActive } ?: return@launch
            
            for (alert in alerts) {
                try {
                    val quickPrice = marketService.getQuickPrice(alert.itemName)
                    if (quickPrice != null) {
                        val triggered = when (alert.alertType) {
                            AlertType.PRICE_DROP -> quickPrice.lowestSell != null && quickPrice.lowestSell <= alert.targetPrice
                            AlertType.PRICE_RISE -> quickPrice.highestBuy != null && quickPrice.highestBuy >= alert.targetPrice
                        }
                        
                        if (triggered) {
                            // Show notification
                            showPriceAlertNotification(alert, quickPrice)
                        }
                    }
                } catch (e: Exception) {
                    // Continue checking other alerts
                }
            }
        }
    }

    private fun showPriceAlertNotification(alert: PriceAlert, price: QuickPrice) {
        // This would typically trigger a system notification
        // For now, just update the alert as triggered
        val currentAlerts = _priceAlerts.value?.toMutableList() ?: mutableListOf()
        val index = currentAlerts.indexOfFirst { it.id == alert.id }
        if (index >= 0) {
            currentAlerts[index] = alert.copy(isActive = false, triggeredAt = System.currentTimeMillis())
            _priceAlerts.value = currentAlerts
            savePriceAlerts(currentAlerts)
        }
    }

    private fun getSavedPriceAlerts(): List<PriceAlert> {
        // Load from SharedPreferences or Room database
        return emptyList()
    }

    private fun savePriceAlerts(alerts: List<PriceAlert>) {
        // Save to SharedPreferences or Room database
    }

    private fun generateAlertId(): String {
        return "alert_${System.currentTimeMillis()}"
    }

    override fun onCleared() {
        super.onCleared()
        _selectedItemDetails.value = null
    }
}

// Data classes moved to WarframeData.kt