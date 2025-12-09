package com.example.warframeapp20.ui.market

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.warframeapp20.R
import com.example.warframeapp20.data.PriceAlert

class PriceAlertAdapter(
    private val onRemoveAlert: (PriceAlert) -> Unit
) : RecyclerView.Adapter<PriceAlertAdapter.PriceAlertViewHolder>() {

    private var alerts = listOf<PriceAlert>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateAlerts(newAlerts: List<PriceAlert>) {
        alerts = newAlerts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceAlertViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_price_alert, parent, false)
        return PriceAlertViewHolder(view)
    }

    override fun onBindViewHolder(holder: PriceAlertViewHolder, position: Int) {
        holder.bind(alerts[position])
    }

    override fun getItemCount(): Int = alerts.size

    inner class PriceAlertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemNameText: TextView = itemView.findViewById(R.id.alertItemNameText)
        private val targetPriceText: TextView = itemView.findViewById(R.id.alertTargetPriceText)

        fun bind(alert: PriceAlert) {
            itemNameText.text = alert.itemName
            targetPriceText.text = "${alert.targetPrice}p"

            itemView.setOnLongClickListener {
                onRemoveAlert(alert)
                true
            }
        }
    }
}