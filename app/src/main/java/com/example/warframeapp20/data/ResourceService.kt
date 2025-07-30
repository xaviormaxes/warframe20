package com.example.warframeapp20.data

import kotlin.math.ceil

// Extended FarmingLocation with additional properties for service use
data class DetailedFarmingLocation(
    val name: String,
    val missionType: String,
    val efficiency: Double, // 0-5 rating
    val notes: String,
    val timePerRun: Double = 15.0, // minutes
    val averageDrops: Double = 1.0
)

/**
 * Service for resource tracking and farming optimization
 * Provides farming routes, resource management, and crafting optimization
 */
class ResourceService {

    companion object {
        // Common farming locations with efficiency ratings
        private val FARMING_LOCATIONS = mapOf(
            "neurodes" to listOf(
                DetailedFarmingLocation("Earth - Lua", "Survival", 4.5, "High-level enemies"),
                DetailedFarmingLocation("Eris - Akkad", "Defense", 4.0, "Good spawn rates"),
                DetailedFarmingLocation("Earth - E Prime", "Exterminate", 3.5, "Quick runs")
            ),
            "orokin_cells" to listOf(
                DetailedFarmingLocation("Saturn - Helene", "Defense", 4.8, "Boss + Defense waves"),
                DetailedFarmingLocation("Ceres - Seimeni", "Defense", 4.2, "Consistent drops"),
                DetailedFarmingLocation("Saturn - Titan", "Survival", 4.0, "Long runs")
            ),
            "neural_sensors" to listOf(
                DetailedFarmingLocation("Jupiter - Cameria", "Survival", 4.6, "Alad V area"),
                DetailedFarmingLocation("Jupiter - Io", "Defense", 4.3, "Fast waves"),
                DetailedFarmingLocation("Jupiter - Sinai", "Defense", 4.0, "Alternative")
            ),
            "control_modules" to listOf(
                DetailedFarmingLocation("Neptune - Psamathe", "Assassination", 4.7, "Hyena Pack"),
                DetailedFarmingLocation(
                    "Europa - Cholistan",
                    "Excavation",
                    4.2,
                    "Multiple extractors"
                ),
                DetailedFarmingLocation("Void - Mot", "Survival", 4.0, "Corrupted enemies")
            ),
            "morphics" to listOf(
                DetailedFarmingLocation("Mars - Wahiba", "Survival", 4.4, "Mercury enemies"),
                DetailedFarmingLocation("Mercury - Lares", "Defense", 4.1, "Boss proximity"),
                DetailedFarmingLocation("Phobos - Zeugma", "Survival", 3.8, "Alternative location")
            ),
            "alloy_plate" to listOf(
                DetailedFarmingLocation("Venus - Kiliken", "Excavation", 4.9, "High efficiency"),
                DetailedFarmingLocation("Phobos - Stickney", "Survival", 4.5, "Long runs"),
                DetailedFarmingLocation("Ceres - Gabii", "Survival", 4.2, "Dark Sector bonus")
            ),
            "polymer_bundle" to listOf(
                DetailedFarmingLocation("Mercury - Lares", "Defense", 4.6, "High drop rate"),
                DetailedFarmingLocation("Venus - Malva", "Survival", 4.3, "Consistent farming"),
                DetailedFarmingLocation("Uranus - Assur", "Survival", 4.0, "Dark Sector")
            )
        )

        // Relic farming locations
        private val RELIC_FARMING = mapOf(
            "lith" to listOf(
                DetailedFarmingLocation("Earth - Hepit", "Capture", 4.8, "Fast A rotation"),
                DetailedFarmingLocation("Mercury - Lares", "Defense", 4.5, "Waves 5-10"),
                DetailedFarmingLocation("Venus - Tessera", "Defense", 4.2, "Alternative")
            ),
            "meso" to listOf(
                DetailedFarmingLocation("Mars - Kadesh", "Defense", 4.7, "Waves 10-15"),
                DetailedFarmingLocation("Ceres - Seimeni", "Defense", 4.4, "Consistent drops"),
                DetailedFarmingLocation("Void - Hepit", "Capture", 4.0, "Quick runs")
            ),
            "neo" to listOf(
                DetailedFarmingLocation("Pluto - Hieracon", "Excavation", 4.9, "A rotation"),
                DetailedFarmingLocation("Sedna - Hydron", "Defense", 4.6, "Waves 15-20"),
                DetailedFarmingLocation("Eris - Xini", "Interception", 4.3, "B rotation")
            ),
            "axi" to listOf(
                DetailedFarmingLocation("Pluto - Hieracon", "Excavation", 4.8, "A&B rotation"),
                DetailedFarmingLocation("Sedna - Berehynia", "Interception", 4.5, "C rotation"),
                DetailedFarmingLocation("Lua - Apollo", "Disruption", 4.3, "Round C")
            )
        )
    }

    /**
     * Get optimal farming routes for specific resources
     */
    fun getFarmingRoute(resourceNeeds: List<CraftingRequirement>): FarmingRoute {
        val routes = mutableListOf<FarmingStop>()
        var totalEstimatedTime = 0.0

        for (requirement in resourceNeeds.filter { !it.isComplete }) {
            val locations = FARMING_LOCATIONS[requirement.resourceId.lowercase()]
            if (locations != null) {
                val bestLocation = locations.maxByOrNull { it.efficiency }
                if (bestLocation != null) {
                    val estimatedRuns =
                        ceil(requirement.remaining / bestLocation.averageDrops).toInt()
                    val timeMinutes = estimatedRuns * bestLocation.timePerRun

                    routes.add(
                        FarmingStop(
                            location = bestLocation.name,
                            resourceName = requirement.resourceId,
                            estimatedTime = timeMinutes.toInt(),
                            efficiency = bestLocation.efficiency,
                            notes = bestLocation.notes
                        )
                    )
                    totalEstimatedTime += timeMinutes
                }
            }
        }

        // Sort by efficiency and group nearby locations
        val optimizedRoutes = optimizeFarmingOrder(routes)

        return FarmingRoute(
            id = "auto_${System.currentTimeMillis()}",
            name = "Optimized Farming Route",
            stops = optimizedRoutes,
            totalEstimatedTime = totalEstimatedTime.toInt(),
            efficiency = calculateRouteEfficiency(optimizedRoutes),
            targetResource = resourceNeeds.firstOrNull()?.resourceId ?: "mixed"
        )
    }

    /**
     * Get resource inventory with current status
     */
    fun getResourceInventory(): List<ResourceInventoryItem> {
        // This would typically load from local storage or game API
        return getAllResources().map { resource ->
            val currentAmount = getCurrentResourceAmount(resource.name)
            val needed = getResourceNeeded(resource.name)

            ResourceInventoryItem(
                resource = resource,
                currentAmount = currentAmount,
                targetAmount = needed,
                deficit = maxOf(0, needed - currentAmount),
                surplus = maxOf(0, currentAmount - needed)
            )
        }
    }

    /**
     * Calculate optimal resource allocation for multiple projects
     */
    fun optimizeResourceAllocation(projects: List<CraftingProject>): ResourceAllocation {
        val allRequirements = projects.flatMap { project ->
            project.requirements.map { req -> req.copy() }
        }

        val resourceGroups = allRequirements.groupBy { it.resourceId }
        val prioritizedResources = mutableListOf<ResourcePriority>()

        for ((resourceId, requirements) in resourceGroups) {
            val totalNeeded = requirements.sumOf { it.quantity }
            val totalObtained = requirements.sumOf { it.obtained }
            val projectCount = requirements.size

            val priority = when {
                projectCount >= 3 -> 5 // High priority for widely needed resources
                totalNeeded >= 50 -> 4 // High quantity needed
                totalNeeded >= 20 -> 3 // Medium quantity
                totalNeeded >= 5 -> 2 // Low quantity
                else -> 1 // Very low priority
            }

            prioritizedResources.add(
                ResourcePriority(
                    resourceId = resourceId,
                    totalNeeded = totalNeeded,
                    totalObtained = totalObtained,
                    projectCount = projectCount,
                    priority = priority,
                    farmingRoute = getFarmingRoute(
                        listOf(
                            CraftingRequirement(
                                resourceId,
                                totalNeeded - totalObtained,
                                totalObtained
                            )
                        )
                    )
                )
            )
        }

        return ResourceAllocation(
            totalDeficit = prioritizedResources.sumOf { it.totalNeeded - it.totalObtained },
            surplusResources = emptyList(), // TODO: Calculate surplus resources
            recommendations = generateAllocationRecommendations(prioritizedResources)
        )
    }

    /**
     * Get relic farming recommendations
     */
    fun getRelicFarmingGuide(relicType: String, quantity: Int): RelicFarmingGuide {
        val locations = RELIC_FARMING[relicType.lowercase()] ?: emptyList()
        val bestLocation = locations.maxByOrNull { it.efficiency }

        return RelicFarmingGuide(
            relicType = relicType,
            recommendedLocation = bestLocation,
            alternativeLocations = locations.filter { it != bestLocation },
            estimatedRuns = if (bestLocation != null) ceil(quantity / bestLocation.averageDrops).toInt() else 0,
            estimatedTime = if (bestLocation != null) {
                ceil(quantity / bestLocation.averageDrops * bestLocation.timePerRun).toInt()
            } else 0
        )
    }

    /**
     * Track resource progress toward goals
     */
    fun updateResourceProgress(resourceId: String, newAmount: Int): ResourceProgress {
        val oldAmount = getCurrentResourceAmount(resourceId)
        val goals = getActiveResourceGoals(resourceId)

        return ResourceProgress(
            resourceId = resourceId,
            oldAmount = oldAmount,
            newAmount = newAmount,
            difference = newAmount - oldAmount,
            completedGoals = goals.filter { newAmount >= it.targetAmount && oldAmount < it.targetAmount },
            remainingGoals = goals.filter { newAmount < it.targetAmount }
        )
    }

    /**
     * Generate farming efficiency tips
     */
    fun getFarmingTips(resourceId: String): List<FarmingTip> {
        return when (resourceId.lowercase()) {
            "neurodes" -> listOf(
                FarmingTip(
                    "1",
                    "Bring Nekros",
                    "Increases drop chances with Desecrate",
                    "neurodes",
                    "Medium"
                ),
                FarmingTip(
                    "2",
                    "Use resource boosters",
                    "Doubles resource gains",
                    "neurodes",
                    "Easy"
                ),
                FarmingTip(
                    "3",
                    "Target heavy units",
                    "Bombards and Heavy Gunners have higher drop rates",
                    "neurodes",
                    "Medium"
                )
            )

            "orokin_cells" -> listOf(
                FarmingTip(
                    "4",
                    "Assassination missions",
                    "Bosses guarantee drops",
                    "orokin_cells",
                    "Easy"
                ),
                FarmingTip(
                    "5",
                    "Saturn locations",
                    "Best planet for cell farming",
                    "orokin_cells",
                    "Easy"
                ),
                FarmingTip(
                    "6",
                    "Resource booster + Smeeta",
                    "Maximum efficiency combo",
                    "orokin_cells",
                    "Medium"
                )
            )

            else -> listOf(
                FarmingTip(
                    "7",
                    "Check drop tables",
                    "Verify best sources for your target",
                    "general",
                    "Easy"
                ),
                FarmingTip(
                    "8",
                    "Use appropriate Warframes",
                    "Some frames boost resource gains",
                    "general",
                    "Medium"
                )
            )
        }
    }

    private fun optimizeFarmingOrder(routes: List<FarmingStop>): List<FarmingStop> {
        // Simple optimization: prioritize by efficiency and group by planet
        return routes.sortedWith(
            compareByDescending<FarmingStop> { it.efficiency }
                .thenBy { it.estimatedTime }
        )
    }

    private fun calculateRouteEfficiency(routes: List<FarmingStop>): Double {
        if (routes.isEmpty()) return 0.0
        return routes.map { it.efficiency }.average()
    }

    private fun calculateFarmingPriority(resource: Resource, current: Int, needed: Int): Int {
        return when {
            needed > current * 2 -> 5 // Critically low
            needed > current -> 4 // Low stock
            needed > current * 0.5 -> 3 // Moderate stock
            needed > 0 -> 2 // Adequate stock
            else -> 1 // Surplus
        }
    }

    private fun getCurrentResourceAmount(resourceId: String): Int {
        // This would typically load from local storage or game API
        return (0..1000).random()
    }

    private fun getResourceNeeded(resourceId: String): Int {
        // This would calculate based on active crafting projects
        return (0..100).random()
    }

    private fun getActiveResourceGoals(resourceId: String): List<ResourceGoal> {
        // This would load from user's active projects
        return emptyList()
    }

    private fun generateAllocationRecommendations(resources: List<ResourcePriority>): List<String> {
        val recommendations = mutableListOf<String>()

        val highPriorityResources = resources.filter { it.priority >= 4 }
        if (highPriorityResources.isNotEmpty()) {
            recommendations.add("Focus on ${highPriorityResources.size} high-priority resources first")
        }

        val widelyNeeded = resources.filter { it.projectCount >= 3 }
        if (widelyNeeded.isNotEmpty()) {
            recommendations.add("Prioritize ${widelyNeeded.first().resourceId} - needed by ${widelyNeeded.first().projectCount} projects")
        }

        val quickFarms = resources.filter { it.farmingRoute.totalEstimatedTime <= 30 }
        if (quickFarms.isNotEmpty()) {
            recommendations.add("Quick farms available for ${quickFarms.size} resources (under 30 min)")
        }

        return recommendations
    }

    private fun getAllResources(): List<Resource> {
        return listOf(
            Resource(
                "Neurodes", "Rare neural processing unit", ResourceType.RARE, ResourceRarity.RARE,
                listOf("Earth", "Lua", "Eris"),
                listOf(DropSource("Earth - Lua", "enemy", "Heavy Units", 0.03, 0.5))
            ),
            Resource(
                "Orokin Cells", "Ancient energy source", ResourceType.RARE, ResourceRarity.RARE,
                listOf("Saturn", "Ceres"),
                listOf(DropSource("Saturn - Helene", "mission", "Defense", 0.05, 1.2))
            ),
            Resource(
                "Neural Sensors",
                "Sensory processing component",
                ResourceType.RARE,
                ResourceRarity.RARE,
                listOf("Jupiter"),
                listOf(DropSource("Jupiter - Io", "mission", "Defense", 0.04, 0.8))
            ),
            Resource(
                "Control Modules",
                "Advanced control system",
                ResourceType.RARE,
                ResourceRarity.RARE,
                listOf("Neptune", "Europa", "Void"),
                listOf(DropSource("Neptune - Psamathe", "boss", "Hyena Pack", 0.15, 2.0))
            ),
            Resource(
                "Morphics", "Adaptive material", ResourceType.RARE, ResourceRarity.RARE,
                listOf("Mars", "Mercury", "Phobos"),
                listOf(DropSource("Mars - Wahiba", "enemy", "All enemies", 0.02, 0.3))
            )
        )
    }
}

// Supporting data classes - unique to ResourceService
data class ResourcePriority(
    val resourceId: String,
    val totalNeeded: Int,
    val totalObtained: Int,
    val projectCount: Int,
    val priority: Int,
    val farmingRoute: FarmingRoute
)

data class RelicFarmingGuide(
    val relicType: String,
    val recommendedLocation: DetailedFarmingLocation?,
    val alternativeLocations: List<DetailedFarmingLocation>,
    val estimatedRuns: Int,
    val estimatedTime: Int
)

data class ResourceProgress(
    val resourceId: String,
    val oldAmount: Int,
    val newAmount: Int,
    val difference: Int,
    val completedGoals: List<ResourceGoal>,
    val remainingGoals: List<ResourceGoal>
)

data class ResourceGoal(
    val id: String,
    val resourceId: String,
    val targetAmount: Int,
    val purpose: String
)

enum class TipType {
    WARFRAME, WEAPON, BOOSTER, STRATEGY, MISSION, LOCATION, COMBO, GENERAL
}