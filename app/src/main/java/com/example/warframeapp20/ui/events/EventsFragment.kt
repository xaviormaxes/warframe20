package com.example.warframeapp20.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.warframeapp20.databinding.FragmentEventsBinding
import com.example.warframeapp20.ui.dashboard.EventAdapter

class EventsFragment : Fragment() {

    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: EventsViewModel
    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[EventsViewModel::class.java]
        
        setupRecyclerView()
        observeViewModel()
        
        return binding.root
    }
    
    private fun setupRecyclerView() {
        eventAdapter = EventAdapter(emptyList())
        binding.recyclerViewEvents.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = eventAdapter
        }
    }
    
    private fun observeViewModel() {
        viewModel.events.observe(viewLifecycleOwner) { events ->
            eventAdapter.updateData(events)
        }
        
        viewModel.loadEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}