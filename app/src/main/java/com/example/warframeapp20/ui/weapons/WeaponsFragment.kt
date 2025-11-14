package com.example.warframeapp20.ui.weapons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.warframeapp20.databinding.FragmentWeaponsBinding

class WeaponsFragment : Fragment() {

    private var _binding: FragmentWeaponsBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: WeaponsViewModel
    private lateinit var weaponAdapter: WeaponAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeaponsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[WeaponsViewModel::class.java]
        
        setupRecyclerView()
        observeViewModel()
        
        return binding.root
    }
    
    private fun setupRecyclerView() {
        weaponAdapter = WeaponAdapter { weapon ->
            // Handle weapon click
        }
        
        binding.recyclerViewWeapons.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = weaponAdapter
        }
    }
    
    private fun observeViewModel() {
        viewModel.weapons.observe(viewLifecycleOwner) { weapons ->
            weaponAdapter.updateWeapons(weapons)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}