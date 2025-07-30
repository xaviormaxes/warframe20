package com.example.warframeapp20.ui.theme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.warframeapp20.databinding.FragmentThemesBinding
import com.example.warframeapp20.util.ThemeManager

class ThemeFragment : Fragment(), ThemeDialogFragment.ThemeDialogListener {

    private var _binding: FragmentThemesBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var themeAdapter: ThemeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThemesBinding.inflate(inflater, container, false)
        
        setupThemeList()
        
        return binding.root
    }
    
    private fun setupThemeList() {
        val themeManager = ThemeManager.getInstance(requireContext())
        val themes = themeManager.getAvailableThemes()
        val currentTheme = themeManager.getCurrentTheme()
        
        themeAdapter = ThemeAdapter(themes, currentTheme) { themeName ->
            onThemeSelected(themeName)
        }
        
        // You could add a RecyclerView to the fragment_themes.xml layout to display themes
        // For now, just show the dialog when the button is tapped
        binding.textTitle.setOnClickListener {
            showThemeDialog()
        }
    }
    
    private fun showThemeDialog() {
        val themeDialog = ThemeDialogFragment.newInstance()
        themeDialog.setThemeDialogListener(this)
        themeDialog.show(childFragmentManager, ThemeDialogFragment.TAG)
    }
    
    override fun onThemeSelected(themeName: String) {
        val themeManager = ThemeManager.getInstance(requireContext())
        themeManager.setTheme(themeName)
        
        // Recreate activity to apply new theme
        requireActivity().recreate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class ThemeAdapter(
    private val themes: Map<String, String>,
    private val currentTheme: String,
    private val onThemeClick: (String) -> Unit
) {
    // Simple adapter for theme selection - could be expanded with RecyclerView
}