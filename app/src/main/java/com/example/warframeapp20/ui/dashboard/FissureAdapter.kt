package com.example.warframeapp20.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.warframeapp20.R
import com.example.warframeapp20.data.Fissure

class FissureAdapter(private var fissures: List<Fissure>) : 
    RecyclerView.Adapter<FissureAdapter.FissureViewHolder>() {

    class FissureViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val eraText: TextView = view.findViewById(R.id.fissureEraText)
        val missionText: TextView = view.findViewById(R.id.fissureMissionText)
        val locationText: TextView = view.findViewById(R.id.fissureLocationText)
        val enemyText: TextView = view.findViewById(R.id.fissureEnemyText)
        val timeText: TextView = view.findViewById(R.id.fissureTimeText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FissureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fissure, parent, false)
        return FissureViewHolder(view)
    }

    override fun onBindViewHolder(holder: FissureViewHolder, position: Int) {
        val fissure = fissures[position]
        val context = holder.itemView.context

        holder.eraText.text = fissure.tier
        holder.missionText.text = fissure.missionType
        holder.locationText.text = fissure.node
        holder.enemyText.text = fissure.enemy
        holder.timeText.text = fissure.eta

        // Set era-specific colors
        val eraColor = when (fissure.tier) {
            "Lith" -> ContextCompat.getColor(context, android.R.color.holo_blue_light)
            "Meso" -> ContextCompat.getColor(context, android.R.color.holo_green_light)
            "Neo" -> ContextCompat.getColor(context, android.R.color.holo_orange_light)
            "Axi" -> ContextCompat.getColor(context, android.R.color.holo_red_light)
            "Requiem" -> ContextCompat.getColor(context, android.R.color.holo_purple)
            else -> ContextCompat.getColor(context, android.R.color.secondary_text_light)
        }

        holder.eraText.setTextColor(eraColor)

        // Set enemy faction color
        when (fissure.enemy) {
            "Grineer" -> holder.enemyText.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
            "Corpus" -> holder.enemyText.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_light))
            "Infested" -> holder.enemyText.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
            "Corrupted" -> holder.enemyText.setTextColor(ContextCompat.getColor(context, android.R.color.holo_orange_light))
        }

        // Set time color
        holder.timeText.setTextColor(ContextCompat.getColor(context, android.R.color.holo_orange_light))
    }

    override fun getItemCount() = fissures.size

    fun updateData(newFissures: List<Fissure>) {
        fissures = newFissures
        notifyDataSetChanged()
    }
}
