package com.example.warframeapp20.ui.warframes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warframeapp20.data.Warframe
import com.example.warframeapp20.databinding.ItemWarframeBinding

class WarframeAdapter(
    private val onWarframeClick: (Warframe) -> Unit
) : RecyclerView.Adapter<WarframeAdapter.WarframeViewHolder>() {

    private var warframes = listOf<Warframe>()

    fun updateWarframes(newWarframes: List<Warframe>) {
        warframes = newWarframes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WarframeViewHolder {
        val binding = ItemWarframeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WarframeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WarframeViewHolder, position: Int) {
        holder.bind(warframes[position])
    }

    override fun getItemCount(): Int = warframes.size

    inner class WarframeViewHolder(
        private val binding: ItemWarframeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(warframe: Warframe) {
            binding.apply {
                textWarframeName.text = warframe.name
                textWarframeDescription.text = warframe.description
                
                // Health stats
                textHealth.text = warframe.health.toString()
                textShield.text = warframe.shield.toString()
                textArmor.text = warframe.armor.toString()
                textEnergy.text = warframe.energy.toString()
                
                // Show prime badge if applicable
                chipPrime.visibility = if (warframe.isPrime) {
                    android.view.View.VISIBLE
                } else {
                    android.view.View.GONE
                }
                
                // Mastery requirement
                textMastery.text = "MR ${warframe.mastery}"
                
                root.setOnClickListener {
                    onWarframeClick(warframe)
                }
            }
        }
    }
}