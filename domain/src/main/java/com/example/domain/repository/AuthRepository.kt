package com.example.domain.repository

interface AuthRepository {
    suspend fun signIn(email: String, password: String)
    suspend fun signInWithGoogle()
    suspend fun signUp(email: String, password: String)
    suspend fun signOut()
    suspend fun checkIfLoggedIn(): Boolean

}