package com.example.domain.usecase

import com.example.domain.repository.AuthRepository

class SignInWithGoogleUseCase(val authRepository: AuthRepository) {
    suspend operator fun invoke() {
        authRepository.signInWithGoogle()
    }
}