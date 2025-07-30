package com.example.warframeapp20.ui.builds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.warframeapp20.databinding.FragmentBuildsBinding

class BuildsFragment : Fragment() {

    private var _binding: FragmentBuildsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BuildsViewModel
    private lateinit var buildsAdapter: BuildsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[BuildsViewModel::class.java]
        _binding = FragmentBuildsBinding.inflate(inflater, container, false)

        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
        
        return binding.root
    }

    private fun setupRecyclerView() {
        buildsAdapter = BuildsAdapter(
            onBuildClick = { build ->
                viewModel.selectBuild(build)
            },
            onEditClick = { build ->
                openBuildEditor(build)
            },
            onDeleteClick = { build ->
                viewModel.deleteBuild(build)
            },
            onShareClick = { build ->
                viewModel.shareBuild(build)
            }
        )
        
        binding.buildsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = buildsAdapter
        }
    }

    private fun setupClickListeners() {
        binding.fabNewBuild.setOnClickListener {
            openBuildEditor(null)
        }

        binding.calculatorCard.setOnClickListener {
            openBuildCalculator()
        }

        binding.modLibraryCard.setOnClickListener {
            openModLibrary()
        }
    }

    private fun observeViewModel() {
        viewModel.builds.observe(viewLifecycleOwner) { builds ->
            buildsAdapter.updateBuilds(builds)
            updateEmptyState(builds.isEmpty())
        }

        viewModel.selectedBuild.observe(viewLifecycleOwner) { build ->
            if (build != null) {
                updateSelectedBuildDisplay(build)
            }
        }

        viewModel.buildStats.observe(viewLifecycleOwner) { stats ->
            if (stats != null) {
                updateStatsDisplay(stats)
            }
        }

        viewModel.modSuggestions.observe(viewLifecycleOwner) { suggestions ->
            updateModSuggestions(suggestions)
        }

        viewModel.isCalculating.observe(viewLifecycleOwner) { isCalculating ->
            binding.calculationProgress.visibility = if (isCalculating) View.VISIBLE else View.GONE
        }
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        binding.emptyStateLayout.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.buildsRecyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun updateSelectedBuildDisplay(build: com.example.warframeapp20.data.Build) {
        binding.selectedBuildCard.visibility = View.VISIBLE
        binding.selectedBuildName.text = build.name
        binding.selectedBuildWarframe.text = "Warframe: ${build.warframeId}"
        binding.selectedBuildMods.text = "${build.mods.size} mods equipped"
        binding.selectedBuildForma.text = "${build.formaCount} forma used"
    }

    private fun updateStatsDisplay(stats: Any) {
        // Display calculated stats based on type (weapon vs warframe)
        when (stats) {
            is com.example.warframeapp20.data.WeaponDamageStats -> {
                binding.damageStatsCard.visibility = View.VISIBLE
                binding.totalDamageText.text = "Total: ${stats.totalDamage.toInt()}"
                binding.critDamageText.text = "Crit: ${stats.criticalDamage.toInt()}"
                binding.burstDpsText.text = "Burst DPS: ${stats.burstDPS.toInt()}"
                binding.sustainedDpsText.text = "Sustained DPS: ${stats.sustainedDPS.toInt()}"
            }
            is com.example.warframeapp20.data.WarframeStats -> {
                binding.warframeStatsCard.visibility = View.VISIBLE
                binding.healthText.text = "Health: ${stats.health}"
                binding.shieldText.text = "Shield: ${stats.shield}"
                binding.armorText.text = "Armor: ${stats.armor}"
                binding.ehpText.text = "EHP: ${stats.effectiveHealth}"
                binding.powerStrengthText.text = "Strength: ${stats.powerStrength.toInt()}%"
                binding.powerEfficiencyText.text = "Efficiency: ${stats.powerEfficiency.toInt()}%"
            }
        }
    }

    private fun updateModSuggestions(suggestions: List<com.example.warframeapp20.data.ModSuggestion>) {
        if (suggestions.isNotEmpty()) {
            binding.suggestionsCard.visibility = View.VISIBLE
            val suggestionsText = suggestions.take(3).joinToString("\n") { 
                "• ${it.mod.name}: ${it.reason}"
            }
            binding.suggestionsText.text = suggestionsText
        } else {
            binding.suggestionsCard.visibility = View.GONE
        }
    }

    private fun openBuildEditor(build: com.example.warframeapp20.data.Build?) {
        val fragment = BuildEditorFragment.newInstance(build)
        parentFragmentManager.beginTransaction()
            .replace(android.R.id.content, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun openBuildCalculator() {
        val fragment = BuildCalculatorFragment()
        parentFragmentManager.beginTransaction()
            .replace(android.R.id.content, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun openModLibrary() {
        val fragment = ModLibraryFragment()
        parentFragmentManager.beginTransaction()
            .replace(android.R.id.content, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}