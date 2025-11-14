package com.example.warframeapp20.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.warframeapp20.R

/**
 * Manages Warframe themes throughout the application
 */
class ThemeManager(private val context: Context) {

    companion object {
        const val PREF_THEME = "pref_theme"
        const val THEME_DEFAULT = "default"
        const val THEME_RHINO_PRIME = "rhino_prime"
        const val THEME_EXCALIBUR_UMBRA = "excalibur_umbra"
        const val THEME_WISP = "wisp"
        const val THEME_MESA_PRIME = "mesa_prime"
        const val THEME_SARYN_PRIME = "saryn_prime"
        const val THEME_NOVA_PRIME = "nova_prime"
        const val THEME_FROST_PRIME = "frost_prime"

        private var instance: ThemeManager? = null

        fun getInstance(context: Context): ThemeManager {
            if (instance == null) {
                instance = ThemeManager(context.applicationContext)
            }
            return instance!!
        }
    }

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    /**
     * Get the current theme name from preferences
     */
    fun getCurrentTheme(): String {
        return prefs.getString(PREF_THEME, THEME_DEFAULT) ?: THEME_DEFAULT
    }

    /**
     * Set the theme name in preferences
     */
    fun setTheme(themeName: String) {
        prefs.edit().putString(PREF_THEME, themeName).apply()
    }

    /**
     * Apply the selected theme to an activity
     */
    fun applyTheme(activity: Activity) {
        when (getCurrentTheme()) {
            THEME_RHINO_PRIME -> activity.setTheme(R.style.Theme_WarframeApp_RhinoPrime)
            THEME_EXCALIBUR_UMBRA -> activity.setTheme(R.style.Theme_WarframeApp_ExcaliburUmbra)
            THEME_WISP -> activity.setTheme(R.style.Theme_WarframeApp_Wisp)
            THEME_MESA_PRIME -> activity.setTheme(R.style.Theme_WarframeApp_MesaPrime)
            THEME_SARYN_PRIME -> activity.setTheme(R.style.Theme_WarframeApp_SarynPrime)
            THEME_NOVA_PRIME -> activity.setTheme(R.style.Theme_WarframeApp_NovaPrime)
            THEME_FROST_PRIME -> activity.setTheme(R.style.Theme_WarframeApp_FrostPrime)
            else -> activity.setTheme(R.style.Theme_WarframeApp)
        }
    }

    /**
     * Get theme title from the theme name
     */
    fun getThemeTitle(themeName: String): String {
        return when (themeName) {
            THEME_RHINO_PRIME -> "Rhino Prime"
            THEME_EXCALIBUR_UMBRA -> "Excalibur Umbra"
            THEME_WISP -> "Wisp"
            THEME_MESA_PRIME -> "Mesa Prime"
            THEME_SARYN_PRIME -> "Saryn Prime"
            THEME_NOVA_PRIME -> "Nova Prime"
            THEME_FROST_PRIME -> "Frost Prime"
            else -> "Default"
        }
    }

    /**
     * Get available themes as a map of theme name to display name
     */
    fun getAvailableThemes(): Map<String, String> {
        return mapOf(
            THEME_DEFAULT to "Default",
            THEME_RHINO_PRIME to "Rhino Prime",
            THEME_EXCALIBUR_UMBRA to "Excalibur Umbra",
            THEME_WISP to "Wisp",
            THEME_MESA_PRIME to "Mesa Prime",
            THEME_SARYN_PRIME to "Saryn Prime",
            THEME_NOVA_PRIME to "Nova Prime",
            THEME_FROST_PRIME to "Frost Prime"
        )
    }
}
