package com.example.warframeapp20.ui.weapons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.warframeapp20.data.Weapon
import com.example.warframeapp20.data.WarframeRepository

class WeaponsViewModel : ViewModel() {
    
    private val _weapons = MutableLiveData<List<Weapon>>()
    val weapons: LiveData<List<Weapon>> = _weapons
    
    fun loadWeapons() {
        _weapons.value = WarframeRepository.weapons
    }
}