package com.example.countrieschallange.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countrieschallange.cons.UiState
import com.example.countrieschallange.model.Country
import com.example.countrieschallange.repository.CountryRepositoryImp
import com.example.countrieschallange.view.adapters.CountriesAdapter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class CountryViewModel(
    private val repositoryImpl: CountryRepositoryImp,
    private val dispatcher: CoroutineDispatcher
): BaseViewModel() {

    private val _countryLiveData = MutableLiveData<UiState>()
    val countryLiveData: LiveData<UiState> get() = _countryLiveData

    val countryAdapter by lazy {
        CountriesAdapter()
    }

    fun getCountries() {
        viewModelSafeScope.launch(dispatcher) {
            repositoryImpl.getCountries().collect {
                _countryLiveData.postValue(it)
            }
        }
    }

    fun setLoadingState() { _countryLiveData.value = UiState.Loading }

}