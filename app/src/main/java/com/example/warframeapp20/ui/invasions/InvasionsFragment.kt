package com.example.warframeapp20.ui.invasions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.warframeapp20.databinding.FragmentInvasionsBinding
import com.example.warframeapp20.ui.dashboard.InvasionAdapter

class InvasionsFragment : Fragment() {

    private var _binding: FragmentInvasionsBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: InvasionsViewModel
    private lateinit var invasionAdapter: InvasionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInvasionsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[InvasionsViewModel::class.java]
        
        setupRecyclerView()
        observeViewModel()
        
        return binding.root
    }
    
    private fun setupRecyclerView() {
        invasionAdapter = InvasionAdapter(emptyList())
        binding.recyclerViewInvasions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = invasionAdapter
        }
    }
    
    private fun observeViewModel() {
        viewModel.invasions.observe(viewLifecycleOwner) { invasions ->
            invasionAdapter.updateData(invasions)
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