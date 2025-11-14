package com.example.warframeapp20.ui.sortie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warframeapp20.data.Sortie
import com.example.warframeapp20.data.WorldStateService
import kotlinx.coroutines.launch

class SortieViewModel : ViewModel() {

    private val worldStateService = WorldStateService()

    private val _sortie = MutableLiveData<Sortie?>()
    val sortie: LiveData<Sortie?> = _sortie

    private var dataLoaded = false

    init {
        // Load data once when ViewModel is created
        loadSortie()
    }

    fun loadSortie() {
        // Prevent duplicate loads unless explicitly refreshed
        if (dataLoaded && _sortie.value != null) return

        dataLoaded = true
        viewModelScope.launch {
            try {
                val sortieData = worldStateService.getSortie()
                _sortie.value = sortieData
            } catch (e: Exception) {
                _sortie.value = null
            }
        }
    }

    fun refresh() {
        dataLoaded = false
        loadSortie()
    }
}