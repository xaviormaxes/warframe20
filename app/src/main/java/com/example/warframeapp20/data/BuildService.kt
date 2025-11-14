package com.example.warframeapp20.data

import kotlin.math.pow

/**
 * Service for build planning and optimization
 * Provides damage calculations, mod optimization, and build management
 */
class BuildService {
    
    companion object {
        // Base polarity costs for mod drain calculation
        private val POLARITY_COSTS = mapOf(
            Polarity.MADURAI to 1.0,
            Polarity.VAZARIN to 1.0,
            Polarity.NARAMON to 1.0,
            Polarity.ZENURIK to 1.0,
            Polarity.UNAIRU to 1.0,
            Polarity.PENJAGA to 1.0,
            Polarity.UMBRA to 1.0,
            Polarity.AURA to 1.0,
            Polarity.EXILUS to 1.0,
            Polarity.NONE to 2.0
        )
    }
    
    /**
     * Calculate total mod drain for a build
     */
    fun calculateModDrain(build: Build, warframe: Warframe): Int {
        val mods = getModsForBuild(build)
        var totalDrain = 0
        
        for (buildMod in build.mods) {
            val mod = mods.find { it.name == buildMod.modId }
            if (mod != null) {
                val modDrain = calculateModDrainAtRank(mod, buildMod.rank)
                val polarityMultiplier = if (mod.polarity == buildMod.polarity) 0.5 else 1.0
                totalDrain += (modDrain * polarityMultiplier).toInt()
            }
        }
        
        return totalDrain
    }
    
    /**
     * Calculate mod drain at specific rank
     */
    fun calculateModDrainAtRank(mod: ExtendedMod, rank: Int): Int {
        if (rank <= 0) return mod.baseDrain
        if (rank >= mod.maxRank) return mod.maxDrain
        
        val drainIncrease = mod.maxDrain - mod.baseDrain
        val drainPerRank = drainIncrease.toDouble() / mod.maxRank
        return mod.baseDrain + (drainPerRank * rank).toInt()
    }
    
    /**
     * Calculate damage output for a weapon build
     */
    fun calculateWeaponDamage(weapon: Weapon, build: Build): WeaponDamageStats {
        val mods = getModsForBuild(build)
        
        var baseDamage = weapon.damage.toDouble()
        var critChance = weapon.critChance
        var critMultiplier = weapon.critMultiplier
        var statusChance = weapon.statusChance
        var fireRate = weapon.fireRate
        var multishot = 1.0
        var elementalDamage = mutableMapOf<String, Double>()
        
        // Apply mod effects
        for (buildMod in build.mods) {
            val mod = mods.find { it.name == buildMod.modId }
            if (mod != null) {
                for (stat in mod.stats) {
                    val value = calculateStatValueAtRank(stat, buildMod.rank, mod.maxRank)
                    
                    when (stat.name.lowercase()) {
                        "damage" -> baseDamage *= (1 + value / 100)
                        "critical_chance" -> critChance += (value / 100).toFloat()
                        "critical_multiplier" -> critMultiplier += (value / 100).toFloat()
                        "status_chance" -> statusChance += (value / 100).toFloat()
                        "fire_rate" -> fireRate *= (1 + value / 100).toFloat()
                        "multishot" -> multishot += value / 100
                        "heat_damage" -> elementalDamage["heat"] = (elementalDamage["heat"] ?: 0.0) + (baseDamage * value / 100)
                        "cold_damage" -> elementalDamage["cold"] = (elementalDamage["cold"] ?: 0.0) + (baseDamage * value / 100)
                        "electric_damage" -> elementalDamage["electric"] = (elementalDamage["electric"] ?: 0.0) + (baseDamage * value / 100)
                        "toxin_damage" -> elementalDamage["toxin"] = (elementalDamage["toxin"] ?: 0.0) + (baseDamage * value / 100)
                    }
                }
            }
        }
        
        // Calculate final damage per shot
        val totalDamage = baseDamage + elementalDamage.values.sum()
        val critDamage = totalDamage * (1 + (critChance * (critMultiplier - 1)))
        val burstDamage = critDamage * multishot * fireRate
        
        return WeaponDamageStats(
            baseDamage = baseDamage,
            totalDamage = totalDamage,
            criticalDamage = critDamage,
            burstDPS = burstDamage,
            sustainedDPS = burstDamage * 0.8, // Account for reload time
            critChance = critChance,
            critMultiplier = critMultiplier,
            statusChance = statusChance,
            fireRate = fireRate,
            multishot = multishot,
            elementalDamage = elementalDamage
        )
    }
    
    /**
     * Calculate effective health for a Warframe build
     */
    fun calculateWarframeStats(warframe: Warframe, build: Build): WarframeStats {
        val mods = getModsForBuild(build)
        
        var health = warframe.health.toDouble()
        var shield = warframe.shield.toDouble()
        var armor = warframe.armor.toDouble()
        var energy = warframe.energy.toDouble()
        var power_strength = 100.0
        var power_efficiency = 100.0
        var power_duration = 100.0
        var power_range = 100.0
        
        // Apply mod effects
        for (buildMod in build.mods) {
            val mod = mods.find { it.name == buildMod.modId }
            if (mod != null) {
                for (stat in mod.stats) {
                    val value = calculateStatValueAtRank(stat, buildMod.rank, mod.maxRank)
                    
                    when (stat.name.lowercase()) {
                        "health" -> health *= (1 + value / 100)
                        "shield" -> shield *= (1 + value / 100)
                        "armor" -> armor *= (1 + value / 100)
                        "energy" -> energy *= (1 + value / 100)
                        "power_strength", "ability_strength" -> power_strength += value
                        "power_efficiency", "ability_efficiency" -> power_efficiency += value
                        "power_duration", "ability_duration" -> power_duration += value
                        "power_range", "ability_range" -> power_range += value
                    }
                }
            }
        }
        
        // Calculate effective health (EHP)
        val armorMultiplier = 1 + (armor / 300)
        val effectiveHealth = (health + shield) * armorMultiplier
        
        return WarframeStats(
            health = health.toInt(),
            shield = shield.toInt(),
            armor = armor.toInt(),
            energy = energy.toInt(),
            effectiveHealth = effectiveHealth.toInt(),
            powerStrength = power_strength,
            powerEfficiency = power_efficiency,
            powerDuration = power_duration,
            powerRange = power_range
        )
    }
    
    /**
     * Calculate stat value at specific rank
     */
    private fun calculateStatValueAtRank(stat: ModStat, rank: Int, maxRank: Int): Double {
        if (rank <= 0) return 0.0
        if (rank >= maxRank) return stat.value
        
        val rankRatio = rank.toDouble() / maxRank
        return stat.value * rankRatio
    }
    
    /**
     * Suggest optimal mods for a specific goal
     */
    fun suggestMods(warframe: Warframe, weapon: Weapon?, goal: BuildGoal): List<ModSuggestion> {
        val suggestions = mutableListOf<ModSuggestion>()
        val availableMods = getAllMods().filter { 
            it.type == when (weapon?.type) {
                WeaponType.PRIMARY -> ModType.PRIMARY
                WeaponType.SECONDARY -> ModType.SECONDARY
                WeaponType.MELEE -> ModType.MELEE
                null -> ModType.WARFRAME
            }
        }
        
        when (goal) {
            BuildGoal.DAMAGE -> {
                // Prioritize damage, crit, and multishot mods
                suggestions.addAll(availableMods.filter { mod ->
                    mod.stats.any { stat ->
                        stat.name.lowercase() in listOf("damage", "critical_chance", "critical_multiplier", "multishot")
                    }
                }.map { ModSuggestion(it, "High damage output", 9) })
            }
            BuildGoal.SURVIVABILITY -> {
                // Prioritize health, shield, and armor mods
                suggestions.addAll(availableMods.filter { mod ->
                    mod.stats.any { stat ->
                        stat.name.lowercase() in listOf("health", "shield", "armor")
                    }
                }.map { ModSuggestion(it, "Increased survivability", 8) })
            }
            BuildGoal.ABILITY_FOCUS -> {
                // Prioritize power stats and energy
                suggestions.addAll(availableMods.filter { mod ->
                    mod.stats.any { stat ->
                        stat.name.lowercase() in listOf("power_strength", "power_efficiency", "power_duration", "power_range", "energy")
                    }
                }.map { ModSuggestion(it, "Enhanced abilities", 8) })
            }
            BuildGoal.STATUS -> {
                // Prioritize status chance and elemental mods
                suggestions.addAll(availableMods.filter { mod ->
                    mod.stats.any { stat ->
                        stat.name.lowercase() in listOf("status_chance", "heat_damage", "cold_damage", "electric_damage", "toxin_damage")
                    }
                }.map { ModSuggestion(it, "Status effect focus", 7) })
            }
        }
        
        return suggestions.sortedByDescending { it.priority }.take(10)
    }
    
    /**
     * Validate if a build is possible with current capacity
     */
    fun validateBuild(build: Build, warframe: Warframe): BuildValidation {
        val totalDrain = calculateModDrain(build, warframe)
        val maxCapacity = 30 + build.formaCount * 2 // Base 30 + 2 per forma
        
        val issues = mutableListOf<String>()
        if (totalDrain > maxCapacity) {
            issues.add("Mod capacity exceeded by ${totalDrain - maxCapacity}")
        }
        
        val duplicateMods = build.mods.groupBy { it.modId }.filter { it.value.size > 1 }
        if (duplicateMods.isNotEmpty()) {
            issues.add("Duplicate mods: ${duplicateMods.keys.joinToString()}")
        }
        
        return BuildValidation(
            isValid = issues.isEmpty(),
            issues = issues,
            totalDrain = totalDrain,
            maxCapacity = maxCapacity,
            remainingCapacity = maxCapacity - totalDrain
        )
    }
    
    private fun getModsForBuild(build: Build): List<ExtendedMod> {
        // This would typically load from a database or cache
        // For now, return a basic set of common mods
        return getAllMods()
    }
    
    private fun getAllMods(): List<ExtendedMod> {
        return listOf(
            // Common Warframe mods
            ExtendedMod("Vitality", "Increases Health", ModType.WARFRAME, ModRarity.COMMON, 10, Polarity.VAZARIN, 4, 14, 
                listOf(ModStat("health", 440.0, true))),
            ExtendedMod("Redirection", "Increases Shield", ModType.WARFRAME, ModRarity.COMMON, 10, Polarity.VAZARIN, 4, 14,
                listOf(ModStat("shield", 440.0, true))),
            ExtendedMod("Steel Fiber", "Increases Armor", ModType.WARFRAME, ModRarity.COMMON, 10, Polarity.VAZARIN, 4, 14,
                listOf(ModStat("armor", 110.0, true))),
            ExtendedMod("Flow", "Increases Energy", ModType.WARFRAME, ModRarity.UNCOMMON, 5, Polarity.NARAMON, 6, 11,
                listOf(ModStat("energy", 150.0, true))),
            ExtendedMod("Intensify", "Increases Ability Strength", ModType.WARFRAME, ModRarity.COMMON, 5, Polarity.MADURAI, 6, 11,
                listOf(ModStat("power_strength", 30.0))),
            ExtendedMod("Streamline", "Increases Ability Efficiency", ModType.WARFRAME, ModRarity.COMMON, 5, Polarity.NARAMON, 6, 11,
                listOf(ModStat("power_efficiency", 30.0))),
            ExtendedMod("Continuity", "Increases Ability Duration", ModType.WARFRAME, ModRarity.COMMON, 5, Polarity.MADURAI, 6, 11,
                listOf(ModStat("power_duration", 30.0))),
            ExtendedMod("Stretch", "Increases Ability Range", ModType.WARFRAME, ModRarity.COMMON, 5, Polarity.NARAMON, 6, 11,
                listOf(ModStat("power_range", 45.0))),
            
            // Common weapon mods
            ExtendedMod("Serration", "Increases Damage", ModType.PRIMARY, ModRarity.COMMON, 10, Polarity.MADURAI, 4, 14,
                listOf(ModStat("damage", 165.0, true))),
            ExtendedMod("Split Chamber", "Multishot", ModType.PRIMARY, ModRarity.RARE, 5, Polarity.MADURAI, 6, 11,
                listOf(ModStat("multishot", 90.0, true))),
            ExtendedMod("Point Strike", "Critical Chance", ModType.PRIMARY, ModRarity.COMMON, 5, Polarity.NARAMON, 6, 11,
                listOf(ModStat("critical_chance", 150.0, true))),
            ExtendedMod("Vital Sense", "Critical Multiplier", ModType.PRIMARY, ModRarity.UNCOMMON, 5, Polarity.NARAMON, 6, 11,
                listOf(ModStat("critical_multiplier", 120.0, true)))
        )
    }
}

// Data classes moved to WarframeData.kt to avoid duplication

data class BuildValidation(
    val isValid: Boolean,
    val issues: List<String>,
    val totalDrain: Int,
    val maxCapacity: Int,
    val remainingCapacity: Int
)

enum class BuildGoal {
    DAMAGE, SURVIVABILITY, ABILITY_FOCUS, STATUS
}