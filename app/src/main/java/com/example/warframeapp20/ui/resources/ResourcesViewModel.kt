package com.example.warframeapp20.ui.resources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warframeapp20.data.*
import kotlinx.coroutines.launch

class ResourcesViewModel : ViewModel() {

    private val resourceService = ResourceService()
    
    private val _resourceInventory = MutableLiveData<List<ResourceInventoryItem>>()
    val resourceInventory: LiveData<List<ResourceInventoryItem>> = _resourceInventory

    private val _craftingProjects = MutableLiveData<List<CraftingProject>>()
    val craftingProjects: LiveData<List<CraftingProject>> = _craftingProjects

    private val _farmingRoutes = MutableLiveData<List<FarmingRoute>>()
    val farmingRoutes: LiveData<List<FarmingRoute>> = _farmingRoutes

    private val _selectedResource = MutableLiveData<ResourceInventoryItem?>()
    val selectedResource: LiveData<ResourceInventoryItem?> = _selectedResource

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _currentTab = MutableLiveData<ResourceTab>()
    val currentTab: LiveData<ResourceTab> = _currentTab
    
    private val _farmingRoute = MutableLiveData<FarmingRoute?>()
    val farmingRoute: LiveData<FarmingRoute?> = _farmingRoute
    
    private val _resourceAllocation = MutableLiveData<ResourceAllocation?>()
    val resourceAllocation: LiveData<ResourceAllocation?> = _resourceAllocation
    
    private val _farmingTips = MutableLiveData<List<FarmingTip>>()
    val farmingTips: LiveData<List<FarmingTip>> = _farmingTips

    init {
        _currentTab.value = ResourceTab.INVENTORY
        loadResourceData()
    }

    fun switchTab(tab: ResourceTab) {
        _currentTab.value = tab
        when (tab) {
            ResourceTab.INVENTORY -> loadResourceInventory()
            ResourceTab.PROJECTS -> loadCraftingProjects()
            ResourceTab.FARMING -> loadFarmingRoutes()
        }
    }

    private fun loadResourceData() {
        loadResourceInventory()
        loadCraftingProjects()
        loadFarmingRoutes()
    }

    private fun loadResourceInventory() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val inventory = resourceService.getResourceInventory()
                _resourceInventory.value = inventory
            } catch (e: Exception) {
                _resourceInventory.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadCraftingProjects() {
        viewModelScope.launch {
            try {
                // TODO: Implement crafting projects loading
                val projects = emptyList<CraftingProject>()
                _craftingProjects.value = projects
            } catch (e: Exception) {
                _craftingProjects.value = emptyList()
            }
        }
    }

    private fun loadFarmingRoutes() {
        viewModelScope.launch {
            try {
                // Create a sample farming route for now
                val sampleRoute = FarmingRoute(
                    id = "sample",
                    name = "Resource Farming Route",
                    stops = emptyList(),
                    totalEstimatedTime = 0,
                    efficiency = 0.0,
                    targetResource = "neurodes"
                )
                _farmingRoute.value = sampleRoute
                _farmingRoutes.value = listOf(sampleRoute)
            } catch (e: Exception) {
                _farmingRoutes.value = emptyList()
            }
        }
    }

    fun showResourceDetails(resource: ResourceInventoryItem) {
        _selectedResource.value = resource
    }

    fun optimizeResourceAllocation() {
        viewModelScope.launch {
            try {
                val projects = _craftingProjects.value ?: emptyList()
                val allocation = resourceService.optimizeResourceAllocation(projects)
                // TODO: Handle optimization results
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun showFarmingTips() {
        // TODO: Show farming tips dialog
    }

    fun addCraftingProject(project: CraftingProject) {
        val currentProjects = _craftingProjects.value?.toMutableList() ?: mutableListOf()
        currentProjects.add(project)
        _craftingProjects.value = currentProjects
    }

    fun removeCraftingProject(projectId: String) {
        val currentProjects = _craftingProjects.value?.toMutableList() ?: return
        currentProjects.removeAll { it.id == projectId }
        _craftingProjects.value = currentProjects
    }
    
    fun selectProject(project: CraftingProject) {
        // Handle project selection
    }
    
    fun toggleProjectTracking(project: CraftingProject, enabled: Boolean) {
        // Handle project tracking toggle
    }
    
    fun navigateToFarmingLocation(stop: FarmingStop) {
        // Handle navigation to farming location
    }
    
    fun refreshData() {
        loadResourceData()
    }
    
    fun addCraftingProject(projectName: String, quantity: Int) {
        val project = CraftingProject(
            id = System.currentTimeMillis().toString(),
            name = projectName,
            type = "item",
            requirements = emptyList(),
            buildTime = 0L,
            credits = 0
        )
        addCraftingProject(project)
    }
}

enum class ResourceTab {
    INVENTORY, PROJECTS, FARMING
}