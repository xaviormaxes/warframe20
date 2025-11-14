package com.example.warframeapp20.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.warframeapp20.R
import com.example.warframeapp20.data.Invasion

class InvasionAdapter(private var invasions: List<Invasion>) : 
    RecyclerView.Adapter<InvasionAdapter.InvasionViewHolder>() {

    class InvasionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val locationText: TextView = view.findViewById(R.id.invasionLocationText)
        val progressText: TextView = view.findViewById(R.id.invasionProgressText)
        val progressBar: ProgressBar = view.findViewById(R.id.invasionProgressBar)
        val attackerText: TextView = view.findViewById(R.id.invasionAttackerText)
        val attackerRewardText: TextView = view.findViewById(R.id.invasionAttackerRewardText)
        val defenderText: TextView = view.findViewById(R.id.invasionDefenderText)
        val defenderRewardText: TextView = view.findViewById(R.id.invasionDefenderRewardText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvasionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_invasion, parent, false)
        return InvasionViewHolder(view)
    }

    override fun onBindViewHolder(holder: InvasionViewHolder, position: Int) {
        val invasion = invasions[position]

        holder.locationText.text = invasion.node
        holder.progressText.text = "${invasion.completion.toInt()}%"
        holder.progressBar.progress = invasion.completion.toInt()

        // Extract faction from rewards if available, or use default text
        val attackerFaction = if (invasion.vsInfestation) "Infested" else "Attacker"
        val defenderFaction = "Defender"
        
        holder.attackerText.text = attackerFaction
        holder.attackerRewardText.text = invasion.attackerReward.asString

        holder.defenderText.text = defenderFaction
        holder.defenderRewardText.text = invasion.defenderReward.asString

        // Set colors based on faction
        val context = holder.itemView.context
        if (invasion.vsInfestation) {
            holder.attackerText.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
        } else {
            holder.attackerText.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_light))
        }

        holder.defenderText.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_light))
    }

    override fun getItemCount() = invasions.size

    fun updateData(newInvasions: List<Invasion>) {
        invasions = newInvasions
        notifyDataSetChanged()
    }
}
