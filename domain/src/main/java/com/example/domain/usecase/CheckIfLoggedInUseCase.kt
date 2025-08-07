package com.example.domain.usecase

import com.example.domain.repository.AuthRepository

class CheckIfLoggedInUseCase(val authRepository: AuthRepository) {
    suspend operator fun invoke(): Boolean {
        return authRepository.checkIfLoggedIn()
    }
}