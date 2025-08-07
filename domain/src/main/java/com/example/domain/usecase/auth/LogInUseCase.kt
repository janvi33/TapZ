package com.example.domain.usecase.auth

import com.example.domain.repository.AuthRepository

class LogInUseCase(val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) {
        authRepository.logIn(email, password)
    }
}