package com.example.domain.usecase

import com.example.domain.repository.AuthRepository

class SignOutUseCase(val authRepository: AuthRepository) {
    suspend operator fun invoke() {
        authRepository.signOut()
    }
}