package com.example.warframeapp20.ui.resources

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.warframeapp20.R
import com.example.warframeapp20.data.ResourceInventoryItem

class ResourcesAdapter(
    private val onResourceClick: (ResourceInventoryItem) -> Unit
) : RecyclerView.Adapter<ResourcesAdapter.ResourceViewHolder>() {

    private var resources = listOf<ResourceInventoryItem>()

    fun updateResources(newResources: List<ResourceInventoryItem>) {
        resources = newResources
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resource, parent, false)
        return ResourceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        holder.bind(resources[position])
    }

    override fun getItemCount(): Int = resources.size

    inner class ResourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.resourceNameText)
        private val amountText: TextView = itemView.findViewById(R.id.resourceAmountText)
        private val statusText: TextView = itemView.findViewById(R.id.resourceStatusText)

        fun bind(item: ResourceInventoryItem) {
            nameText.text = item.resource.name
            amountText.text = "${item.currentAmount}"
            
            statusText.text = when {
                item.isLow -> "LOW (need ${item.deficit})"
                item.surplus > 0 -> "Surplus: +${item.surplus}"
                else -> "Adequate"
            }
            
            statusText.setTextColor(
                when {
                    item.isLow -> android.graphics.Color.RED
                    item.surplus > 0 -> android.graphics.Color.GREEN
                    else -> android.graphics.Color.GRAY
                }
            )

            itemView.setOnClickListener {
                onResourceClick(item)
            }
        }
    }
}