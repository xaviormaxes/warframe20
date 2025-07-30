package com.example.warframeapp20.ui.market

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.warframeapp20.databinding.DialogMarketItemDetailsBinding
import com.example.warframeapp20.data.ItemDetails

class MarketItemDetailsDialog : DialogFragment() {

    private var _binding: DialogMarketItemDetailsBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var itemDetails: ItemDetails

    companion object {
        private const val ARG_ITEM_DETAILS = "item_details"
        
        fun newInstance(itemDetails: ItemDetails): MarketItemDetailsDialog {
            val fragment = MarketItemDetailsDialog()
            val args = Bundle().apply {
                putSerializable(ARG_ITEM_DETAILS, itemDetails)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemDetails = arguments?.getSerializable(ARG_ITEM_DETAILS) as ItemDetails
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogMarketItemDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDialog()
    }

    private fun setupDialog() {
        binding.itemNameText.text = itemDetails.itemName
        
        // Setup sell orders
        binding.sellOrdersRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MarketOrderAdapter(itemDetails.bestSellOrders)
        }
        
        // Setup buy orders  
        binding.buyOrdersRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MarketOrderAdapter(itemDetails.bestBuyOrders)
        }
        
        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}