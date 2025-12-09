package com.example.warframeapp20.util

import android.os.Handler
import android.os.Looper
import java.text.SimpleDateFormat
import java.util.*

object CountdownTimer {
    
    private val handler = Handler(Looper.getMainLooper())
    private val activeTimers = mutableMapOf<String, Runnable>()
    
    /**
     * Start a countdown timer that updates a callback with formatted time remaining
     */
    fun startCountdown(
        id: String,
        expiryTime: String,
        onUpdate: (String) -> Unit,
        onExpired: (() -> Unit)? = null
    ) {
        // Stop existing timer with same ID
        stopCountdown(id)
        
        val runnable = object : Runnable {
            override fun run() {
                val timeRemaining = calculateTimeRemaining(expiryTime)
                
                if (timeRemaining <= 0) {
                    onUpdate("Expired")
                    onExpired?.invoke()
                    activeTimers.remove(id)
                } else {
                    onUpdate(formatDuration(timeRemaining))
                    handler.postDelayed(this, 1000) // Update every second
                }
            }
        }
        
        activeTimers[id] = runnable
        handler.post(runnable)
    }
    
    /**
     * Stop a specific countdown timer
     */
    fun stopCountdown(id: String) {
        activeTimers[id]?.let { runnable ->
            handler.removeCallbacks(runnable)
            activeTimers.remove(id)
        }
    }
    
    /**
     * Stop all countdown timers
     */
    fun stopAllCountdowns() {
        activeTimers.values.forEach { runnable ->
            handler.removeCallbacks(runnable)
        }
        activeTimers.clear()
    }
    
    /**
     * Calculate time remaining in milliseconds from ISO 8601 timestamp
     */
    private fun calculateTimeRemaining(expiryTime: String): Long {
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            format.timeZone = TimeZone.getTimeZone("UTC")
            val expiryDate = format.parse(expiryTime)
            val currentTime = System.currentTimeMillis()
            (expiryDate?.time ?: 0) - currentTime
        } catch (e: Exception) {
            0L
        }
    }
    
    /**
     * Format duration in milliseconds to human readable string
     */
    private fun formatDuration(milliseconds: Long): String {
        val seconds = milliseconds / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        
        return when {
            days > 0 -> "${days}d ${hours % 24}h"
            hours > 0 -> "${hours}h ${minutes % 60}m"
            minutes > 0 -> "${minutes}m ${seconds % 60}s"
            seconds > 0 -> "${seconds}s"
            else -> "Expired"
        }
    }
}