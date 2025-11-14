package com.example.warframeapp20.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

/**
 * Service to fetch real-time Warframe world state data
 */
class WorldStateService {
    
    companion object {
        private const val BASE_URL = "https://api.warframestat.us"
        private const val PC_ENDPOINT = "$BASE_URL/pc"
        
        // API endpoints
        private const val ALERTS_ENDPOINT = "$PC_ENDPOINT/alerts"
        private const val INVASIONS_ENDPOINT = "$PC_ENDPOINT/invasions"
        private const val FISSURES_ENDPOINT = "$PC_ENDPOINT/fissures"
        private const val SORTIE_ENDPOINT = "$PC_ENDPOINT/sortie"
        private const val NIGHTWAVE_ENDPOINT = "$PC_ENDPOINT/nightwave"
        private const val CETUS_ENDPOINT = "$PC_ENDPOINT/cetusCycle"
        private const val CAMBION_ENDPOINT = "$PC_ENDPOINT/cambionCycle"
        private const val EVENTS_ENDPOINT = "$PC_ENDPOINT/events"
    }
    
    /**
     * Fetch current alerts
     */
    suspend fun getAlerts(): List<Alert> = withContext(Dispatchers.IO) {
        try {
            val json = fetchJsonFromUrl(ALERTS_ENDPOINT)
            parseAlerts(JSONArray(json))
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Fetch current invasions
     */
    suspend fun getInvasions(): List<Invasion> = withContext(Dispatchers.IO) {
        try {
            val json = fetchJsonFromUrl(INVASIONS_ENDPOINT)
            parseInvasions(JSONArray(json))
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Fetch current void fissures
     */
    suspend fun getFissures(): List<Fissure> = withContext(Dispatchers.IO) {
        try {
            val json = fetchJsonFromUrl(FISSURES_ENDPOINT)
            parseFissures(JSONArray(json))
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Fetch current sortie
     */
    suspend fun getSortie(): Sortie? = withContext(Dispatchers.IO) {
        try {
            val json = fetchJsonFromUrl(SORTIE_ENDPOINT)
            parseSortie(JSONObject(json))
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * Fetch Cetus cycle information
     */
    suspend fun getCetusCycle(): CetusCycle? = withContext(Dispatchers.IO) {
        try {
            val json = fetchJsonFromUrl(CETUS_ENDPOINT)
            parseCetusCycle(JSONObject(json))
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * Fetch Cambion Drift cycle
     */
    suspend fun getCambionCycle(): CambionCycle? = withContext(Dispatchers.IO) {
        try {
            val json = fetchJsonFromUrl(CAMBION_ENDPOINT)
            parseCambionCycle(JSONObject(json))
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * Fetch current events
     */
    suspend fun getEvents(): List<Event> = withContext(Dispatchers.IO) {
        try {
            val json = fetchJsonFromUrl(EVENTS_ENDPOINT)
            parseEvents(JSONArray(json))
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    private fun fetchJsonFromUrl(urlString: String): String {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        
        return try {
            connection.requestMethod = "GET"
            connection.setRequestProperty("User-Agent", "WarframeApp/1.0")
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            
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
    
    private fun parseAlerts(jsonArray: JSONArray): List<Alert> {
        val alerts = mutableListOf<Alert>()
        
        for (i in 0 until jsonArray.length()) {
            val alertJson = jsonArray.getJSONObject(i)
            
            val mission = Mission(
                node = alertJson.getString("node"),
                type = alertJson.getString("type"),
                faction = alertJson.getString("faction"),
                minEnemyLevel = alertJson.getInt("minEnemyLevel"),
                maxEnemyLevel = alertJson.getInt("maxEnemyLevel")
            )
            
            val reward = Reward(
                items = parseStringArray(alertJson.optJSONArray("rewardTypes")),
                credits = alertJson.optInt("credits", 0),
                asString = alertJson.optString("reward", "")
            )
            
            alerts.add(Alert(
                id = alertJson.getString("id"),
                mission = mission,
                reward = reward,
                expiry = alertJson.getString("expiry"),
                eta = alertJson.getString("eta"),
                isExpired = alertJson.optBoolean("expired", false)
            ))
        }
        
        return alerts
    }
    
    private fun parseInvasions(jsonArray: JSONArray): List<Invasion> {
        val invasions = mutableListOf<Invasion>()
        
        for (i in 0 until jsonArray.length()) {
            val invasionJson = jsonArray.getJSONObject(i)
            
            val attackerReward = Reward(
                items = parseStringArray(invasionJson.optJSONArray("attackerReward")),
                asString = invasionJson.optString("attackerReward", "")
            )
            
            val defenderReward = Reward(
                items = parseStringArray(invasionJson.optJSONArray("defenderReward")),
                asString = invasionJson.optString("defenderReward", "")
            )
            
            invasions.add(Invasion(
                id = invasionJson.getString("id"),
                node = invasionJson.getString("node"),
                attackerReward = attackerReward,
                defenderReward = defenderReward,
                completion = (invasionJson.optDouble("completion", 0.0) * 100).toFloat(),
                eta = invasionJson.optString("eta", ""),
                vsInfestation = invasionJson.optBoolean("vsInfestation", false)
            ))
        }
        
        return invasions
    }
    
    private fun parseFissures(jsonArray: JSONArray): List<Fissure> {
        val fissures = mutableListOf<Fissure>()
        
        for (i in 0 until jsonArray.length()) {
            val fissureJson = jsonArray.getJSONObject(i)
            
            fissures.add(Fissure(
                id = fissureJson.getString("id"),
                node = fissureJson.getString("node"),
                missionType = fissureJson.getString("missionType"),
                enemy = fissureJson.getString("enemy"),
                tier = fissureJson.getString("tier"),
                tierNum = fissureJson.getInt("tierNum"),
                activation = fissureJson.getString("activation"),
                expiry = fissureJson.getString("expiry"),
                eta = fissureJson.getString("eta"),
                isHard = fissureJson.optBoolean("isHard", false),
                isStorm = fissureJson.optBoolean("isStorm", false)
            ))
        }
        
        return fissures
    }
    
    private fun parseSortie(jsonObject: JSONObject): Sortie {
        val missionsArray = jsonObject.getJSONArray("variants")
        val missions = mutableListOf<SortieMission>()
        
        for (i in 0 until missionsArray.length()) {
            val missionJson = missionsArray.getJSONObject(i)
            
            missions.add(SortieMission(
                node = missionJson.getString("node"),
                missionType = missionJson.getString("missionType"),
                modifier = missionJson.getString("modifier"),
                modifierDescription = missionJson.getString("modifierDescription")
            ))
        }
        
        return Sortie(
            id = jsonObject.getString("id"),
            boss = jsonObject.getString("boss"),
            faction = jsonObject.getString("faction"),
            missions = missions,
            expiry = jsonObject.getString("expiry"),
            eta = jsonObject.getString("eta")
        )
    }
    
    private fun parseCetusCycle(jsonObject: JSONObject): CetusCycle {
        return CetusCycle(
            isDay = jsonObject.getBoolean("isDay"),
            timeLeft = jsonObject.getString("timeLeft"),
            expiry = jsonObject.getString("expiry")
        )
    }
    
    private fun parseCambionCycle(jsonObject: JSONObject): CambionCycle {
        return CambionCycle(
            active = jsonObject.getString("active"),
            timeLeft = jsonObject.getString("timeLeft"),
            expiry = jsonObject.getString("expiry")
        )
    }
    
    private fun parseEvents(jsonArray: JSONArray): List<Event> {
        val events = mutableListOf<Event>()
        
        for (i in 0 until jsonArray.length()) {
            val eventJson = jsonArray.getJSONObject(i)
            
            val rewards = mutableListOf<Reward>()
            val rewardsArray = eventJson.optJSONArray("rewards")
            if (rewardsArray != null) {
                for (j in 0 until rewardsArray.length()) {
                    val rewardJson = rewardsArray.getJSONObject(j)
                    rewards.add(Reward(
                        items = parseStringArray(rewardJson.optJSONArray("items")),
                        credits = rewardJson.optInt("credits", 0),
                        asString = rewardJson.optString("asString", "")
                    ))
                }
            }
            
            events.add(Event(
                id = eventJson.getString("id"),
                description = eventJson.getString("description"),
                tooltip = eventJson.optString("tooltip", ""),
                node = eventJson.optString("node", ""),
                rewards = rewards,
                health = eventJson.optString("health", ""),
                expiry = eventJson.optString("expiry", ""),
                maximumScore = eventJson.optInt("maximumScore"),
                currentScore = eventJson.optInt("currentScore"),
                isActive = !eventJson.optBoolean("expired", false)
            ))
        }
        
        return events
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