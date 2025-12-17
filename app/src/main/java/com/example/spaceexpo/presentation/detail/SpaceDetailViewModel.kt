package com.example.spaceexpo.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceexpo.data.model.SpaceObject
import com.example.spaceexpo.domain.SpaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpaceDetailViewModel @Inject constructor(
    private val repository: SpaceRepository
): ViewModel(){
    private val _spaceObject = MutableStateFlow<SpaceObject?>(null)
    val spaceObject: StateFlow<SpaceObject?> = _spaceObject.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadSpaceObject(id: Int){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _spaceObject.value = repository.getSpaceObjectById(id)
            }finally {
                _isLoading.value = false
            }
        }
    }



}