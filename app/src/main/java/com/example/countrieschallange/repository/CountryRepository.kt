package com.example.countrieschallange.repository

import android.util.Log
import com.example.countrieschallange.api.CountryService
import com.example.countrieschallange.cons.NullResponseException
import com.example.countrieschallange.cons.ResponseIsFailure
import com.example.countrieschallange.cons.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent.get


interface CountryRepository {
    fun getCountries(): Flow<UiState>
}

class CountryRepositoryImp(
    private val service: CountryService  = get(CountryService::class.java)
): CountryRepository {
    override fun getCountries(): Flow<UiState> =
        flow {
            try {
                val response = service.getCountries()
                if (response.isSuccessful) {
                    emit(response.body()?.let {
                        UiState.Success(it)
                    } ?: throw NullResponseException())
                } else {
                    throw ResponseIsFailure()
                }
            } catch (e: Exception) {
                emit(UiState.Error(e))
            }
        }
}