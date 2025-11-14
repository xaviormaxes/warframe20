package com.example.warframeapp20.ui.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.warframeapp20.databinding.FragmentMarketBinding
import com.example.warframeapp20.data.ItemDetails

class MarketFragment : Fragment() {

    private var _binding: FragmentMarketBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MarketViewModel
    private lateinit var marketItemAdapter: MarketItemAdapter
    private lateinit var priceAlertAdapter: PriceAlertAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[MarketViewModel::class.java]
        _binding = FragmentMarketBinding.inflate(inflater, container, false)

        setupRecyclerViews()
        setupSearchView()
        observeViewModel()
        
        return binding.root
    }

    private fun setupRecyclerViews() {
        // Market items list
        marketItemAdapter = MarketItemAdapter { item ->
            viewModel.loadItemDetails(item.urlName)
        }
        binding.marketItemsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = marketItemAdapter
        }

        // Price alerts
        priceAlertAdapter = PriceAlertAdapter { alert ->
            viewModel.removeAlert(alert)
        }
        binding.priceAlertsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = priceAlertAdapter
        }

        // Trending items
        binding.trendingRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = TrendingItemAdapter { item ->
                viewModel.searchItems(item.itemName)
            }
        }
    }

    private fun setupSearchView() {
        binding.marketSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchItems(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    viewModel.loadPopularItems()
                } else if (newText.length >= 3) {
                    viewModel.searchItems(newText)
                }
                return true
            }
        })
    }

    private fun observeViewModel() {
        viewModel.marketItems.observe(viewLifecycleOwner) { items ->
            marketItemAdapter.updateItems(items)
            binding.emptyStateText.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.selectedItemDetails.observe(viewLifecycleOwner) { details ->
            if (details != null) {
                showItemDetailsDialog(details)
            }
        }

        viewModel.trendingItems.observe(viewLifecycleOwner) { trending ->
            (binding.trendingRecyclerView.adapter as? TrendingItemAdapter)?.updateItems(trending)
        }

        viewModel.priceAlerts.observe(viewLifecycleOwner) { alerts ->
            priceAlertAdapter.updateAlerts(alerts)
            binding.priceAlertsSection.visibility = if (alerts.isEmpty()) View.GONE else View.VISIBLE
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            if (error != null) {
                binding.errorText.apply {
                    text = error
                    visibility = View.VISIBLE
                }
            } else {
                binding.errorText.visibility = View.GONE
            }
        }
    }

    private fun showItemDetailsDialog(details: ItemDetails) {
        val dialog = MarketItemDetailsDialog.newInstance(details)
        dialog.show(parentFragmentManager, "item_details")
    }

    // Removed onResume() to prevent duplicate data loading
    // The ViewModel loads data in its init block, which is sufficient

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}