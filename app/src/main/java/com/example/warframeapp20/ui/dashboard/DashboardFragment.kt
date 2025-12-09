package com.example.warframeapp20.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager  
import com.example.warframeapp20.databinding.FragmentDashboardBinding
import com.example.warframeapp20.ui.dashboard.EventAdapter
import com.example.warframeapp20.ui.dashboard.FissureAdapter
import com.example.warframeapp20.ui.dashboard.InvasionAdapter
import com.example.warframeapp20.ui.dashboard.SortieMissionAdapter

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        setupInvasionsRecyclerView()
        setupEventsRecyclerView()
        setupFissuresRecyclerView()
        setupSortieRecyclerView()

        // Observe data from ViewModel
        observeViewModel()

        return binding.root
    }

    private fun setupInvasionsRecyclerView() {
        binding.invasionsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.invasionsRecyclerView.adapter = InvasionAdapter(emptyList())
    }

    private fun setupEventsRecyclerView() {
        binding.eventsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.eventsRecyclerView.adapter = EventAdapter(emptyList())
    }

    private fun setupFissuresRecyclerView() {
        binding.fissuresRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.fissuresRecyclerView.adapter = FissureAdapter(emptyList())
    }

    private fun setupSortieRecyclerView() {
        binding.sortieRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.sortieRecyclerView.adapter = SortieMissionAdapter(emptyList())
    }

    private fun observeViewModel() {
        viewModel.invasions.observe(viewLifecycleOwner) { invasions ->
            (binding.invasionsRecyclerView.adapter as InvasionAdapter).updateData(invasions)
        }

        viewModel.events.observe(viewLifecycleOwner) { events ->
            (binding.eventsRecyclerView.adapter as EventAdapter).updateData(events)
        }

        viewModel.fissures.observe(viewLifecycleOwner) { fissures ->
            (binding.fissuresRecyclerView.adapter as FissureAdapter).updateData(fissures)
        }

        viewModel.sortie.observe(viewLifecycleOwner) { sortie ->
            binding.sortieBossTextView.text = sortie.boss
            binding.sortieFactionTextView.text = sortie.faction
            binding.sortieRewardTextView.text = "Rewards: Anasa Sculpture, Riven Mods, Boosters"
            (binding.sortieRecyclerView.adapter as SortieMissionAdapter).updateData(sortie.missions)
        }

        viewModel.cetusCycle.observe(viewLifecycleOwner) { cycle ->
            binding.cetusStatusTextView.text = if (cycle.isDay) "Day" else "Night"
            binding.cetusTimeLeftTextView.text = cycle.timeLeft
        }

        viewModel.vallisCycle.observe(viewLifecycleOwner) { cycle ->
            binding.vallisStatusTextView.text = if (cycle.isWarm) "Warm" else "Cold"
            binding.vallisTimeLeftTextView.text = cycle.timeLeft
        }

        viewModel.cambionCycle.observe(viewLifecycleOwner) { cycle ->
            binding.cambionStatusTextView.text = cycle.active
            binding.cambionTimeLeftTextView.text = cycle.timeLeft
        }
        
        // Observe loading state
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // You can add a loading indicator here if needed
            // For now, we'll just log it
        }
        
        // Observe error state
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                // You can show a toast or snackbar here
                // For now, we'll just log it
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up countdown timers
        (binding.fissuresRecyclerView.adapter as? FissureAdapter)?.cleanup()
        _binding = null
    }
}
