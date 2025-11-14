package com.example.warframeapp20.ui.market

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.warframeapp20.R
import com.example.warframeapp20.data.MarketOrder

class MarketOrderAdapter(
    private val orders: List<MarketOrder>
) : RecyclerView.Adapter<MarketOrderAdapter.MarketOrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketOrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_market_order, parent, false)
        return MarketOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: MarketOrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int = orders.size

    inner class MarketOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val platinumText: TextView = itemView.findViewById(R.id.platinumText)
        private val quantityText: TextView = itemView.findViewById(R.id.quantityText)
        private val userText: TextView = itemView.findViewById(R.id.userText)
        private val statusText: TextView = itemView.findViewById(R.id.statusText)

        fun bind(order: MarketOrder) {
            platinumText.text = "${order.platinum}p"
            quantityText.text = "x${order.quantity}"
            userText.text = order.user.ingameName
            statusText.text = order.user.status
        }
    }
}