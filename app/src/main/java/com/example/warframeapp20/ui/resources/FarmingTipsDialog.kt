package com.example.warframeapp20.ui.resources

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.warframeapp20.data.FarmingTip

class FarmingTipsDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val tipsArrayList = arguments?.getSerializable(ARG_TIPS) as? ArrayList<*>
        val tips = tipsArrayList?.filterIsInstance<FarmingTip>() ?: listOf(
            FarmingTip("1", "Use Smeeta Kavat", "Smeeta Kavat provides random resource buffs", "general", "Easy"),
            FarmingTip("2", "Dark Sector missions", "Dark Sector missions provide resource bonuses", "location", "Easy"),
            FarmingTip("3", "Bring Nekros", "Nekros increases loot drops with Desecrate", "warframe", "Medium")
        )

        val tipsText = tips.joinToString("\n\n") { "• ${it.title}: ${it.description}" }

        return AlertDialog.Builder(requireContext())
            .setTitle("Farming Tips")
            .setMessage(tipsText)
            .setPositiveButton("Got it!", null)
            .create()
    }

    companion object {
        const val TAG = "FarmingTipsDialog"
        private const val ARG_TIPS = "tips"
        
        fun newInstance(tips: List<FarmingTip> = emptyList()): FarmingTipsDialog {
            val fragment = FarmingTipsDialog()
            val args = Bundle()
            args.putSerializable(ARG_TIPS, ArrayList(tips))
            fragment.arguments = args
            return fragment
        }
    }
}