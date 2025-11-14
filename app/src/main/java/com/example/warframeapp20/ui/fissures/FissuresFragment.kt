package com.example.warframeapp20.ui.fissures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.warframeapp20.databinding.FragmentFissuresBinding
import com.example.warframeapp20.ui.dashboard.FissureAdapter
import kotlinx.coroutines.launch

class FissuresFragment : Fragment() {

    private var _binding: FragmentFissuresBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: FissuresViewModel
    private lateinit var fissureAdapter: FissureAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFissuresBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[FissuresViewModel::class.java]
        
        setupRecyclerView()
        observeViewModel()
        
        return binding.root
    }
    
    private fun setupRecyclerView() {
        fissureAdapter = FissureAdapter(emptyList())
        binding.recyclerViewFissures.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = fissureAdapter
        }
    }
    
    private fun observeViewModel() {
        viewModel.fissures.observe(viewLifecycleOwner) { fissures ->
            fissureAdapter.updateData(fissures)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}