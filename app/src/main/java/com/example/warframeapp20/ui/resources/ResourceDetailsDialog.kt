package com.example.warframeapp20.ui.resources

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.warframeapp20.data.ResourceInventoryItem

class ResourceDetailsDialog : DialogFragment() {

    private var resourceItem: ResourceInventoryItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // TODO: Implement proper resource serialization
            // resourceItem = it.getSerializable(ARG_RESOURCE) as? ResourceInventoryItem
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val resource = resourceItem
        
        return if (resource != null) {
            AlertDialog.Builder(requireContext())
                .setTitle(resource.resource.name)
                .setMessage(buildResourceDetails(resource))
                .setPositiveButton("Close", null)
                .create()
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle("Resource Details")
                .setMessage("Resource information not available")
                .setPositiveButton("Close", null)
                .create()
        }
    }

    private fun buildResourceDetails(resource: ResourceInventoryItem): String {
        return buildString {
            appendLine("Description: ${resource.resource.description}")
            appendLine("Type: ${resource.resource.type}")
            appendLine("Rarity: ${resource.resource.rarity}")
            appendLine()
            appendLine("Current Amount: ${resource.currentAmount}")
            appendLine("Target Amount: ${resource.targetAmount}")
            if (resource.isLow) {
                appendLine("Deficit: ${resource.deficit}")
            } else if (resource.surplus > 0) {
                appendLine("Surplus: ${resource.surplus}")
            }
            appendLine()
            appendLine("Drop Locations:")
            resource.resource.locations.forEach { location ->
                appendLine("• $location")
            }
        }
    }

    companion object {
        const val TAG = "ResourceDetailsDialog"
        private const val ARG_RESOURCE = "resource"
        
        fun newInstance(resource: ResourceInventoryItem): ResourceDetailsDialog {
            return ResourceDetailsDialog().apply {
                arguments = Bundle().apply {
                    // TODO: Implement proper resource serialization
                    // putSerializable(ARG_RESOURCE, resource)
                }
                resourceItem = resource
            }
        }
    }
}