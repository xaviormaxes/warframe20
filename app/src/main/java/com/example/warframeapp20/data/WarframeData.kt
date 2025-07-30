package com.example.warframeapp20.data

import androidx.annotation.DrawableRes
import com.example.warframeapp20.R

// Warframe Data Classes
data class Warframe(
    val name: String,
    val description: String,
    val health: Int,
    val shield: Int,
    val armor: Int,
    val energy: Int,
    val abilities: List<Ability>,
    @DrawableRes val imageRes: Int = R.drawable.ic_warframe_24dp,
    val isPrime: Boolean = false,
    val mastery: Int = 0
)

data class Ability(
    val name: String,
    val description: String,
    val energyCost: Int,
    @DrawableRes val iconRes: Int = R.drawable.ic_warframe_24dp
)

data class Weapon(
    val name: String,
    val type: WeaponType,
    val damage: Int,
    val critChance: Float,
    val critMultiplier: Float,
    val statusChance: Float,
    val fireRate: Float,
    val accuracy: Float,
    val magazine: Int,
    val reload: Float,
    val mastery: Int,
    @DrawableRes val imageRes: Int = R.drawable.ic_weapon_24dp,
    val isPrime: Boolean = false
)

enum class WeaponType {
    PRIMARY, SECONDARY, MELEE
}

data class Companion(
    val name: String,
    val type: CompanionType,
    val health: Int,
    val shield: Int,
    val armor: Int,
    val abilities: List<String>,
    @DrawableRes val imageRes: Int = R.drawable.ic_companion_24dp,
    val mastery: Int = 0
)

enum class CompanionType {
    SENTINEL, KUBROW, KAVAT, MOA, HOUND, PREDASITE, VULPAPHYLA
}

data class Mod(
    val name: String,
    val description: String,
    val type: ModType,
    val rarity: ModRarity,
    val maxRank: Int,
    val polarity: Polarity,
    @DrawableRes val imageRes: Int = R.drawable.ic_settings_24dp
)

enum class ModType {
    WARFRAME, PRIMARY, SECONDARY, MELEE, COMPANION, ARCHWING, NECRAMECH
}

enum class ModRarity {
    COMMON, UNCOMMON, RARE, LEGENDARY, RIVEN, PRIMED, AMALGAM, AUGMENT
}

enum class Polarity {
    MADURAI, VAZARIN, NARAMON, ZENURIK, UNAIRU, PENJAGA, UMBRA, AURA, EXILUS, NONE
}

// World State Data Classes
data class Alert(
    val id: String,
    val mission: Mission,
    val reward: Reward,
    val expiry: String,
    val eta: String,
    val isExpired: Boolean = false
)

data class Invasion(
    val id: String,
    val node: String,
    val attackerReward: Reward,
    val defenderReward: Reward,
    val completion: Float,
    val eta: String,
    val vsInfestation: Boolean
)

data class Fissure(
    val id: String,
    val node: String,
    val missionType: String,
    val enemy: String,
    val tier: String,
    val tierNum: Int,
    val activation: String,
    val expiry: String,
    val eta: String,
    val isHard: Boolean = false,
    val isStorm: Boolean = false
)

data class Sortie(
    val id: String,
    val boss: String,
    val faction: String,
    val missions: List<SortieMission>,
    val expiry: String,
    val eta: String
)

data class SortieMission(
    val node: String,
    val missionType: String,
    val modifier: String,
    val modifierDescription: String
)

data class Mission(
    val node: String,
    val type: String,
    val faction: String,
    val minEnemyLevel: Int,
    val maxEnemyLevel: Int
)

data class Reward(
    val items: List<String> = emptyList(),
    val credits: Int = 0,
    val asString: String = ""
)

data class NightwaveChallenge(
    val id: String,
    val title: String,
    val desc: String,
    val reputation: Int,
    val isDaily: Boolean,
    val isElite: Boolean
)

data class CetusCycle(
    val isDay: Boolean,
    val timeLeft: String,
    val expiry: String
)

data class CambionCycle(
    val active: String,
    val timeLeft: String,
    val expiry: String
)

data class VallisCycle(
    val isWarm: Boolean,
    val timeLeft: String,
    val expiry: String = ""
)

data class InvasionReward(
    val attackerReward: String,
    val defenderReward: String
)

// Event Data
data class Event(
    val id: String,
    val description: String,
    val tooltip: String,
    val node: String,
    val rewards: List<Reward>,
    val health: String,
    val expiry: String,
    val maximumScore: Int?,
    val currentScore: Int?,
    val isActive: Boolean
)

// Market Data Classes
data class MarketItem(
    val id: String,
    val urlName: String,
    val itemName: String,
    val thumb: String,
    val subItems: List<MarketSubItem> = emptyList()
)

data class MarketSubItem(
    val id: String,
    val urlName: String,
    val itemName: String,
    val ducats: Int? = null,
    val tags: List<String> = emptyList()
)

data class MarketOrder(
    val id: String,
    val platinum: Int,
    val quantity: Int,
    val orderType: String, // "sell" or "buy"
    val platform: String,
    val region: String,
    val user: MarketUser,
    val visible: Boolean,
    val lastSeen: String,
    val creationDate: String
) : java.io.Serializable

data class MarketUser(
    val id: String,
    val ingameName: String,
    val status: String, // "online", "offline", "ingame"
    val region: String,
    val reputation: Int,
    val avatar: String? = null
) : java.io.Serializable

data class MarketStatistics(
    val closed90Days: List<MarketClosedOrder>,
    val closed48Hours: List<MarketClosedOrder>
) : java.io.Serializable

data class MarketClosedOrder(
    val datetime: String,
    val volume: Int,
    val minPrice: Int,
    val maxPrice: Int,
    val avgPrice: Double,
    val waPrice: Double,
    val median: Double
) : java.io.Serializable

// Build Planning Data Classes
data class ExtendedMod(
    val name: String,
    val description: String,
    val type: ModType,
    val rarity: ModRarity,
    val maxRank: Int,
    val polarity: Polarity,
    val baseDrain: Int,
    val maxDrain: Int,
    val stats: List<ModStat>,
    @DrawableRes val imageRes: Int = R.drawable.ic_settings_24dp
)

data class ModStat(
    val name: String,
    val value: Double,
    val isPercentage: Boolean = false
)

data class Build(
    val id: String,
    val name: String,
    val warframeId: String,
    val weaponId: String? = null,
    val mods: List<BuildMod>,
    val formaCount: Int = 0,
    val notes: String = "",
    val tags: List<String> = emptyList(),
    val author: String = "",
    val lastModified: Long = System.currentTimeMillis(),
    val isPublic: Boolean = false,
    val likes: Int = 0
) : java.io.Serializable

data class BuildMod(
    val modId: String,
    val rank: Int,
    val slot: Int,
    val polarity: Polarity
) : java.io.Serializable

// Resource Management Data Classes
data class Resource(
    val name: String,
    val description: String,
    val type: ResourceType,
    val rarity: ResourceRarity,
    val locations: List<String>,
    val dropSources: List<DropSource>,
    @DrawableRes val imageRes: Int = R.drawable.ic_settings_24dp
)

data class DropSource(
    val location: String,
    val sourceType: String, // "mission", "enemy", "container", "plant"
    val sourceName: String,
    val dropChance: Double,
    val averagePerRun: Double
)

data class CraftingRequirement(
    val resourceId: String,
    val quantity: Int,
    val obtained: Int = 0
) {
    val remaining: Int get() = maxOf(0, quantity - obtained)
    val isComplete: Boolean get() = obtained >= quantity
}

data class CraftingProject(
    val id: String,
    val name: String,
    val type: String, // "warframe", "weapon", "item"
    val requirements: List<CraftingRequirement>,
    val buildTime: Long, // in milliseconds
    val credits: Int,
    val marketable: Boolean = true,
    val description: String = ""
) {
    val totalProgress: Double get() {
        if (requirements.isEmpty()) return 1.0
        return requirements.sumOf { it.obtained.toDouble() / it.quantity } / requirements.size
    }
    val isComplete: Boolean get() = requirements.all { it.isComplete }
}

// Foundry Management
data class FoundryItem(
    val id: String,
    val name: String,
    val type: String,
    val startTime: Long,
    val completionTime: Long,
    val rushed: Boolean = false
) {
    val remainingTime: Long get() = maxOf(0, completionTime - System.currentTimeMillis())
    val isComplete: Boolean get() = remainingTime <= 0
    val progressPercentage: Double get() {
        val totalTime = completionTime - startTime
        val elapsed = System.currentTimeMillis() - startTime
        return (elapsed.toDouble() / totalTime).coerceIn(0.0, 1.0)
    }
}

// Clan Management Data Classes
data class Clan(
    val id: String,
    val name: String,
    val tag: String,
    val tier: Int,
    val memberCount: Int,
    val maxMembers: Int,
    val alliance: String? = null,
    val description: String = "",
    val emblem: String? = null,
    val created: Long,
    val lastActive: Long
)

data class ClanMember(
    val id: String,
    val name: String,
    val rank: String,
    val joinDate: Long,
    val lastSeen: Long,
    val contributionPoints: Int,
    val isOnline: Boolean
)

data class ClanResearch(
    val id: String,
    val name: String,
    val lab: String,
    val progress: Double,
    val contributors: List<ClanMember>,
    val requirements: List<CraftingRequirement>,
    val startTime: Long,
    val estimatedCompletion: Long?
)

// Riven Management
data class RivenMod(
    val id: String,
    val weaponName: String,
    val weaponType: WeaponType,
    val mastery: Int,
    val rerolls: Int,
    val polarity: Polarity,
    val stats: List<RivenStat>,
    val curse: RivenStat? = null,
    val estimatedValue: IntRange? = null
)

data class RivenStat(
    val name: String,
    val value: Double,
    val isPositive: Boolean,
    val isPercentage: Boolean = false
)

// Additional Enums

enum class ResourceType {
    COMMON, UNCOMMON, RARE, PRIME_PART, BLUEPRINT, RELIC, ORB_VALLIS, PLAINS_OF_EIDOLON, DEIMOS
}

enum class ResourceRarity {
    COMMON, UNCOMMON, RARE, LEGENDARY
}

// Data Repositories
object WarframeRepository {
    val warframes = listOf(
        Warframe(
            "Excalibur",
            "A master of blade and gun, Excalibur is a balanced Warframe perfect for new Tenno.",
            health = 100,
            shield = 100,
            armor = 225,
            energy = 100,
            abilities = listOf(
                Ability("Slash Dash", "Dash forward, slashing enemies with your sword", 25),
                Ability("Radial Blind", "Emit a blinding flash, stunning nearby enemies", 50),
                Ability("Radial Javelin", "Launch javelins in all directions", 75),
                Ability("Exalted Blade", "Summon an ethereal sword", 25)
            ),
            mastery = 0
        ),
        Warframe(
            "Rhino",
            "Rhino is the heaviest assault Warframe, capable of devastating damage and incredible durability.",
            health = 100,
            shield = 150,
            armor = 190,
            energy = 100,
            abilities = listOf(
                Ability("Rhino Charge", "Charge forward, ramming enemies", 25),
                Ability("Iron Skin", "Become invulnerable for a duration", 50),
                Ability("Roar", "Boost damage of you and nearby allies", 75),
                Ability("Rhino Stomp", "Create a devastating shockwave", 100)
            ),
            mastery = 2
        ),
        Warframe(
            "Rhino Prime",
            "The Prime variant of Rhino, with enhanced stats and gold accents.",
            health = 130,
            shield = 150,
            armor = 225,
            energy = 100,
            abilities = listOf(
                Ability("Rhino Charge", "Charge forward, ramming enemies", 25),
                Ability("Iron Skin", "Become invulnerable for a duration", 50),
                Ability("Roar", "Boost damage of you and nearby allies", 75),
                Ability("Rhino Stomp", "Create a devastating shockwave", 100)
            ),
            isPrime = true,
            mastery = 2
        ),
        Warframe(
            "Mag",
            "Manipulator of magnetic forces, Mag uses her powers to crush and control enemies.",
            health = 75,
            shield = 150,
            armor = 65,
            energy = 100,
            abilities = listOf(
                Ability("Pull", "Yank enemies towards you with magnetic force", 25),
                Ability("Magnetize", "Create a magnetic field that attracts projectiles", 50),
                Ability("Polarize", "Strip enemy armor and shields", 75),
                Ability("Crush", "Compress enemies with magnetic force", 100)
            ),
            mastery = 0
        ),
        Warframe(
            "Volt",
            "Harness the power of electricity to shock and stun your enemies.",
            health = 100,
            shield = 150,
            armor = 15,
            energy = 100,
            abilities = listOf(
                Ability("Shock", "Launch an electric projectile", 25),
                Ability("Speed", "Increase movement and attack speed", 50),
                Ability("Electric Shield", "Create a protective electric barrier", 75),
                Ability("Discharge", "Emit electricity in all directions", 100)
            ),
            mastery = 0
        )
    )

    val weapons = listOf(
        Weapon(
            "Braton",
            WeaponType.PRIMARY,
            damage = 24,
            critChance = 0.08f,
            critMultiplier = 1.6f,
            statusChance = 0.18f,
            fireRate = 8.8f,
            accuracy = 28.6f,
            magazine = 60,
            reload = 2.0f,
            mastery = 0
        ),
        Weapon(
            "Braton Prime",
            WeaponType.PRIMARY,
            damage = 30,
            critChance = 0.12f,
            critMultiplier = 2.0f,
            statusChance = 0.24f,
            fireRate = 8.8f,
            accuracy = 28.6f,
            magazine = 75,
            reload = 2.0f,
            mastery = 8,
            isPrime = true
        ),
        Weapon(
            "Soma",
            WeaponType.PRIMARY,
            damage = 12,
            critChance = 0.30f,
            critMultiplier = 3.0f,
            statusChance = 0.07f,
            fireRate = 12.5f,
            accuracy = 28.6f,
            magazine = 100,
            reload = 3.0f,
            mastery = 6
        )
    )

    val companions = listOf(
        Companion(
            "Carrier",
            CompanionType.SENTINEL,
            health = 200,
            shield = 100,
            armor = 50,
            abilities = listOf("Vacuum", "Ammo Case", "Looter"),
            mastery = 0
        ),
        Companion(
            "Shade",
            CompanionType.SENTINEL,
            health = 200,
            shield = 50,
            armor = 50,
            abilities = listOf("Ghost", "Revenge"),
            mastery = 0
        ),
        Companion(
            "Huras Kubrow",
            CompanionType.KUBROW,
            health = 300,
            shield = 0,
            armor = 100,
            abilities = listOf("Stalk", "Hunt"),
            mastery = 0
        )
    )
}

// Resource Inventory Management
data class ResourceInventoryItem(
    val resource: Resource,
    val currentAmount: Int,
    val targetAmount: Int = 0,
    val deficit: Int = 0,
    val surplus: Int = 0
) {
    val isLow: Boolean get() = currentAmount < targetAmount
}

// Market Data Classes (moved from MarketViewModel and MarketService)
data class ItemDetails(
    val itemName: String,
    val orders: List<MarketOrder>,
    val statistics: MarketStatistics?,
    val quickPrice: QuickPrice?,
    val bestSellOrders: List<MarketOrder>,
    val bestBuyOrders: List<MarketOrder>
) : java.io.Serializable

data class PriceAlert(
    val id: String,
    val itemName: String,
    val targetPrice: Int,
    val alertType: AlertType,
    val isActive: Boolean,
    val createdAt: Long,
    val triggeredAt: Long? = null
)

enum class AlertType {
    PRICE_DROP, PRICE_RISE
}

data class QuickPrice(
    val itemName: String,
    val lowestSell: Int?,
    val highestBuy: Int?,
    val averageSell: Int?,
    val averageBuy: Int?,
    val lastUpdated: Long
) : java.io.Serializable {
    val spread: Int? get() = if (lowestSell != null && highestBuy != null) {
        lowestSell - highestBuy
    } else null
    
    val profitMargin: Double? get() = if (lowestSell != null && highestBuy != null && highestBuy > 0) {
        ((lowestSell - highestBuy).toDouble() / highestBuy) * 100
    } else null
}

data class TrendingItem(
    val itemName: String,
    val currentPrice: Int,
    val priceChange: Double, // percentage
    val volume: Int
) {
    val isRising: Boolean get() = priceChange > 0
    val isFalling: Boolean get() = priceChange < 0
}

// Additional data classes for ResourcesFragment
data class FarmingStop(
    val location: String,
    val resourceName: String,
    val estimatedTime: Int, // minutes
    val efficiency: Double,
    val notes: String = ""
)

data class FarmingRoute(
    val id: String,
    val name: String,
    val stops: List<FarmingStop>,
    val totalEstimatedTime: Int,
    val efficiency: Double,
    val targetResource: String
)

data class ResourceAllocation(
    val totalDeficit: Int,
    val surplusResources: List<String>,
    val recommendations: List<String>
)

data class FarmingTip(
    val id: String,
    val title: String,
    val description: String,
    val resourceType: String,
    val difficulty: String // "Easy", "Medium", "Hard"
) : java.io.Serializable

data class FarmingLocation(
    val location: String,
    val missionType: String,
    val efficiency: Double,
    val notes: String = ""
)

// Build Stats Data Classes
data class WeaponDamageStats(
    val baseDamage: Double,
    val totalDamage: Double,
    val criticalDamage: Double,
    val burstDPS: Double,
    val sustainedDPS: Double,
    val critChance: Float,
    val critMultiplier: Float,
    val statusChance: Float,
    val fireRate: Float,
    val multishot: Double = 1.0,
    val elementalDamage: Map<String, Double> = emptyMap(),
    val statusDamage: Double = 0.0
)

data class WarframeStats(
    val health: Int,
    val shield: Int,
    val armor: Int,
    val energy: Int,
    val effectiveHealth: Int,
    val powerStrength: Double,
    val powerEfficiency: Double,
    val powerDuration: Double = 100.0,
    val powerRange: Double = 100.0
)

data class ModSuggestion(
    val mod: ExtendedMod,
    val reason: String,
    val priority: Int,
    val damageIncrease: Double = 0.0
)