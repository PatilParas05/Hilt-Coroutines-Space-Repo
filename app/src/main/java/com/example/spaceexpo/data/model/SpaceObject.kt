package com.example.spaceexpo.data.model

data class SpaceObject(
    val id: Int,
    val name: String,
    val type: SpaceObjectType,
    val description: String,
    val distanceFromEarth: String,
    val discoveryYear: Int,
    val imageUrl: Int,
    val interestingFacts: List<String>
)

