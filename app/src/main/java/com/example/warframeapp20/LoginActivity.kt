package com.example.warframeapp20

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.warframeapp20.databinding.ActivityLoginBinding
import com.example.warframeapp20.util.ThemeManager
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    private var animationJob: Job? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.getInstance(this).applyTheme(this)
        super.onCreate(savedInstanceState)
        
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupFullscreenExperience()
        setupAnimations()
        setupClickListeners()
    }
    
    private fun setupFullscreenExperience() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, binding.root)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
    
    private fun setupAnimations() {
        // Initial setup - make elements invisible
        binding.apply {
            warframeLogo.alpha = 0f
            loginContainer.alpha = 0f
            backgroundGlow.alpha = 0f
            particleEffect1.alpha = 0f
            particleEffect2.alpha = 0f
            
            // Start entrance animations
            animateEntranceSequence()
        }
    }
    
    private fun animateEntranceSequence() {
        animationJob = CoroutineScope(Dispatchers.Main).launch {
            // Background glow fade in
            ObjectAnimator.ofFloat(binding.backgroundGlow, "alpha", 0f, 0.3f).apply {
                duration = 1500
                interpolator = DecelerateInterpolator()
                start()
            }
            
            delay(300)
            
            // Logo fade in with scale
            binding.warframeLogo.apply {
                scaleX = 0.8f
                scaleY = 0.8f
                ObjectAnimator.ofFloat(this, "alpha", 0f, 1f).apply {
                    duration = 1000
                    interpolator = DecelerateInterpolator()
                    start()
                }
                ObjectAnimator.ofFloat(this, "scaleX", 0.8f, 1f).apply {
                    duration = 1000
                    interpolator = DecelerateInterpolator()
                    start()
                }
                ObjectAnimator.ofFloat(this, "scaleY", 0.8f, 1f).apply {
                    duration = 1000
                    interpolator = DecelerateInterpolator()
                    start()
                }
            }
            
            delay(500)
            
            // Login container slide up
            binding.loginContainer.apply {
                translationY = 100f
                ObjectAnimator.ofFloat(this, "alpha", 0f, 1f).apply {
                    duration = 800
                    interpolator = DecelerateInterpolator()
                    start()
                }
                ObjectAnimator.ofFloat(this, "translationY", 100f, 0f).apply {
                    duration = 800
                    interpolator = DecelerateInterpolator()
                    start()
                }
            }
            
            delay(200)
            
            // Particle effects
            ObjectAnimator.ofFloat(binding.particleEffect1, "alpha", 0f, 0.4f).apply {
                duration = 1200
                interpolator = DecelerateInterpolator()
                start()
            }
            
            delay(300)
            
            ObjectAnimator.ofFloat(binding.particleEffect2, "alpha", 0f, 0.2f).apply {
                duration = 1000
                interpolator = DecelerateInterpolator()
                start()
            }
            
            // Start continuous animations
            startContinuousAnimations()
        }
    }
    
    private fun startContinuousAnimations() {
        // Floating animation for logo
        ObjectAnimator.ofFloat(binding.warframeLogo, "translationY", 0f, -10f, 0f).apply {
            duration = 3000
            repeatCount = ObjectAnimator.INFINITE
            interpolator = DecelerateInterpolator()
            start()
        }
        
        // Rotation animation for particle effects
        ObjectAnimator.ofFloat(binding.particleEffect1, "rotation", 0f, 360f).apply {
            duration = 20000
            repeatCount = ObjectAnimator.INFINITE
            start()
        }
        
        ObjectAnimator.ofFloat(binding.particleEffect2, "rotation", 360f, 0f).apply {
            duration = 15000
            repeatCount = ObjectAnimator.INFINITE
            start()
        }
    }
    
    private fun setupClickListeners() {
        binding.loginButton.setOnClickListener {
            performLogin()
        }
        
        // Test if guest button exists
        Toast.makeText(this, "Setting up guest button listener", Toast.LENGTH_SHORT).show()
        
        binding.guestButton.setOnClickListener {
            proceedAsGuest()
        }
        
        binding.createAccountText.setOnClickListener {
            // TODO: Navigate to account creation
        }
        
        binding.forgotPasswordText.setOnClickListener {
            // TODO: Navigate to password recovery
        }
    }
    
    private fun performLogin() {
        val username = binding.usernameInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password")
            return
        }
        
        // Show loading state
        binding.loginButton.isEnabled = false
        binding.loginProgress.visibility = View.VISIBLE
        binding.loginButton.text = "CONNECTING..."
        
        // Simulate login process
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000) // Simulate network delay
            
            // Test accounts for demo
            val validCredentials = mapOf(
                "testuser" to "password123",
                "demo" to "demo",
                "tenno" to "warframe",
                "guest" to "guest123",
                "admin" to "admin"
            )
            
            // Check credentials
            if (validCredentials[username.lowercase()] == password || 
                (username.isNotEmpty() && password.isNotEmpty() && username.length > 2)) {
                navigateToMain()
            } else {
                showError("Invalid credentials. Try: testuser/password123 or demo/demo")
                resetLoginButton()
            }
        }
    }
    
    private fun proceedAsGuest() {
        Toast.makeText(this, "Guest button clicked!", Toast.LENGTH_SHORT).show()
        
        binding.guestButton.isEnabled = false
        binding.guestButton.visibility = View.INVISIBLE
        binding.guestProgress.visibility = View.VISIBLE
        
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            Toast.makeText(this@LoginActivity, "Navigating to main...", Toast.LENGTH_SHORT).show()
            navigateToMain()
        }
    }
    
    private fun navigateToMain() {
        try {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        } catch (e: Exception) {
            Toast.makeText(this, "Error starting MainActivity: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun showError(message: String) {
        binding.errorText.text = message
        binding.errorText.visibility = View.VISIBLE
        
        // Shake animation for login container
        ObjectAnimator.ofFloat(binding.loginContainer, "translationX", 0f, -10f, 10f, -5f, 5f, 0f).apply {
            duration = 500
            start()
        }
        
        // Hide error after 3 seconds
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            binding.errorText.visibility = View.GONE
        }
    }
    
    private fun resetLoginButton() {
        binding.loginButton.isEnabled = true
        binding.loginProgress.visibility = View.GONE
        binding.loginButton.text = "LOGIN"
    }
    
    override fun onDestroy() {
        super.onDestroy()
        animationJob?.cancel()
    }
}