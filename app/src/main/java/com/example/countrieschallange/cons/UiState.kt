package com.example.countrieschallange.cons

sealed class UiState {
    data class Success(val data: Any) : UiState()
    data class Error(val error: Throwable): UiState()
    object Loading : UiState()
}