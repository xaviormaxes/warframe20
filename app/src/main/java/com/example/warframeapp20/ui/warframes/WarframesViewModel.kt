package com.example.warframeapp20.ui.warframes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.warframeapp20.data.Warframe
import com.example.warframeapp20.data.WarframeRepository

class WarframesViewModel : ViewModel() {
    
    private val _warframes = MutableLiveData<List<Warframe>>()
    val warframes: LiveData<List<Warframe>> = _warframes
    
    fun loadWarframes() {
        _warframes.value = WarframeRepository.warframes
    }
}