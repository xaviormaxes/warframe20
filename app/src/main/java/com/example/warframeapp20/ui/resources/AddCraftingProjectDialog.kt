package com.example.warframeapp20.ui.resources

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.warframeapp20.data.CraftingProject

class AddCraftingProjectDialog : DialogFragment() {

    private var listener: OnProjectAddedListener? = null

    interface OnProjectAddedListener {
        fun onProjectAdded(project: CraftingProject)
    }

    fun setOnProjectAddedListener(listener: OnProjectAddedListener) {
        this.listener = listener
    }
    
    fun setOnProjectSelectedListener(callback: (String, Int) -> Unit) {
        this.listener = object : OnProjectAddedListener {
            override fun onProjectAdded(project: CraftingProject) {
                callback(project.name, 1) // Default quantity of 1
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Add Crafting Project")
            .setMessage("Select a project to add to your crafting queue")
            .setPositiveButton("Add") { _, _ ->
                // TODO: Implement project selection UI
                // For now, create a sample project
                val sampleProject = CraftingProject(
                    id = "sample_${System.currentTimeMillis()}",
                    name = "Sample Project",
                    type = "warframe",
                    requirements = emptyList(),
                    buildTime = 72 * 60 * 60 * 1000L, // 72 hours
                    credits = 25000
                )
                listener?.onProjectAdded(sampleProject)
            }
            .setNegativeButton("Cancel", null)
            .create()
    }

    companion object {
        const val TAG = "AddCraftingProjectDialog"
        
        fun newInstance(): AddCraftingProjectDialog {
            return AddCraftingProjectDialog()
        }
    }
}