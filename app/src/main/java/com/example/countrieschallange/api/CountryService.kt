package com.example.countrieschallange.api

import com.example.countrieschallange.model.Country
import retrofit2.Response
import retrofit2.http.GET

interface CountryService {
    @GET("countries.json")
    suspend fun getCountries(
    ): Response<List<Country>>
}