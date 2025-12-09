package com.example.warframeapp20.ui.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.warframeapp20.R
import com.example.warframeapp20.data.SortieMission

class SortieMissionAdapter(private var missions: List<SortieMission>) : 
    RecyclerView.Adapter<SortieMissionAdapter.SortieMissionViewHolder>() {

    class SortieMissionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val missionNumberView: TextView = view.findViewById(R.id.sortieMissionNumber)
        val missionTypeText: TextView = view.findViewById(R.id.sortieMissionText)
        val missionLocationText: TextView = view.findViewById(R.id.sortieLocationText)
        val missionModifierText: TextView = view.findViewById(R.id.sortieConditionText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortieMissionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sortie_mission, parent, false)
        return SortieMissionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SortieMissionViewHolder, position: Int) {
        val mission = missions[position]
        val context = holder.itemView.context

        // Set mission number (index + 1)
        holder.missionNumberView.text = (position + 1).toString()

        // Set mission details
        holder.missionTypeText.text = mission.missionType
        holder.missionLocationText.text = mission.node
        holder.missionModifierText.text = mission.modifier

        // Since new data class doesn't have completed field, assume all missions are active
        holder.missionTypeText.alpha = 1.0f
        holder.missionLocationText.alpha = 1.0f
        holder.missionNumberView.setBackgroundResource(R.drawable.bg_mission_active)

        // Set modifier text color
        holder.missionModifierText.setTextColor(ContextCompat.getColor(context, android.R.color.holo_orange_light))
    }

    override fun getItemCount() = missions.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newMissions: List<SortieMission>) {
        missions = newMissions
        notifyDataSetChanged()
    }
}
