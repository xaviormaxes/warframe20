package com.example.warframeapp20.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.warframeapp20.data.*

class DashboardViewModel : ViewModel() {

    // Sample data for demonstration
    private val _invasions = MutableLiveData<List<Invasion>>().apply {
        value = listOf(
            Invasion(
                id = "invasion1",
                node = "Earth - Everest",
                attackerReward = Reward(asString = "Orokin Cell"),
                defenderReward = Reward(asString = "Fieldron"),
                completion = 45.0f,
                eta = "2h 15m",
                vsInfestation = false
            ),
            Invasion(
                id = "invasion2",
                node = "Jupiter - Adrastea",
                attackerReward = Reward(asString = ""),
                defenderReward = Reward(asString = "Detonite Injector"),
                completion = 78.0f,
                eta = "45m",
                vsInfestation = true
            ),
            Invasion(
                id = "invasion3",
                node = "Mars - Olympus",
                attackerReward = Reward(asString = "Mutagen Mass"),
                defenderReward = Reward(asString = "Exilus Adapter Blueprint"),
                completion = 23.0f,
                eta = "3h 42m",
                vsInfestation = false
            )
        )
    }
    val invasions: LiveData<List<Invasion>> = _invasions

    private val _events = MutableLiveData<List<Event>>().apply {
        value = listOf(
            Event(
                id = "event1",
                description = "Ghoul Purge",
                tooltip = "Active",
                node = "Earth - Plains of Eidolon",
                rewards = emptyList(),
                health = "100%",
                expiry = "2024-12-31T23:59:59.000Z",
                maximumScore = null,
                currentScore = null,
                isActive = true
            ),
            Event(
                id = "event2",
                description = "Thermia Fractures",
                tooltip = "87% Completed",
                node = "Venus - Orb Vallis",
                rewards = emptyList(),
                health = "87%",
                expiry = "2024-12-31T23:59:59.000Z",
                maximumScore = 100,
                currentScore = 87,
                isActive = true
            )
        )
    }
    val events: LiveData<List<Event>> = _events

    private val _fissures = MutableLiveData<List<Fissure>>().apply {
        value = listOf(
            Fissure(
                id = "fissure1",
                node = "Earth - Mantle",
                missionType = "Capture",
                enemy = "Grineer",
                tier = "Lith",
                tierNum = 1,
                activation = "2024-01-01T00:00:00.000Z",
                expiry = "2024-01-01T01:00:00.000Z",
                eta = "23m"
            ),
            Fissure(
                id = "fissure2",
                node = "Mars - Spear",
                missionType = "Defense",
                enemy = "Corpus",
                tier = "Meso",
                tierNum = 2,
                activation = "2024-01-01T00:00:00.000Z",
                expiry = "2024-01-01T01:00:00.000Z",
                eta = "45m"
            ),
            Fissure(
                id = "fissure3",
                node = "Jupiter - Cameria",
                missionType = "Survival",
                enemy = "Corpus",
                tier = "Neo",
                tierNum = 3,
                activation = "2024-01-01T00:00:00.000Z",
                expiry = "2024-01-01T01:00:00.000Z",
                eta = "1h 12m"
            ),
            Fissure(
                id = "fissure4",
                node = "Pluto - Cerberus",
                missionType = "Interception",
                enemy = "Corpus",
                tier = "Axi",
                tierNum = 4,
                activation = "2024-01-01T00:00:00.000Z",
                expiry = "2024-01-01T01:00:00.000Z",
                eta = "1h 34m"
            )
        )
    }
    val fissures: LiveData<List<Fissure>> = _fissures

    private val _sortie = MutableLiveData<Sortie>().apply {
        value = Sortie(
            id = "sortie1",
            boss = "Kela De Thaym",
            faction = "Grineer",
            missions = listOf(
                SortieMission(
                    node = "Sedna - Adaro",
                    missionType = "Exterminate",
                    modifier = "Enemy Physical Enhancement",
                    modifierDescription = "Enemies have increased health, shields and armor"
                ),
                SortieMission(
                    node = "Sedna - Kelpie",
                    missionType = "Mobile Defense",
                    modifier = "Eximus Stronghold",
                    modifierDescription = "More Eximus enemies will spawn"
                ),
                SortieMission(
                    node = "Sedna - Merrow",
                    missionType = "Assassination",
                    modifier = "Energy Reduction",
                    modifierDescription = "Energy capacity and regeneration is reduced"
                )
            ),
            expiry = "2024-01-02T00:00:00.000Z",
            eta = "8h 32m"
        )
    }
    val sortie: LiveData<Sortie> = _sortie

    private val _cetusCycle = MutableLiveData<CetusCycle>().apply {
        value = CetusCycle(
            isDay = true,
            timeLeft = "2h 34m",
            expiry = "2024-01-01T12:00:00.000Z"
        )
    }
    val cetusCycle: LiveData<CetusCycle> = _cetusCycle

    private val _vallisCycle = MutableLiveData<VallisCycle>().apply {
        value = VallisCycle(
            isWarm = false,
            timeLeft = "14m",
            expiry = "2024-01-01T12:00:00.000Z"
        )
    }
    val vallisCycle: LiveData<VallisCycle> = _vallisCycle

    private val _cambionCycle = MutableLiveData<CambionCycle>().apply {
        value = CambionCycle(
            active = "Fass",
            timeLeft = "45m",
            expiry = "2024-01-01T12:00:00.000Z"
        )
    }
    val cambionCycle: LiveData<CambionCycle> = _cambionCycle

    // Methods to refresh data from API would go here
    fun refreshData() {
        // In a real app, this would fetch fresh data from a repository
    }
}
