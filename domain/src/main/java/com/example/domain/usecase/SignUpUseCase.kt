package com.example.domain.usecase

import com.example.domain.repository.AuthRepository

class SignUpUseCase(val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) {
        authRepository.signUp(email, password)
    }
}