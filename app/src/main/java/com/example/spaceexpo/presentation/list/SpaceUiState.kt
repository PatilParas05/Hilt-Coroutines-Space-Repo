package com.example.spaceexpo.presentation.list

import com.example.spaceexpo.data.model.SpaceObject

sealed class SpaceUiState{
    object Loading : SpaceUiState()
    data class Success (val spaceObject: List<SpaceObject>): SpaceUiState()
    data class Error (val message: String): SpaceUiState()
}