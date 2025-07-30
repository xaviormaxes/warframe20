package com.example.warframeapp20.ui.theme

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.warframeapp20.util.ThemeManager

/**
 * Dialog for selecting Warframe themes
 */
class ThemeDialogFragment : DialogFragment() {

    interface ThemeDialogListener {
        fun onThemeSelected(themeName: String)
    }

    private var listener: ThemeDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val themeManager = ThemeManager.getInstance(requireContext())
        val currentTheme = themeManager.getCurrentTheme()
        val themes = themeManager.getAvailableThemes()

        val themeNames = themes.keys.toTypedArray()
        val themeDisplayNames = themes.values.toTypedArray()

        // Find the index of the current theme
        val selectedIndex = themeNames.indexOf(currentTheme)

        return AlertDialog.Builder(requireContext())
            .setTitle(requireContext().getString(com.example.warframeapp20.R.string.dialog_select_theme))
            .setSingleChoiceItems(themeDisplayNames, selectedIndex) { _, which ->
                val selectedTheme = themeNames[which]
                listener?.onThemeSelected(selectedTheme)
                dismiss()
            }
            .setNegativeButton(requireContext().getString(com.example.warframeapp20.R.string.dialog_cancel)) { _, _ -> dismiss() }
            .create()
    }

    fun setThemeDialogListener(listener: ThemeDialogListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "ThemeDialogFragment"

        fun newInstance(): ThemeDialogFragment {
            return ThemeDialogFragment()
        }
    }
}
