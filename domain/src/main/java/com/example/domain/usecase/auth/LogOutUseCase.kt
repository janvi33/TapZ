package com.example.domain.usecase.auth

import com.example.domain.repository.AuthRepository

class LogOutUseCase(val authRepository: AuthRepository) {
    suspend operator fun invoke() {
        authRepository.logOut()
    }
}