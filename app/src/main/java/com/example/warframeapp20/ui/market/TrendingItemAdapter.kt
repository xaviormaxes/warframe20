package com.example.warframeapp20.ui.market

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.warframeapp20.R
import com.example.warframeapp20.data.TrendingItem

class TrendingItemAdapter(
    private val onItemClick: (TrendingItem) -> Unit
) : RecyclerView.Adapter<TrendingItemAdapter.TrendingItemViewHolder>() {

    private var items = listOf<TrendingItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<TrendingItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trending, parent, false)
        return TrendingItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class TrendingItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.trendingItemNameText)
        private val priceText: TextView = itemView.findViewById(R.id.trendingPriceText)
        private val changeText: TextView = itemView.findViewById(R.id.trendingChangeText)

        fun bind(item: TrendingItem) {
            nameText.text = item.itemName
            priceText.text = "${item.currentPrice}p"
            changeText.text = "${String.format(java.util.Locale.getDefault(), "%.1f", item.priceChange)}%"
            
            changeText.setTextColor(
                if (item.isRising) android.graphics.Color.GREEN 
                else android.graphics.Color.RED
            )

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}