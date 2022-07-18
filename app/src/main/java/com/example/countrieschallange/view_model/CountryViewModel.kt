package com.example.countrieschallange.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countrieschallange.cons.UiState
import com.example.countrieschallange.repository.CountryRepositoryImp
import com.example.countrieschallange.view.adapters.CountriesAdapter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class CountryViewModel(
    private val repositoryImpl: CountryRepositoryImp,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {
    private val tag = "CountryViewModel"
    lateinit var countryItemAdapter: CountriesAdapter

    private val viewModelSafeScope by lazy {
        viewModelScope + coroutineExceptionHandler
    }

    private val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(tag, "Context: $coroutineContext\nMessage: ${throwable.localizedMessage}", throwable)
        }
    }

    private val _countryLiveData = MutableLiveData<UiState>()
    val countryLiveData: LiveData<UiState> get() = _countryLiveData

    fun getCountries() {
        viewModelSafeScope.launch(dispatcher) {
            repositoryImpl.getCountries().collect {
                _countryLiveData.postValue(it)
            }
        }
    }

    fun setLoadingState() { _countryLiveData.value = UiState.Loading }

}