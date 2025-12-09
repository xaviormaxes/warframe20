package com.example.warframeapp20

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.warframeapp20.R
import com.example.warframeapp20.databinding.ActivityMainBinding
import com.example.warframeapp20.ui.theme.ThemeDialogFragment
import com.example.warframeapp20.util.ThemeManager
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, ThemeDialogFragment.ThemeDialogListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply theme before setting content view
        ThemeManager.getInstance(this).applyTheme(this)
        
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up toolbar
        setSupportActionBar(binding.toolbar)

        // Set up drawer layout
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()

        // Set up navigation after fragment is ready
        binding.root.post {
            try {
                val navController = findNavController(R.id.nav_host_fragment)
                
                // Set up app bar configuration
                appBarConfiguration = AppBarConfiguration(
                    setOf(
                        R.id.nav_dashboard, R.id.nav_fissures, R.id.nav_invasions,
                        R.id.nav_sortie, R.id.nav_events, R.id.nav_market, R.id.nav_builds,
                        R.id.nav_resources, R.id.nav_foundry, R.id.nav_clan, R.id.nav_rivens
                    ),
                    binding.drawerLayout
                )
                
                setupActionBarWithNavController(navController, appBarConfiguration)
                binding.navView?.setupWithNavController(navController)
                binding.bottomNavView?.setupWithNavController(navController)
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle navigation setup failure gracefully
            }
        }

        // Set up navigation drawer
        binding.navView?.setNavigationItemSelectedListener(this)


        // Set up back press handling
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
                    binding.drawerLayout?.closeDrawer(GravityCompat.START)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return try {
            val navController = findNavController(R.id.nav_host_fragment)
            navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
        } catch (e: Exception) {
            super.onSupportNavigateUp()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks
        return try {
            val navController = findNavController(R.id.nav_host_fragment)
            
            when (item.itemId) {
                R.id.nav_dashboard, R.id.nav_fissures, R.id.nav_invasions,
                R.id.nav_sortie, R.id.nav_events, R.id.nav_arsenal,
                R.id.nav_warframes, R.id.nav_weapons, R.id.nav_market,
                R.id.nav_builds, R.id.nav_resources, R.id.nav_foundry,
                R.id.nav_clan, R.id.nav_rivens, R.id.nav_settings,
                R.id.nav_themes, R.id.nav_about -> {
                    navController.navigate(item.itemId)
                    binding.drawerLayout?.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        } catch (e: Exception) {
            binding.drawerLayout?.closeDrawer(GravityCompat.START)
            false
        }
    }

    
    private fun showThemeDialog() {
        val themeDialog = ThemeDialogFragment.newInstance()
        themeDialog.setThemeDialogListener(this)
        themeDialog.show(supportFragmentManager, ThemeDialogFragment.TAG)
    }
    
    override fun onThemeSelected(themeName: String) {
        val themeManager = ThemeManager.getInstance(this)
        themeManager.setTheme(themeName)
        
        // Recreate activity to apply new theme
        recreate()
    }
}