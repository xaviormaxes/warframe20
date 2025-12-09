package com.example.warframeapp20.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.warframeapp20.databinding.FragmentSettingsBinding
import com.example.warframeapp20.ui.theme.ThemeDialogFragment
import com.example.warframeapp20.util.ThemeManager

class SettingsFragment : Fragment(), ThemeDialogFragment.ThemeDialogListener {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView = binding.textSettings
        settingsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Set up theme button
        binding.themeButton.setOnClickListener {
            showThemeDialog()
        }

        return root
    }

    private fun showThemeDialog() {
        val themeDialog = ThemeDialogFragment.newInstance()
        themeDialog.setThemeDialogListener(this)
        themeDialog.show(parentFragmentManager, ThemeDialogFragment.TAG)
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