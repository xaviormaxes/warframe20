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
        ),
        Warframe(
            "Wisp",
            "Wisp floats between the material and the ethereal. She accesses multiple dimensions to support allies.",
            health = 100,
            shield = 175,
            armor = 175,
            energy = 200,
            abilities = listOf(
                Ability("Reservoirs", "Create health, speed, or shock motes", 50),
                Ability("Wil-O-Wisp", "Open a portal to summon a decoy", 50),
                Ability("Breach Surge", "Open breaches to the sun and blind enemies", 75),
                Ability("Sol Gate", "Open a portal to the sun to burn enemies", 100)
            ),
            mastery = 0
        ),
        Warframe(
            "Mesa",
            "With lethal pistol skills, Mesa is the fastest draw in the system.",
            health = 125,
            shield = 75,
            armor = 65,
            energy = 112,
            abilities = listOf(
                Ability("Ballistic Battery", "Charge a defensive battery", 25),
                Ability("Shooting Gallery", "Stun nearby enemies", 50),
                Ability("Shatter Shield", "Reduce incoming ranged damage", 50),
                Ability("Peacemaker", "Draw dual pistols with perfect accuracy", 25)
            ),
            mastery = 2
        ),
        Warframe(
            "Saryn",
            "Saryn is a master of poison and contagion, spreading death with every touch.",
            health = 125,
            shield = 100,
            armor = 225,
            energy = 150,
            abilities = listOf(
                Ability("Spores", "Spread toxin spores that burst on contact", 25),
                Ability("Molt", "Shed skin to create a decoy", 50),
                Ability("Toxic Lash", "Infuse melee weapon with toxin", 75),
                Ability("Miasma", "Release a noxious cloud of spores", 100)
            ),
            mastery = 0
        ),
        Warframe(
            "Octavia",
            "Compose her song and then conduct the deadly chorus. Octavia is music and destruction.",
            health = 100,
            shield = 100,
            armor = 125,
            energy = 225,
            abilities = listOf(
                Ability("Mallet", "Deploy rhythmic beats to damage enemies", 25),
                Ability("Resonator", "Deploy a roaming ball that charms enemies", 50),
                Ability("Metronome", "Grant buffs to allies matching the beat", 75),
                Ability("Amp", "Amplify all sound damage in the area", 100)
            ),
            mastery = 0
        ),
        Warframe(
            "Protea",
            "Manipulate time to support allies and damage enemies. Protea is a temporal engineer.",
            health = 100,
            shield = 50,
            armor = 175,
            energy = 175,
            abilities = listOf(
                Ability("Grenade Fan", "Throw grenades that deal damage", 25),
                Ability("Blaze Artillery", "Deploy an auto-turret", 50),
                Ability("Dispensary", "Create a source of energy, health, and ammo", 75),
                Ability("Temporal Anchor", "Rewind time to restore health and energy", 75)
            ),
            mastery = 7
        ),
        Warframe(
            "Revenant",
            "Control the minds of enemies and become an avatar of sentient power.",
            health = 100,
            shield = 225,
            armor = 105,
            energy = 150,
            abilities = listOf(
                Ability("Enthrall", "Convert enemies into thralls", 25),
                Ability("Mesmer Skin", "Charges that stun attackers", 50),
                Ability("Reave", "Dash through enemies stealing health and shields", 75),
                Ability("Danse Macabre", "Emit energy beams from fingertips", 25)
            ),
            mastery = 7
        ),
        Warframe(
            "Wukong",
            "The trickster warrior. Wukong has two lives, and can summon a clone.",
            health = 100,
            shield = 125,
            armor = 225,
            energy = 100,
            abilities = listOf(
                Ability("Celestial Twin", "Summon a clone to fight", 25),
                Ability("Cloud Walker", "Become mist and move quickly", 25),
                Ability("Defy", "Become invulnerable and taunt enemies", 50),
                Ability("Primal Fury", "Summon an exalted staff", 25)
            ),
            mastery = 0
        ),
        Warframe(
            "Nezha",
            "The flaming spearman. Nezha combines agility with defensive rings.",
            health = 100,
            shield = 125,
            armor = 175,
            energy = 150,
            abilities = listOf(
                Ability("Fire Walker", "Leave a trail of flame", 25),
                Ability("Blazing Chakram", "Throw a ring of fire", 50),
                Ability("Warding Halo", "Create a protective ring", 75),
                Ability("Divine Spears", "Impale enemies on flaming spears", 100)
            ),
            mastery = 0
        ),
        Warframe(
            "Gauss",
            "Redline Gauss' battery to unlock devastating speed and damage.",
            health = 100,
            shield = 125,
            armor = 175,
            energy = 150,
            abilities = listOf(
                Ability("Mach Rush", "Rush forward at high speed", 25),
                Ability("Kinetic Plating", "Store kinetic energy as armor", 50),
                Ability("Thermal Sunder", "Freeze or heat enemies", 75),
                Ability("Redline", "Charge battery to boost abilities", 100)
            ),
            mastery = 0
        ),
        Warframe(
            "Khora",
            "Command the beast Venari and lash enemies with living chains.",
            health = 100,
            shield = 100,
            armor = 275,
            energy = 150,
            abilities = listOf(
                Ability("Whipclaw", "Lash out with a living chain", 25),
                Ability("Ensnare", "Trap enemies in living chains", 25),
                Ability("Venari", "Command a feral kavat", 0),
                Ability("Strangledome", "Create a dome of strangling chains", 100)
            ),
            mastery = 8
        ),
        Warframe(
            "Nova",
            "Manipulate antimatter to devastate enemies. Nova creates deadly explosions.",
            health = 100,
            shield = 75,
            armor = 65,
            energy = 150,
            abilities = listOf(
                Ability("Null Star", "Create antimatter particles for defense", 25),
                Ability("Antimatter Drop", "Launch an antimatter bomb", 50),
                Ability("Worm Hole", "Create a portal for quick travel", 75),
                Ability("Molecular Prime", "Irradiate enemies with volatile antimatter", 100)
            ),
            mastery = 0
        ),
        Warframe(
            "Trinity",
            "Healer and support. Trinity sustains allies with health and energy.",
            health = 100,
            shield = 100,
            armor = 15,
            energy = 150,
            abilities = listOf(
                Ability("Well of Life", "Mark an enemy to become a health fountain", 25),
                Ability("Energy Vampire", "Drain energy from an enemy", 50),
                Ability("Link", "Link to enemies to share damage", 75),
                Ability("Blessing", "Restore health and shields to all allies", 100)
            ),
            mastery = 0
        ),
        Warframe(
            "Frost",
            "Master of ice. Frost freezes enemies and protects objectives.",
            health = 100,
            shield = 150,
            armor = 300,
            energy = 100,
            abilities = listOf(
                Ability("Freeze", "Launch a shard of ice", 25),
                Ability("Ice Wave", "Send a wave of ice", 50),
                Ability("Snow Globe", "Create a protective sphere of ice", 50),
                Ability("Avalanche", "Summon a hail of ice shards", 100)
            ),
            mastery = 0
        ),
        Warframe(
            "Loki",
            "The trickster. Loki uses invisibility and deception.",
            health = 75,
            shield = 75,
            armor = 65,
            energy = 175,
            abilities = listOf(
                Ability("Decoy", "Create a holographic decoy", 25),
                Ability("Invisibility", "Become invisible", 50),
                Ability("Switch Teleport", "Swap positions with a target", 25),
                Ability("Radial Disarm", "Disarm all nearby enemies", 100)
            ),
            mastery = 0
        ),
        Warframe(
            "Ash",
            "The ninja. Ash assassinates enemies with deadly efficiency.",
            health = 100,
            shield = 100,
            armor = 65,
            energy = 150,
            abilities = listOf(
                Ability("Shuriken", "Throw shurikens to strip armor", 25),
                Ability("Smoke Screen", "Drop a smoke bomb and vanish", 50),
                Ability("Teleport", "Teleport to a target", 25),
                Ability("Blade Storm", "Mark enemies for assassination", 75)
            ),
            mastery = 0
        ),
        Warframe(
            "Nidus",
            "The infested frame. Nidus grows stronger as he consumes enemies.",
            health = 150,
            shield = 0,
            armor = 300,
            energy = 100,
            abilities = listOf(
                Ability("Virulence", "Burst with infested energy", 0),
                Ability("Larva", "Pull enemies into a larva mass", 0),
                Ability("Parasitic Link", "Link to allies or enemies", 0),
                Ability("Ravenous", "Create an infested garden", 0)
            ),
            mastery = 5
        ),
        Warframe(
            "Inaros",
            "The mummy warframe. Inaros has massive health and no shields.",
            health = 550,
            shield = 0,
            armor = 200,
            energy = 100,
            abilities = listOf(
                Ability("Desiccation", "Blind enemies with sand", 25),
                Ability("Devour", "Consume an enemy to heal", 50),
                Ability("Sandstorm", "Become a whirling sandstorm", 75),
                Ability("Scarab Swarm", "Deploy a swarm of scarabs", 100)
            ),
            mastery = 5
        ),
        Warframe(
            "Chroma",
            "The elemental dragon. Chroma adapts to damage and grows stronger.",
            health = 100,
            shield = 100,
            armor = 350,
            energy = 150,
            abilities = listOf(
                Ability("Spectral Scream", "Breathe elemental fury", 25),
                Ability("Elemental Ward", "Emit an elemental aura", 50),
                Ability("Vex Armor", "Convert damage into energy and armor", 75),
                Ability("Effigy", "Summon a dragon pelt", 50)
            ),
            mastery = 5
        ),
        Warframe(
            "Valkyr",
            "The berserker. Valkyr unleashes rage with devastating melee attacks.",
            health = 100,
            shield = 50,
            armor = 600,
            energy = 100,
            abilities = listOf(
                Ability("Rip Line", "Rip to a target or pull target to you", 25),
                Ability("Warcry", "Boost attack speed and slow enemies", 50),
                Ability("Paralysis", "Emit a wave that stuns enemies", 75),
                Ability("Hysteria", "Enter a rage and use exalted claws", 25)
            ),
            mastery = 0
        ),
        Warframe(
            "Ember",
            "Master of fire. Ember immolates enemies with heat.",
            health = 100,
            shield = 100,
            armor = 125,
            energy = 150,
            abilities = listOf(
                Ability("Fireball", "Launch a fireball", 25),
                Ability("Immolation", "Protect yourself with heat", 0),
                Ability("Fire Blast", "Emit a wave of fire", 50),
                Ability("Inferno", "Engulf enemies in flame", 100)
            ),
            mastery = 0
        ),
        Warframe(
            "Titania",
            "The fairy queen. Titania shrinks and takes flight.",
            health = 100,
            shield = 100,
            armor = 65,
            energy = 150,
            abilities = listOf(
                Ability("Spellbind", "Cast a debuff on enemies", 25),
                Ability("Tribute", "Drain health and grant buffs", 25),
                Ability("Lantern", "Lift an enemy and attract others", 50),
                Ability("Razorwing", "Shrink and take flight with weapons", 25)
            ),
            mastery = 7
        ),
        Warframe(
            "Garuda",
            "The blood priestess. Garuda sacrifices health for deadly power.",
            health = 100,
            shield = 50,
            armor = 175,
            energy = 150,
            abilities = listOf(
                Ability("Dread Mirror", "Capture damage in a mirror", 50),
                Ability("Blood Altar", "Impale an enemy for healing", 50),
                Ability("Bloodletting", "Sacrifice health for energy", 0),
                Ability("Seeking Talons", "Launch projectiles that track enemies", 100)
            ),
            mastery = 5
        ),
        Warframe(
            "Limbo",
            "Master of the rift. Limbo phases between dimensions.",
            health = 100,
            shield = 75,
            armor = 65,
            energy = 150,
            abilities = listOf(
                Ability("Banish", "Send targets to the rift", 25),
                Ability("Stasis", "Freeze time in the rift", 25),
                Ability("Rift Surge", "Surge rift energy into enemies", 50),
                Ability("Cataclysm", "Create a massive rift bubble", 50)
            ),
            mastery = 0
        ),
        Warframe(
            "Oberon",
            "The paladin. Oberon heals allies and smites enemies.",
            health = 125,
            shield = 100,
            armor = 150,
            energy = 150,
            abilities = listOf(
                Ability("Smite", "Send a projectile that seeks enemies", 25),
                Ability("Hallowed Ground", "Sanctify ground to protect allies", 25),
                Ability("Renewal", "Heal allies over time", 25),
                Ability("Reckoning", "Lift and damage enemies", 100)
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
        ),
        // Additional Popular Primary Weapons
        Weapon(
            "Kuva Zarr",
            WeaponType.PRIMARY,
            damage = 840,
            critChance = 0.29f,
            critMultiplier = 2.9f,
            statusChance = 0.37f,
            fireRate = 1.67f,
            accuracy = 100f,
            magazine = 7,
            reload = 2.0f,
            mastery = 13
        ),
        Weapon(
            "Kuva Bramma",
            WeaponType.PRIMARY,
            damage = 1250,
            critChance = 0.38f,
            critMultiplier = 2.6f,
            statusChance = 0.24f,
            fireRate = 1.0f,
            accuracy = 100f,
            magazine = 9,
            reload = 2.0f,
            mastery = 15
        ),
        Weapon(
            "Ignis Wraith",
            WeaponType.PRIMARY,
            damage = 35,
            critChance = 0.17f,
            critMultiplier = 2.1f,
            statusChance = 0.29f,
            fireRate = 10.0f,
            accuracy = 100f,
            magazine = 200,
            reload = 2.0f,
            mastery = 9
        ),
        Weapon(
            "Acceltra",
            WeaponType.PRIMARY,
            damage = 80,
            critChance = 0.34f,
            critMultiplier = 2.6f,
            statusChance = 0.12f,
            fireRate = 16.67f,
            accuracy = 100f,
            magazine = 48,
            reload = 1.7f,
            mastery = 12
        ),
        Weapon(
            "Phantasma",
            WeaponType.PRIMARY,
            damage = 50,
            critChance = 0.14f,
            critMultiplier = 1.6f,
            statusChance = 0.46f,
            fireRate = 12.0f,
            accuracy = 100f,
            magazine = 60,
            reload = 2.0f,
            mastery = 10
        ),
        Weapon(
            "Tigris Prime",
            WeaponType.PRIMARY,
            damage = 1560,
            critChance = 0.10f,
            critMultiplier = 2.0f,
            statusChance = 0.30f,
            fireRate = 2.0f,
            accuracy = 9.1f,
            magazine = 2,
            reload = 1.5f,
            mastery = 13,
            isPrime = true
        ),
        Weapon(
            "Rubico Prime",
            WeaponType.PRIMARY,
            damage = 187,
            critChance = 0.38f,
            critMultiplier = 3.0f,
            statusChance = 0.12f,
            fireRate = 2.67f,
            accuracy = 100f,
            magazine = 5,
            reload = 2.4f,
            mastery = 12,
            isPrime = true
        ),
        Weapon(
            "Fulmin",
            WeaponType.PRIMARY,
            damage = 60,
            critChance = 0.24f,
            critMultiplier = 2.6f,
            statusChance = 0.28f,
            fireRate = 10.0f,
            accuracy = 100f,
            magazine = 60,
            reload = 0.0f,
            mastery = 8
        ),
        Weapon(
            "Trumna",
            WeaponType.PRIMARY,
            damage = 91,
            critChance = 0.27f,
            critMultiplier = 2.9f,
            statusChance = 0.19f,
            fireRate = 7.5f,
            accuracy = 100f,
            magazine = 60,
            reload = 2.8f,
            mastery = 13
        ),
        Weapon(
            "Nataruk",
            WeaponType.PRIMARY,
            damage = 260,
            critChance = 0.34f,
            critMultiplier = 2.8f,
            statusChance = 0.14f,
            fireRate = 1.0f,
            accuracy = 100f,
            magazine = 0,
            reload = 0.6f,
            mastery = 14
        ),
        Weapon(
            "Torid",
            WeaponType.PRIMARY,
            damage = 380,
            critChance = 0.07f,
            critMultiplier = 2.5f,
            statusChance = 0.39f,
            fireRate = 2.0f,
            accuracy = 100f,
            magazine = 5,
            reload = 2.0f,
            mastery = 4
        ),
        Weapon(
            "Strun Wraith",
            WeaponType.PRIMARY,
            damage = 288,
            critChance = 0.25f,
            critMultiplier = 2.5f,
            statusChance = 0.20f,
            fireRate = 2.5f,
            accuracy = 6.5f,
            magazine = 10,
            reload = 4.8f,
            mastery = 10
        ),
        // Popular Secondary Weapons
        Weapon(
            "Kuva Nukor",
            WeaponType.SECONDARY,
            damage = 58,
            critChance = 0.29f,
            critMultiplier = 2.3f,
            statusChance = 0.37f,
            fireRate = 5.0f,
            accuracy = 100f,
            magazine = 70,
            reload = 2.5f,
            mastery = 13
        ),
        Weapon(
            "Epitaph",
            WeaponType.SECONDARY,
            damage = 160,
            critChance = 0.27f,
            critMultiplier = 2.1f,
            statusChance = 0.33f,
            fireRate = 3.33f,
            accuracy = 100f,
            magazine = 12,
            reload = 2.0f,
            mastery = 8
        ),
        Weapon(
            "Laetum",
            WeaponType.SECONDARY,
            damage = 60,
            critChance = 0.34f,
            critMultiplier = 2.8f,
            statusChance = 0.18f,
            fireRate = 4.17f,
            accuracy = 100f,
            magazine = 20,
            reload = 1.4f,
            mastery = 0
        ),
        Weapon(
            "Lex Prime",
            WeaponType.SECONDARY,
            damage = 105,
            critChance = 0.25f,
            critMultiplier = 2.5f,
            statusChance = 0.10f,
            fireRate = 2.08f,
            accuracy = 100f,
            magazine = 8,
            reload = 2.35f,
            mastery = 8,
            isPrime = true
        ),
        Weapon(
            "Atomos",
            WeaponType.SECONDARY,
            damage = 24,
            critChance = 0.09f,
            critMultiplier = 1.5f,
            statusChance = 0.35f,
            fireRate = 12.0f,
            accuracy = 100f,
            magazine = 60,
            reload = 1.5f,
            mastery = 5
        ),
        Weapon(
            "Catabolyst",
            WeaponType.SECONDARY,
            damage = 200,
            critChance = 0.26f,
            critMultiplier = 2.2f,
            statusChance = 0.34f,
            fireRate = 1.0f,
            accuracy = 100f,
            magazine = 9,
            reload = 2.0f,
            mastery = 14
        ),
        Weapon(
            "Sporelacer",
            WeaponType.SECONDARY,
            damage = 32,
            critChance = 0.24f,
            critMultiplier = 2.4f,
            statusChance = 0.28f,
            fireRate = 3.33f,
            accuracy = 100f,
            magazine = 48,
            reload = 1.8f,
            mastery = 0
        ),
        // Popular Melee Weapons
        Weapon(
            "Kronen Prime",
            WeaponType.MELEE,
            damage = 165,
            critChance = 0.32f,
            critMultiplier = 2.8f,
            statusChance = 0.16f,
            fireRate = 1.17f,
            accuracy = 100f,
            magazine = 0,
            reload = 0.0f,
            mastery = 13,
            isPrime = true
        ),
        Weapon(
            "Glaive Prime",
            WeaponType.MELEE,
            damage = 200,
            critChance = 0.28f,
            critMultiplier = 2.6f,
            statusChance = 0.14f,
            fireRate = 1.0f,
            accuracy = 100f,
            magazine = 0,
            reload = 0.0f,
            mastery = 10,
            isPrime = true
        ),
        Weapon(
            "Nikana Prime",
            WeaponType.MELEE,
            damage = 198,
            critChance = 0.26f,
            critMultiplier = 2.6f,
            statusChance = 0.14f,
            fireRate = 1.0f,
            accuracy = 100f,
            magazine = 0,
            reload = 0.0f,
            mastery = 12,
            isPrime = true
        ),
        Weapon(
            "Orthos Prime",
            WeaponType.MELEE,
            damage = 176,
            critChance = 0.22f,
            critMultiplier = 2.0f,
            statusChance = 0.18f,
            fireRate = 1.08f,
            accuracy = 100f,
            magazine = 0,
            reload = 0.0f,
            mastery = 7,
            isPrime = true
        ),
        Weapon(
            "Reaper Prime",
            WeaponType.MELEE,
            damage = 200,
            critChance = 0.25f,
            critMultiplier = 2.5f,
            statusChance = 0.05f,
            fireRate = 0.917f,
            accuracy = 100f,
            magazine = 0,
            reload = 0.0f,
            mastery = 10,
            isPrime = true
        ),
        Weapon(
            "Nikana",
            WeaponType.MELEE,
            damage = 132,
            critChance = 0.14f,
            critMultiplier = 2.0f,
            statusChance = 0.14f,
            fireRate = 1.0f,
            accuracy = 100f,
            magazine = 0,
            reload = 0.0f,
            mastery = 4
        ),
        Weapon(
            "Fragor Prime",
            WeaponType.MELEE,
            damage = 320,
            critChance = 0.30f,
            critMultiplier = 3.0f,
            statusChance = 0.10f,
            fireRate = 0.833f,
            accuracy = 100f,
            magazine = 0,
            reload = 0.0f,
            mastery = 10,
            isPrime = true
        ),
        Weapon(
            "Galatine Prime",
            WeaponType.MELEE,
            damage = 260,
            critChance = 0.32f,
            critMultiplier = 2.8f,
            statusChance = 0.14f,
            fireRate = 0.917f,
            accuracy = 100f,
            magazine = 0,
            reload = 0.0f,
            mastery = 13,
            isPrime = true
        ),
        Weapon(
            "Gram Prime",
            WeaponType.MELEE,
            damage = 330,
            critChance = 0.34f,
            critMultiplier = 3.0f,
            statusChance = 0.22f,
            fireRate = 0.917f,
            accuracy = 100f,
            magazine = 0,
            reload = 0.0f,
            mastery = 14,
            isPrime = true
        ),
        Weapon(
            "Lesion",
            WeaponType.MELEE,
            damage = 130,
            critChance = 0.17f,
            critMultiplier = 1.9f,
            statusChance = 0.37f,
            fireRate = 1.0f,
            accuracy = 100f,
            magazine = 0,
            reload = 0.0f,
            mastery = 7
        ),
        Weapon(
            "Guandao Prime",
            WeaponType.MELEE,
            damage = 222,
            critChance = 0.28f,
            critMultiplier = 2.8f,
            statusChance = 0.18f,
            fireRate = 1.0f,
            accuracy = 100f,
            magazine = 0,
            reload = 0.0f,
            mastery = 14,
            isPrime = true
        ),
        Weapon(
            "Paracesis",
            WeaponType.MELEE,
            damage = 258,
            critChance = 0.22f,
            critMultiplier = 2.2f,
            statusChance = 0.26f,
            fireRate = 1.0f,
            accuracy = 100f,
            magazine = 0,
            reload = 0.0f,
            mastery = 10
        ),
        Weapon(
            "Xoris",
            WeaponType.MELEE,
            damage = 170,
            critChance = 0.24f,
            critMultiplier = 2.4f,
            statusChance = 0.14f,
            fireRate = 1.0f,
            accuracy = 100f,
            magazine = 0,
            reload = 0.0f,
            mastery = 5
        ),
        Weapon(
            "Silva & Aegis Prime",
            WeaponType.MELEE,
            damage = 250,
            critChance = 0.28f,
            critMultiplier = 2.4f,
            statusChance = 0.32f,
            fireRate = 1.0f,
            accuracy = 100f,
            magazine = 0,
            reload = 0.0f,
            mastery = 12,
            isPrime = true
        ),
        Weapon(
            "Pennant",
            WeaponType.MELEE,
            damage = 252,
            critChance = 0.18f,
            critMultiplier = 2.0f,
            statusChance = 0.30f,
            fireRate = 1.0f,
            accuracy = 100f,
            magazine = 0,
            reload = 0.0f,
            mastery = 8
        )
    )

    val mods = listOf(
        // Primary Weapon Mods
        Mod("Serration", "+165% Damage", ModType.PRIMARY, ModRarity.COMMON, 10, Polarity.MADURAI),
        Mod("Split Chamber", "+90% Multishot", ModType.PRIMARY, ModRarity.RARE, 10, Polarity.MADURAI),
        Mod("Point Strike", "+150% Critical Chance", ModType.PRIMARY, ModRarity.RARE, 10, Polarity.MADURAI),
        Mod("Vital Sense", "+120% Critical Damage", ModType.PRIMARY, ModRarity.RARE, 10, Polarity.MADURAI),
        Mod("Hellfire", "+90% Heat Damage", ModType.PRIMARY, ModRarity.UNCOMMON, 10, Polarity.MADURAI),
        Mod("Cryo Rounds", "+90% Cold Damage", ModType.PRIMARY, ModRarity.UNCOMMON, 10, Polarity.NARAMON),
        Mod("Stormbringer", "+90% Electricity Damage", ModType.PRIMARY, ModRarity.UNCOMMON, 10, Polarity.MADURAI),
        Mod("Infected Clip", "+90% Toxin Damage", ModType.PRIMARY, ModRarity.UNCOMMON, 10, Polarity.NARAMON),
        Mod("Malignant Force", "+60% Toxin, +60% Status", ModType.PRIMARY, ModRarity.RARE, 5, Polarity.NARAMON),
        Mod("Rime Rounds", "+60% Cold, +60% Status", ModType.PRIMARY, ModRarity.RARE, 5, Polarity.NARAMON),
        Mod("High Voltage", "+60% Electricity, +60% Status", ModType.PRIMARY, ModRarity.RARE, 5, Polarity.MADURAI),
        Mod("Thermite Rounds", "+60% Heat, +60% Status", ModType.PRIMARY, ModRarity.RARE, 5, Polarity.MADURAI),
        Mod("Heavy Caliber", "+165% Damage, -55% Accuracy", ModType.PRIMARY, ModRarity.RARE, 10, Polarity.MADURAI),
        Mod("Hunter Munitions", "30% chance for slash on crit", ModType.PRIMARY, ModRarity.RARE, 5, Polarity.MADURAI),
        Mod("Vigilante Armaments", "+60% Multishot", ModType.PRIMARY, ModRarity.RARE, 5, Polarity.MADURAI),

        // Secondary Weapon Mods
        Mod("Hornet Strike", "+220% Damage", ModType.SECONDARY, ModRarity.COMMON, 10, Polarity.MADURAI),
        Mod("Barrel Diffusion", "+120% Multishot", ModType.SECONDARY, ModRarity.RARE, 10, Polarity.MADURAI),
        Mod("Lethal Torrent", "+60% Fire Rate, +60% Multishot", ModType.SECONDARY, ModRarity.RARE, 10, Polarity.MADURAI),
        Mod("Pistol Gambit", "+120% Critical Chance", ModType.SECONDARY, ModRarity.RARE, 10, Polarity.MADURAI),
        Mod("Target Cracker", "+60% Critical Damage", ModType.SECONDARY, ModRarity.RARE, 10, Polarity.MADURAI),
        Mod("Convulsion", "+90% Electricity Damage", ModType.SECONDARY, ModRarity.UNCOMMON, 10, Polarity.MADURAI),
        Mod("Deep Freeze", "+90% Cold Damage", ModType.SECONDARY, ModRarity.UNCOMMON, 10, Polarity.NARAMON),
        Mod("Heated Charge", "+90% Heat Damage", ModType.SECONDARY, ModRarity.UNCOMMON, 10, Polarity.MADURAI),
        Mod("Pathogen Rounds", "+90% Toxin Damage", ModType.SECONDARY, ModRarity.UNCOMMON, 10, Polarity.NARAMON),

        // Melee Weapon Mods
        Mod("Pressure Point", "+120% Damage", ModType.MELEE, ModRarity.COMMON, 10, Polarity.MADURAI),
        Mod("Condition Overload", "+120% damage per status type", ModType.MELEE, ModRarity.RARE, 10, Polarity.MADURAI),
        Mod("Blood Rush", "Critical Chance scales with combo", ModType.MELEE, ModRarity.RARE, 10, Polarity.MADURAI),
        Mod("Weeping Wounds", "Status Chance scales with combo", ModType.MELEE, ModRarity.RARE, 10, Polarity.NARAMON),
        Mod("Organ Shatter", "+90% Critical Damage", ModType.MELEE, ModRarity.RARE, 10, Polarity.MADURAI),
        Mod("True Steel", "+60% Critical Chance", ModType.MELEE, ModRarity.RARE, 10, Polarity.MADURAI),
        Mod("Berserker Fury", "+75% Attack Speed on crit", ModType.MELEE, ModRarity.RARE, 10, Polarity.MADURAI),
        Mod("Molten Impact", "+90% Heat Damage", ModType.MELEE, ModRarity.UNCOMMON, 10, Polarity.MADURAI),
        Mod("North Wind", "+90% Cold Damage", ModType.MELEE, ModRarity.UNCOMMON, 10, Polarity.NARAMON),
        Mod("Shocking Touch", "+90% Electricity Damage", ModType.MELEE, ModRarity.UNCOMMON, 10, Polarity.MADURAI),
        Mod("Fever Strike", "+90% Toxin Damage", ModType.MELEE, ModRarity.UNCOMMON, 10, Polarity.NARAMON),
        Mod("Primed Reach", "+3m Range", ModType.MELEE, ModRarity.PRIMED, 10, Polarity.NARAMON),
        Mod("Drifting Contact", "+40% Status Duration, +28s Combo", ModType.MELEE, ModRarity.RARE, 10, Polarity.NARAMON),

        // Warframe Survival Mods
        Mod("Vitality", "+440% Health", ModType.WARFRAME, ModRarity.COMMON, 10, Polarity.VAZARIN),
        Mod("Redirection", "+440% Shields", ModType.WARFRAME, ModRarity.COMMON, 10, Polarity.VAZARIN),
        Mod("Steel Fiber", "+110% Armor", ModType.WARFRAME, ModRarity.COMMON, 10, Polarity.VAZARIN),
        Mod("Primed Vigor", "+165% Health, +110% Shields", ModType.WARFRAME, ModRarity.PRIMED, 10, Polarity.VAZARIN),
        Mod("Adaptation", "Gain damage resistance to types received", ModType.WARFRAME, ModRarity.RARE, 10, Polarity.VAZARIN),
        Mod("Rolling Guard", "Brief invulnerability after rolling", ModType.WARFRAME, ModRarity.RARE, 10, Polarity.VAZARIN),
        Mod("Quick Thinking", "Use energy to prevent death", ModType.WARFRAME, ModRarity.RARE, 10, Polarity.VAZARIN),
        Mod("Rage", "Convert health damage to energy", ModType.WARFRAME, ModRarity.UNCOMMON, 10, Polarity.MADURAI),
        Mod("Hunter Adrenaline", "Convert health damage to energy", ModType.WARFRAME, ModRarity.RARE, 5, Polarity.MADURAI),

        // Warframe Ability Mods
        Mod("Intensify", "+30% Ability Strength", ModType.WARFRAME, ModRarity.COMMON, 10, Polarity.MADURAI),
        Mod("Blind Rage", "+99% Strength, -55% Efficiency", ModType.WARFRAME, ModRarity.RARE, 10, Polarity.MADURAI),
        Mod("Transient Fortitude", "+55% Strength, -27.5% Duration", ModType.WARFRAME, ModRarity.RARE, 10, Polarity.MADURAI),
        Mod("Streamline", "+30% Ability Efficiency", ModType.WARFRAME, ModRarity.COMMON, 10, Polarity.NARAMON),
        Mod("Fleeting Expertise", "+60% Efficiency, -60% Duration", ModType.WARFRAME, ModRarity.RARE, 10, Polarity.NARAMON),
        Mod("Continuity", "+30% Ability Duration", ModType.WARFRAME, ModRarity.COMMON, 10, Polarity.VAZARIN),
        Mod("Primed Continuity", "+55% Ability Duration", ModType.WARFRAME, ModRarity.PRIMED, 10, Polarity.VAZARIN),
        Mod("Narrow Minded", "+99% Duration, -66% Range", ModType.WARFRAME, ModRarity.RARE, 10, Polarity.VAZARIN),
        Mod("Stretch", "+45% Ability Range", ModType.WARFRAME, ModRarity.COMMON, 10, Polarity.NARAMON),
        Mod("Overextended", "+90% Range, -60% Strength", ModType.WARFRAME, ModRarity.RARE, 10, Polarity.NARAMON),
        Mod("Augur Reach", "+30% Ability Range", ModType.WARFRAME, ModRarity.RARE, 5, Polarity.NARAMON),

        // Warframe Utility Mods
        Mod("Flow", "+150% Energy Max", ModType.WARFRAME, ModRarity.COMMON, 10, Polarity.NARAMON),
        Mod("Primed Flow", "+275% Energy Max", ModType.WARFRAME, ModRarity.PRIMED, 10, Polarity.NARAMON),
        Mod("Natural Talent", "+50% Casting Speed", ModType.WARFRAME, ModRarity.RARE, 10, Polarity.NARAMON),
        Mod("Handspring", "Knockdown Recovery", ModType.WARFRAME, ModRarity.UNCOMMON, 10, Polarity.VAZARIN),
        Mod("Power Drift", "+15% Strength, +30% Friction", ModType.WARFRAME, ModRarity.RARE, 5, Polarity.EXILUS),
        Mod("Rush", "+30% Sprint Speed", ModType.WARFRAME, ModRarity.COMMON, 10, Polarity.NARAMON),
        Mod("Armored Agility", "+45% Sprint Speed, +15% Armor", ModType.WARFRAME, ModRarity.RARE, 10, Polarity.EXILUS),
        Mod("Constitution", "+28% Duration, +40% Knockdown Recovery", ModType.WARFRAME, ModRarity.RARE, 10, Polarity.VAZARIN),

        // Aura Mods
        Mod("Steel Charge", "+60% Melee Damage to team", ModType.WARFRAME, ModRarity.UNCOMMON, 10, Polarity.AURA),
        Mod("Energy Siphon", "+0.6 Energy/s to team", ModType.WARFRAME, ModRarity.UNCOMMON, 10, Polarity.AURA),
        Mod("Corrosive Projection", "-18% Enemy Armor", ModType.WARFRAME, ModRarity.UNCOMMON, 10, Polarity.AURA),
        Mod("Rejuvenation", "+3 Health/s to team", ModType.WARFRAME, ModRarity.UNCOMMON, 10, Polarity.AURA),
        Mod("Growing Power", "+25% Strength on status to team", ModType.WARFRAME, ModRarity.RARE, 10, Polarity.AURA)
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