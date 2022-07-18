package com.example.countrieschallange.repository

import android.util.Log
import com.example.countrieschallange.api.CountryService
import com.example.countrieschallange.cons.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent.get


interface CountryRepository {
    suspend fun getCountries(): Flow<UiState>
}

class CountryRepositoryImp(
    private val service: CountryService  = get(CountryService::class.java)
): CountryRepository {
    override suspend fun getCountries(): Flow<UiState> =
        flow {
            try {
                // attempt some code
                val response = service.getCountries()
                if (response.isSuccessful) {
                    emit(response.body()?.let {
                        UiState.Success(it)
                    } ?: throw Exception("Null Response"))
                } else {
                    val errorMsg = response.errorBody()?.string()
                    response.errorBody()?.close()  // remember to close it after getting the stream of error body
                    emit(UiState.Error(response.code(), errorMsg.toString()))  // 3. Error State
                }
            } catch (e: Exception) {
                // catch the errors and run this block instead
                emit(UiState.Error(0, "Unexpected Exception"))
            }
        }
}