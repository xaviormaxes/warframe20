package com.example.warframeapp20.ui.resources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.warframeapp20.databinding.FragmentResourcesBinding

class ResourcesFragment : Fragment() {

    private var _binding: FragmentResourcesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ResourcesViewModel
    private lateinit var resourcesAdapter: ResourcesAdapter
    private lateinit var projectsAdapter: CraftingProjectsAdapter
    private lateinit var farmingAdapter: FarmingStopsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ResourcesViewModel::class.java]
        _binding = FragmentResourcesBinding.inflate(inflater, container, false)

        setupRecyclerViews()
        setupClickListeners()
        observeViewModel()
        
        return binding.root
    }

    private fun setupRecyclerViews() {
        // Resource inventory
        resourcesAdapter = ResourcesAdapter { resource ->
            viewModel.showResourceDetails(resource)
        }
        binding.resourcesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = resourcesAdapter
        }

        // Crafting projects
        projectsAdapter = CraftingProjectsAdapter(
            onProjectClick = { project ->
                viewModel.selectProject(project)
            },
            onToggleProject = { project, enabled ->
                viewModel.toggleProjectTracking(project, enabled)
            }
        )
        binding.projectsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = projectsAdapter
        }

        // Farming routes
        farmingAdapter = FarmingStopsAdapter { stop ->
            viewModel.navigateToFarmingLocation(stop)
        }
        binding.farmingRouteRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = farmingAdapter
        }
    }

    private fun setupClickListeners() {
        binding.fabAddProject.setOnClickListener {
            showAddProjectDialog()
        }

        binding.optimizeButton.setOnClickListener {
            viewModel.optimizeResourceAllocation()
        }

        binding.refreshButton.setOnClickListener {
            viewModel.refreshData()
        }

        binding.farmingTipsCard.setOnClickListener {
            viewModel.showFarmingTips()
        }

        // Tab switching
        binding.inventoryTab.setOnClickListener {
            switchToTab(ResourceTab.INVENTORY)
        }
        
        binding.projectsTab.setOnClickListener {
            switchToTab(ResourceTab.PROJECTS)
        }
        
        binding.farmingTab.setOnClickListener {
            switchToTab(ResourceTab.FARMING)
        }
    }

    private fun observeViewModel() {
        viewModel.resourceInventory.observe(viewLifecycleOwner) { resources ->
            resourcesAdapter.updateResources(resources)
        }

        viewModel.craftingProjects.observe(viewLifecycleOwner) { projects ->
            projectsAdapter.updateProjects(projects)
            updateProjectsProgress(projects)
        }

        viewModel.farmingRoute.observe(viewLifecycleOwner) { route ->
            if (route != null) {
                farmingAdapter.updateStops(route.stops)
                updateFarmingStats(route)
            }
        }

        viewModel.resourceAllocation.observe(viewLifecycleOwner) { allocation ->
            if (allocation != null) {
                updateAllocationRecommendations(allocation)
            }
        }

        viewModel.selectedResource.observe(viewLifecycleOwner) { resource ->
            if (resource != null) {
                showResourceDetailsDialog(resource)
            }
        }

        viewModel.farmingTips.observe(viewLifecycleOwner) { tips ->
            if (tips.isNotEmpty()) {
                showFarmingTipsDialog(tips)
            }
        }

        viewModel.currentTab.observe(viewLifecycleOwner) { tab ->
            updateTabDisplay(tab)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun switchToTab(tab: ResourceTab) {
        viewModel.switchTab(tab)
    }

    private fun updateTabDisplay(tab: ResourceTab) {
        // Reset all tabs
        binding.inventoryTab.isSelected = false
        binding.projectsTab.isSelected = false
        binding.farmingTab.isSelected = false
        
        // Hide all content
        binding.inventoryContent.visibility = View.GONE
        binding.projectsContent.visibility = View.GONE
        binding.farmingContent.visibility = View.GONE
        
        // Show selected tab
        when (tab) {
            ResourceTab.INVENTORY -> {
                binding.inventoryTab.isSelected = true
                binding.inventoryContent.visibility = View.VISIBLE
            }
            ResourceTab.PROJECTS -> {
                binding.projectsTab.isSelected = true
                binding.projectsContent.visibility = View.VISIBLE
            }
            ResourceTab.FARMING -> {
                binding.farmingTab.isSelected = true
                binding.farmingContent.visibility = View.VISIBLE
            }
        }
    }

    private fun updateProjectsProgress(projects: List<com.example.warframeapp20.data.CraftingProject>) {
        val totalProjects = projects.size
        val completedProjects = projects.count { it.isComplete }
        val overallProgress = if (totalProjects > 0) {
            projects.sumOf { it.totalProgress } / totalProjects
        } else 0.0
        
        binding.projectProgressText.text = "$completedProjects / $totalProjects projects complete"
        binding.projectProgressBar.progress = (overallProgress * 100).toInt()
    }

    private fun updateFarmingStats(route: com.example.warframeapp20.data.FarmingRoute) {
        binding.farmingTimeText.text = "${route.totalEstimatedTime} minutes"
        binding.farmingStopsText.text = "${route.stops.size} locations"
        binding.farmingEfficiencyText.text = "Efficiency: ${String.format("%.1f", route.efficiency)}/5"
    }

    private fun updateAllocationRecommendations(allocation: com.example.warframeapp20.data.ResourceAllocation) {
        if (allocation.recommendations.isNotEmpty()) {
            binding.recommendationsCard.visibility = View.VISIBLE
            val recommendationsText = allocation.recommendations.take(3).joinToString("\n") { "• $it" }
            binding.recommendationsText.text = recommendationsText
        } else {
            binding.recommendationsCard.visibility = View.GONE
        }
    }

    private fun showAddProjectDialog() {
        val dialog = AddCraftingProjectDialog()
        dialog.setOnProjectSelectedListener { projectName, quantity ->
            viewModel.addCraftingProject(projectName, quantity)
        }
        dialog.show(parentFragmentManager, "add_project")
    }

    private fun showResourceDetailsDialog(resource: com.example.warframeapp20.data.ResourceInventoryItem) {
        val dialog = ResourceDetailsDialog.newInstance(resource)
        dialog.show(parentFragmentManager, "resource_details")
    }

    private fun showFarmingTipsDialog(tips: List<com.example.warframeapp20.data.FarmingTip>) {
        val dialog = FarmingTipsDialog.newInstance(tips)
        dialog.show(parentFragmentManager, "farming_tips")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}