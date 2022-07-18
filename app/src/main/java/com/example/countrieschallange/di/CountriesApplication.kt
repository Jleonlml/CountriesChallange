package com.example.countrieschallange.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CountriesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CountriesApplication)
            modules(listOf(networkModule, viewModelModule))
        }
    }
}