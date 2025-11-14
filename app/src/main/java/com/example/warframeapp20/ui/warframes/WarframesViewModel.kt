package com.example.warframeapp20.ui.warframes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.warframeapp20.data.Warframe
import com.example.warframeapp20.data.WarframeRepository

class WarframesViewModel : ViewModel() {

    private val _warframes = MutableLiveData<List<Warframe>>()
    val warframes: LiveData<List<Warframe>> = _warframes

    private var dataLoaded = false

    init {
        // Load data once when ViewModel is created
        loadWarframes()
    }

    fun loadWarframes() {
        // Prevent duplicate loads unless explicitly refreshed
        if (dataLoaded && _warframes.value != null) return

        dataLoaded = true
        _warframes.value = WarframeRepository.warframes
    }

    fun refresh() {
        dataLoaded = false
        loadWarframes()
    }
}