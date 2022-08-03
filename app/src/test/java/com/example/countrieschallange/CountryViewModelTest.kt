package com.example.countrieschallange

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.countrieschallange.cons.UiState
import com.example.countrieschallange.model.Country
import com.example.countrieschallange.repository.CountryRepositoryImp
import com.example.countrieschallange.view_model.CountryViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CountryViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val mockRepository = mockk<CountryRepositoryImp>(relaxed = true)
    private lateinit var mockViewModel: CountryViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockViewModel = CountryViewModel(mockRepository, testDispatcher)
    }

    @Test
    fun `start of call should return UiStateLoading`() {
        every { mockRepository.getCountries() } returns flowOf(UiState.Loading)

        val responseHelper = mutableListOf<UiState>()

        mockViewModel.countryLiveData.observeForever { responseHelper.add(it) }
        mockViewModel.getCountries()
        assertThat(responseHelper[0]).isInstanceOf(UiState.Loading::class.java)
    }

    @Test
    fun `failure call returns UiStateError`() {
        every { mockRepository.getCountries() } returns flowOf(UiState.Error(getMockErrorResponse()))

        val responseHelper = mutableListOf<UiState>()

        mockViewModel.countryLiveData.observeForever { responseHelper.add(it) }
        mockViewModel.getCountries()
        assertThat(responseHelper[0]).isInstanceOf(UiState.Error::class.java)
    }

    @Test
    fun `successful call returns UiStateSuccess`() {
        every { mockRepository.getCountries() } returns flowOf(UiState.Success(getMockSuccessResponse()))

        val responseHelper = mutableListOf<UiState>()

        mockViewModel.countryLiveData.observeForever { responseHelper.add(it) }
        mockViewModel.getCountries()
        assertThat(responseHelper[0]).isInstanceOf(UiState.Success::class.java)
    }

    private fun getMockErrorResponse() = Exception("Failed network call")
    private fun getMockSuccessResponse() = listOf(
        Country(
            name = "name1",
            region = "region1",
            capital = "capital1"
        ),
        Country(
            name = "name2",
            region = "region2",
            capital = "capital2"
        )
    )
}
