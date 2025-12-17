package com.example.spaceexpo.data.repository

import com.example.spaceexpo.R
import com.example.spaceexpo.data.model.SpaceObject
import com.example.spaceexpo.data.model.SpaceObjectType
import com.example.spaceexpo.domain.SpaceRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SpaceRepositoryImpl @Inject constructor() : SpaceRepository {
    private val dummyData = listOf(
        SpaceObject(
            id = 1,
            name = "Mars",
            type = SpaceObjectType.PLANET,
            description = "The Red Planet, fourth from the Sun",
            distanceFromEarth = "225 million km",
            discoveryYear = 1610,
            imageUrl = R.drawable.mars,
            interestingFacts = listOf(
                "Mars has two moons: Phobos and Deimos",
                "A day on Mars is 24.6 hours",
                "Mars has the largest volcano in the solar system"
            )
        ),

        SpaceObject(
            id = 2,
            name = "Jupiter",
            type = SpaceObjectType.PLANET,
            description = "The largest planet in our solar system",
            distanceFromEarth = "778 million km",
            discoveryYear = 1610,
            imageUrl = R.drawable.jupiter,
            interestingFacts = listOf(
                "Jupiter has 95 known moons",
                "The Great Red Spot is a giant storm",
                "Jupiter is mostly made of hydrogen and helium"
            )
        ),
        SpaceObject(
            id = 3,
            name = "Andromeda Galaxy",
            type = SpaceObjectType.GALAXY,
            description = "The nearest major galaxy to the Milky Way",
            distanceFromEarth = "2.537 million light years",
            discoveryYear = 964,
            imageUrl = R.drawable.andro,
            interestingFacts = listOf(
                "Contains about 1 trillion stars",
                "Will collide with Milky Way in 4.5 billion years",
                "Visible to naked eye from Earth"
            )
        ),
        SpaceObject(
            id = 4,
            name = "Europa",
            type = SpaceObjectType.MOON,
            description = "Jupiter's icy moon with subsurface ocean",
            distanceFromEarth = "628 million km",
            discoveryYear = 1610,
            imageUrl = R.drawable.europa,
            interestingFacts = listOf(
                "Has more water than Earth",
                "Surface is mostly water ice",
                "Potential for extraterrestrial life"
            )
        ),
        SpaceObject(
            id = 5,
            name = "Sirius",
            type = SpaceObjectType.STAR,
            description = "The brightest star in Earth's night sky",
            distanceFromEarth = "8.6 light years",
            discoveryYear = -10000,
            imageUrl = R.drawable.sirius,
            interestingFacts = listOf(
                "Actually a binary star system",
                "25 times more luminous than the Sun",
                "Known as the 'Dog Star'"
            )
        ),
        SpaceObject(
            id = 6,
            name = "Orion Nebula",
            type = SpaceObjectType.NEBULA,
            description = "A stellar nursery where new stars are born",
            distanceFromEarth = "1,344 light years",
            discoveryYear = 1610,
            imageUrl = R.drawable.orion,
            interestingFacts = listOf(
                "Visible to naked eye",
                "Contains about 700 stars",
                "One of the most photographed objects"
            )
        ),

        SpaceObject(
            id = 7,
            name = "Titan",
            type = SpaceObjectType.MOON,
            description = "Largest moon of Saturn, with a thick atmosphere",
            distanceFromEarth = "1.2 billion km",
            discoveryYear = 1655,
            imageUrl = R.drawable.titan,
            interestingFacts = listOf(
                "Has lakes of liquid methane and ethane",
                "Atmosphere is denser than Earth's",
                "Surface may support microbial life"
            )
        ),
        SpaceObject(
            id = 8,
            name = "Betelgeuse",
            type = SpaceObjectType.STAR,
            description = "A red supergiant in the Orion constellation",
            distanceFromEarth = "642 light years",
            discoveryYear = -1000,
            imageUrl = R.drawable.bet,
            interestingFacts = listOf(
                "Expected to explode as a supernova",
                "One of the largest visible stars",
                "Its brightness varies over time"
            )
        ),
        SpaceObject(
            id = 9,
            name = "Milky Way",
            type = SpaceObjectType.GALAXY,
            description = "Our home galaxy, a barred spiral",
            distanceFromEarth = "0 light years",
            discoveryYear = -1000,
            imageUrl = R.drawable.milky,
            interestingFacts = listOf(
                "Contains over 100 billion stars",
                "Solar System is located in Orion Arm",
                "Center hosts a supermassive black hole"
            )
        ),
        SpaceObject(
            id = 10,
            name = "Crab Nebula",
            type = SpaceObjectType.NEBULA,
            description = "Remnant of a supernova observed in 1054",
            distanceFromEarth = "6,500 light years",
            discoveryYear = 1731,
            imageUrl = R.drawable.crab,
            interestingFacts = listOf(
                "Contains a rapidly spinning neutron star",
                "Expanding at 1,500 km/s",
                "Visible in multiple wavelengths"
            )
        ),

    )

    override suspend fun getAllSpaceObjects(): List<SpaceObject> {
        delay(1500)
        return dummyData
    }

    override suspend fun getSpaceObjectById(id: Int): SpaceObject? {
        delay(800)
        return dummyData.find { it.id == id }
    }

    override suspend fun getSpaceObjectsByType(type: SpaceObjectType): List<SpaceObject> {
        delay(1000)
        return dummyData.filter { it.type == type }
    }

    override fun getSpaceObjectsFlow(): Flow<List<SpaceObject>> = flow {
        emit(emptyList())
        delay(1500)
        emit(dummyData)
    }
}