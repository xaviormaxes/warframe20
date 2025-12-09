package com.example.warframeapp20.ui.weapons

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warframeapp20.data.Weapon
import com.example.warframeapp20.databinding.ItemWeaponBinding

class WeaponAdapter(
    private val onWeaponClick: (Weapon) -> Unit
) : RecyclerView.Adapter<WeaponAdapter.WeaponViewHolder>() {

    private var weapons = listOf<Weapon>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateWeapons(newWeapons: List<Weapon>) {
        weapons = newWeapons
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeaponViewHolder {
        val binding = ItemWeaponBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WeaponViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeaponViewHolder, position: Int) {
        holder.bind(weapons[position])
    }

    override fun getItemCount(): Int = weapons.size

    inner class WeaponViewHolder(
        private val binding: ItemWeaponBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(weapon: Weapon) {
            binding.apply {
                textWeaponName.text = weapon.name
                textWeaponType.text = weapon.type.toString()
                textDamage.text = weapon.damage.toString()
                textCritChance.text = "${(weapon.critChance * 100).toInt()}%"
                textMastery.text = "MR ${weapon.mastery}"
                
                chipPrime.visibility = if (weapon.isPrime) {
                    android.view.View.VISIBLE
                } else {
                    android.view.View.GONE
                }
                
                root.setOnClickListener {
                    onWeaponClick(weapon)
                }
            }
        }
    }
}