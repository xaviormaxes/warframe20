package com.example.warframeapp20.ui.market

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.warframeapp20.R
import com.example.warframeapp20.data.MarketItem

class MarketItemAdapter(
    private val onItemClick: (MarketItem) -> Unit
) : RecyclerView.Adapter<MarketItemAdapter.MarketItemViewHolder>() {

    private var items = listOf<MarketItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<MarketItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_market_item, parent, false)
        return MarketItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MarketItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class MarketItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.itemNameText)
        private val urlNameText: TextView = itemView.findViewById(R.id.itemUrlNameText)

        fun bind(item: MarketItem) {
            nameText.text = item.itemName
            urlNameText.text = item.urlName

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}