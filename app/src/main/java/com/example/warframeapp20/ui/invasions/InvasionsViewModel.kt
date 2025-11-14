package com.example.warframeapp20.ui.invasions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warframeapp20.data.Invasion
import com.example.warframeapp20.data.WorldStateService
import kotlinx.coroutines.launch

class InvasionsViewModel : ViewModel() {

    private val worldStateService = WorldStateService()

    private val _invasions = MutableLiveData<List<Invasion>>()
    val invasions: LiveData<List<Invasion>> = _invasions

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var dataLoaded = false

    init {
        // Load data once when ViewModel is created
        loadInvasions()
    }

    fun loadInvasions() {
        // Prevent duplicate loads unless explicitly refreshed
        if (dataLoaded && _invasions.value != null) return

        _isLoading.value = true
        dataLoaded = true
        viewModelScope.launch {
            try {
                val invasionList = worldStateService.getInvasions()
                _invasions.value = invasionList
            } catch (e: Exception) {
                _invasions.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refresh() {
        dataLoaded = false
        loadInvasions()
    }
}