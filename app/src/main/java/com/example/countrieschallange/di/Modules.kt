package com.example.countrieschallange.di

import com.example.countrieschallange.api.CountryService
import com.example.countrieschallange.cons.Cons
import com.example.countrieschallange.repository.CountryRepositoryImp
import com.example.countrieschallange.view_model.CountryViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single<CountryService> {

        Retrofit.Builder()
            .baseUrl(Cons.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryService::class.java)
    }
    single { CountryRepositoryImp(get()) }
    single { Dispatchers.IO}
}

val viewModelModule = module {
    viewModel { CountryViewModel(get(), get()) }
}