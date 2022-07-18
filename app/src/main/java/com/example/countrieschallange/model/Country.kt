package com.example.countrieschallange.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val name: String?,
    val region: String?,
    val capital: String?
): Parcelable
