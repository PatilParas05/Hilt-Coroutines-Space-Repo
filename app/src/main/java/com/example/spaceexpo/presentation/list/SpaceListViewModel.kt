package com.example.spaceexpo.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceexpo.data.model.SpaceObjectType
import com.example.spaceexpo.domain.SpaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpaceListViewModel @Inject constructor(
    private val repository: SpaceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SpaceUiState>(SpaceUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _selectedFilter = MutableStateFlow<SpaceObjectType?>(null)
    val selectedFilter = _selectedFilter.asStateFlow()

    init {
        loadSpaceObjects()
    }

    fun loadSpaceObjects() {
        viewModelScope.launch {
            _uiState.value = SpaceUiState.Loading
            try {
                val objects = if (selectedFilter.value == null) {
                    repository.getAllSpaceObjects()
                } else {
                    repository.getSpaceObjectsByType(selectedFilter.value!!)
                }
                _uiState.value = SpaceUiState.Success(objects)
            } catch (e: Exception) {
                _uiState.value = SpaceUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun filterByType(type: SpaceObjectType?) {
        _selectedFilter.value = type
        loadSpaceObjects()
    }
}