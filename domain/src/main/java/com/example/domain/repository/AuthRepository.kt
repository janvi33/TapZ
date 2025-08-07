package com.example.domain.repository

interface AuthRepository {
    suspend fun logIn(email: String, password: String)
    suspend fun logInWithGoogle()
    suspend fun signUp(email: String, password: String)
    suspend fun logOut()
    suspend fun checkIfLoggedIn(): Boolean

}