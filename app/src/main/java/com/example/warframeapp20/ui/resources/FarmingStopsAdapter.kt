package com.example.warframeapp20.ui.resources

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.warframeapp20.R
import com.example.warframeapp20.data.FarmingStop

class FarmingStopsAdapter(
    private val onStopClick: (FarmingStop) -> Unit
) : RecyclerView.Adapter<FarmingStopsAdapter.FarmingStopViewHolder>() {

    private var stops = listOf<FarmingStop>()

    fun updateStops(newStops: List<FarmingStop>) {
        stops = newStops
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FarmingStopViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resource, parent, false)
        return FarmingStopViewHolder(view)
    }

    override fun onBindViewHolder(holder: FarmingStopViewHolder, position: Int) {
        holder.bind(stops[position])
    }

    override fun getItemCount(): Int = stops.size

    inner class FarmingStopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.resourceNameText)
        private val statusText: TextView = itemView.findViewById(R.id.resourceStatusText)
        private val amountText: TextView = itemView.findViewById(R.id.resourceAmountText)

        fun bind(stop: FarmingStop) {
            nameText.text = stop.location
            statusText.text = "${stop.resourceName} - ${stop.notes}"
            amountText.text = "${stop.efficiency}/5"
            
            // Color code efficiency rating
            amountText.setTextColor(
                when {
                    stop.efficiency >= 4.5 -> android.graphics.Color.GREEN
                    stop.efficiency >= 3.5 -> android.graphics.Color.YELLOW
                    else -> android.graphics.Color.RED
                }
            )

            itemView.setOnClickListener {
                onStopClick(stop)
            }
        }
    }
}