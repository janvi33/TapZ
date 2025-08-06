package com.example.domain.usecase

import com.example.domain.repository.AuthRepository

class SignInUseCase(val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) {
        authRepository.signIn(email, password)
    }
}