package com.example.spaceexpo.domain

import com.example.spaceexpo.data.model.SpaceObject
import com.example.spaceexpo.data.model.SpaceObjectType
import kotlinx.coroutines.flow.Flow

interface SpaceRepository {
    suspend fun getAllSpaceObjects(): List<SpaceObject>
    suspend fun getSpaceObjectById(id: Int): SpaceObject?
    suspend fun getSpaceObjectsByType(type: SpaceObjectType): List<SpaceObject>
    fun getSpaceObjectsFlow(): Flow<List<SpaceObject>>
}