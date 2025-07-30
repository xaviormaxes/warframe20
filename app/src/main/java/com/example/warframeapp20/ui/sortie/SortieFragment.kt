package com.example.warframeapp20.ui.sortie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.warframeapp20.databinding.FragmentSortieBinding
import com.example.warframeapp20.ui.dashboard.SortieMissionAdapter

class SortieFragment : Fragment() {

    private var _binding: FragmentSortieBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: SortieViewModel
    private lateinit var missionAdapter: SortieMissionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSortieBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SortieViewModel::class.java]
        
        setupRecyclerView()
        observeViewModel()
        
        return binding.root
    }
    
    private fun setupRecyclerView() {
        missionAdapter = SortieMissionAdapter(emptyList())
        binding.sortieMissionsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = missionAdapter
        }
    }
    
    private fun observeViewModel() {
        viewModel.sortie.observe(viewLifecycleOwner) { sortie ->
            sortie?.let {
                binding.sortieBossText.text = it.boss
                binding.sortieFactionText.text = it.faction
                missionAdapter.updateData(it.missions)
            }
        }
        
        viewModel.loadSortie()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}