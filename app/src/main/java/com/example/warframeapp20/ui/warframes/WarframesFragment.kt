package com.example.warframeapp20.ui.warframes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.warframeapp20.databinding.FragmentWarframesBinding

class WarframesFragment : Fragment() {

    private var _binding: FragmentWarframesBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: WarframesViewModel
    private lateinit var warframeAdapter: WarframeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWarframesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[WarframesViewModel::class.java]
        
        setupRecyclerView()
        observeViewModel()
        
        return binding.root
    }
    
    private fun setupRecyclerView() {
        warframeAdapter = WarframeAdapter { warframe ->
            // Handle warframe click - could navigate to detail view
        }
        
        binding.recyclerViewWarframes.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = warframeAdapter
        }
    }
    
    private fun observeViewModel() {
        viewModel.warframes.observe(viewLifecycleOwner) { warframes ->
            warframeAdapter.updateWarframes(warframes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}