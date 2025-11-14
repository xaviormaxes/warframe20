package com.example.warframeapp20.ui.builds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warframeapp20.data.*
import kotlinx.coroutines.launch

class BuildsViewModel : ViewModel() {

    private val buildService = BuildService()
    
    private val _builds = MutableLiveData<List<Build>>()
    val builds: LiveData<List<Build>> = _builds

    private val _selectedBuild = MutableLiveData<Build?>()
    val selectedBuild: LiveData<Build?> = _selectedBuild

    private val _buildStats = MutableLiveData<Any?>()
    val buildStats: LiveData<Any?> = _buildStats

    private val _modSuggestions = MutableLiveData<List<ModSuggestion>>()
    val modSuggestions: LiveData<List<ModSuggestion>> = _modSuggestions

    private val _isCalculating = MutableLiveData<Boolean>()
    val isCalculating: LiveData<Boolean> = _isCalculating

    init {
        loadBuilds()
    }

    fun loadBuilds() {
        viewModelScope.launch {
            // Load builds from local storage
            val savedBuilds = getSavedBuilds()
            _builds.value = savedBuilds
            
            // If no builds exist, create some examples
            if (savedBuilds.isEmpty()) {
                createExampleBuilds()
            }
        }
    }

    fun selectBuild(build: Build) {
        _selectedBuild.value = build
        calculateBuildStats(build)
        generateModSuggestions(build)
    }

    fun createBuild(name: String, warframeId: String, weaponId: String? = null): Build {
        val newBuild = Build(
            id = generateBuildId(),
            name = name,
            warframeId = warframeId,
            weaponId = weaponId,
            mods = emptyList(),
            formaCount = 0,
            notes = "",
            tags = emptyList(),
            author = "You"
        )
        
        val currentBuilds = _builds.value?.toMutableList() ?: mutableListOf()
        currentBuilds.add(newBuild)
        _builds.value = currentBuilds
        saveBuilds(currentBuilds)
        
        return newBuild
    }

    fun updateBuild(build: Build) {
        val currentBuilds = _builds.value?.toMutableList() ?: mutableListOf()
        val index = currentBuilds.indexOfFirst { it.id == build.id }
        if (index >= 0) {
            currentBuilds[index] = build.copy(lastModified = System.currentTimeMillis())
            _builds.value = currentBuilds
            saveBuilds(currentBuilds)
            
            // Update selected build if it's the same one
            if (_selectedBuild.value?.id == build.id) {
                _selectedBuild.value = currentBuilds[index]
                calculateBuildStats(currentBuilds[index])
            }
        }
    }

    fun deleteBuild(build: Build) {
        val currentBuilds = _builds.value?.toMutableList() ?: mutableListOf()
        currentBuilds.removeAll { it.id == build.id }
        _builds.value = currentBuilds
        saveBuilds(currentBuilds)
        
        // Clear selection if deleted build was selected
        if (_selectedBuild.value?.id == build.id) {
            _selectedBuild.value = null
            _buildStats.value = null
            _modSuggestions.value = emptyList()
        }
    }

    fun shareBuild(build: Build) {
        // Generate shareable build code or URL
        val buildCode = generateBuildCode(build)
        // This would typically open a share dialog
        // For now, just copy to clipboard or save to shared builds
    }

    private fun calculateBuildStats(build: Build) {
        viewModelScope.launch {
            _isCalculating.value = true
            
            try {
                val warframe = getWarframeById(build.warframeId)
                if (warframe != null) {
                    if (build.weaponId != null) {
                        val weapon = getWeaponById(build.weaponId)
                        if (weapon != null) {
                            val weaponStats = buildService.calculateWeaponDamage(weapon, build)
                            _buildStats.value = weaponStats
                        }
                    } else {
                        val warframeStats = buildService.calculateWarframeStats(warframe, build)
                        _buildStats.value = warframeStats
                    }
                }
            } catch (e: Exception) {
                // Handle calculation error
            } finally {
                _isCalculating.value = false
            }
        }
    }

    private fun generateModSuggestions(build: Build) {
        viewModelScope.launch {
            try {
                val warframe = getWarframeById(build.warframeId)
                val weapon = build.weaponId?.let { getWeaponById(it) }
                
                if (warframe != null) {
                    val suggestions = buildService.suggestMods(warframe, weapon, BuildGoal.DAMAGE)
                    _modSuggestions.value = suggestions
                }
            } catch (e: Exception) {
                _modSuggestions.value = emptyList()
            }
        }
    }

    private fun createExampleBuilds() {
        val exampleBuilds = listOf(
            Build(
                id = "example_1",
                name = "Excalibur DPS",
                warframeId = "excalibur",
                weaponId = "braton_prime",
                mods = listOf(
                    BuildMod("vitality", 10, 0, Polarity.VAZARIN),
                    BuildMod("intensify", 5, 1, Polarity.MADURAI),
                    BuildMod("streamline", 5, 2, Polarity.NARAMON),
                    BuildMod("continuity", 5, 3, Polarity.MADURAI)
                ),
                formaCount = 1,
                notes = "Balanced build for general content",
                tags = listOf("beginner", "balanced"),
                author = "Example"
            ),
            Build(
                id = "example_2",
                name = "Rhino Tank",
                warframeId = "rhino",
                mods = listOf(
                    BuildMod("vitality", 10, 0, Polarity.VAZARIN),
                    BuildMod("steel_fiber", 10, 1, Polarity.VAZARIN),
                    BuildMod("redirection", 10, 2, Polarity.VAZARIN),
                    BuildMod("intensify", 5, 3, Polarity.MADURAI)
                ),
                formaCount = 2,
                notes = "Maximum survivability tank build",
                tags = listOf("tank", "survivability"),
                author = "Example"
            )
        )
        
        _builds.value = exampleBuilds
        saveBuilds(exampleBuilds)
    }

    private fun getSavedBuilds(): List<Build> {
        // Load from SharedPreferences or Room database
        return emptyList()
    }

    private fun saveBuilds(builds: List<Build>) {
        // Save to SharedPreferences or Room database
    }

    private fun getWarframeById(warframeId: String): Warframe? {
        return WarframeRepository.warframes.find { 
            it.name.lowercase().replace(" ", "_") == warframeId.lowercase()
        }
    }

    private fun getWeaponById(weaponId: String): Weapon? {
        return WarframeRepository.weapons.find { 
            it.name.lowercase().replace(" ", "_") == weaponId.lowercase()
        }
    }

    private fun generateBuildId(): String {
        return "build_${System.currentTimeMillis()}"
    }

    private fun generateBuildCode(build: Build): String {
        // Generate a shareable code for the build
        return "WF_BUILD_${build.id}_${build.name.hashCode()}"
    }
}