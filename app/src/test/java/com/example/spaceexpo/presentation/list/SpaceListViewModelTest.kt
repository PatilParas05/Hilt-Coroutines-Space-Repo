package com.example.spaceexpo.presentation.list

import app.cash.turbine.test
import com.example.spaceexpo.data.model.SpaceObject
import com.example.spaceexpo.data.model.SpaceObjectType
import com.example.spaceexpo.domain.SpaceRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SpaceListViewModelTest {
    
    private lateinit var viewModel: SpaceListViewModel
    private val repository: SpaceRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()
    
    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when init, uiState should transition from Loading to Success`() = runTest {
        // Given
        val mockData = listOf(
            SpaceObject(1, "Earth", SpaceObjectType.PLANET, "Home", "0", 0, 0, emptyList())
        )
        coEvery { repository.getAllSpaceObjects() } returns mockData

        // When
        // Initialize ViewModel - with StandardTestDispatcher, the init coroutine won't run yet
        viewModel = SpaceListViewModel(repository)

        // Then using Turbine to test the StateFlow
        viewModel.uiState.test {
            // Initial state should be Loading (from StateFlow initialization)
            assertEquals(SpaceUiState.Loading, awaitItem())

            // Execute the pending coroutines (the one launched in init)
            testDispatcher.scheduler.runCurrent()

            // Next item should be Success
            val state = awaitItem()
            assert(state is SpaceUiState.Success)
            assertEquals(mockData, (state as SpaceUiState.Success).spaceObject)
        }
    }

    @Test
    fun `when repository fails, uiState should be Error`() = runTest {
        // Given
        val errorMessage = "Network Timeout"
        coEvery { repository.getAllSpaceObjects() } throws Exception(errorMessage)

        // When
        viewModel = SpaceListViewModel(repository)

        // Then
        viewModel.uiState.test {
            // Initial state
            assertEquals(SpaceUiState.Loading, awaitItem())

            // Run the coroutine in loadSpaceObjects
            testDispatcher.scheduler.runCurrent()

            val state = awaitItem()
            assert(state is SpaceUiState.Error)
            assertEquals(errorMessage, (state as SpaceUiState.Error).message)
        }
    }
}
