package com.example.warframeapp20.ui.dashboard

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.warframeapp20.R
import com.example.warframeapp20.data.Event

class EventAdapter(private var events: List<Event>) : 
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.eventTitleText)
        val statusText: TextView = view.findViewById(R.id.eventStatusText)
        val locationText: TextView = view.findViewById(R.id.eventLocationText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]

        holder.titleText.text = event.description
        holder.statusText.text = event.tooltip
        holder.locationText.text = event.node

        // Set default color for active events
        if (event.isActive) {
            holder.statusText.setTextColor(Color.GREEN)
        } else {
            holder.statusText.setTextColor(Color.GRAY)
        }
    }

    override fun getItemCount() = events.size

    fun updateData(newEvents: List<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }
}
