package com.example.countrieschallange.cons

sealed class UiState {
    data class Success(val data: Any) : UiState()
    data class Error(val code: Int, val message: String): UiState()
    object Loading : UiState()
}