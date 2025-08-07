package com.example.domain.usecase.auth

import com.example.domain.repository.AuthRepository

class LogInWithGoogleUseCase(val authRepository: AuthRepository) {
    suspend operator fun invoke() {
        authRepository.logInWithGoogle()
    }
}