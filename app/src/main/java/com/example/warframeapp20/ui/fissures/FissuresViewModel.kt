package com.example.warframeapp20.ui.fissures

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warframeapp20.data.Fissure
import com.example.warframeapp20.data.WorldStateService
import kotlinx.coroutines.launch

class FissuresViewModel : ViewModel() {
    
    private val worldStateService = WorldStateService()
    
    private val _fissures = MutableLiveData<List<Fissure>>()
    val fissures: LiveData<List<Fissure>> = _fissures
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    suspend fun loadFissures() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val fissureList = worldStateService.getFissures()
                _fissures.value = fissureList
            } catch (e: Exception) {
                _fissures.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}