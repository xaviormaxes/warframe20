package com.example.warframeapp20.ui.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warframeapp20.data.Event
import com.example.warframeapp20.data.WorldStateService
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {
    
    private val worldStateService = WorldStateService()
    
    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events
    
    fun loadEvents() {
        viewModelScope.launch {
            try {
                val eventList = worldStateService.getEvents()
                _events.value = eventList
            } catch (e: Exception) {
                _events.value = emptyList()
            }
        }
    }
}