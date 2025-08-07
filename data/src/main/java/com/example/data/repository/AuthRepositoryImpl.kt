package com.example.data.repository

import com.example.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signInWithGoogle() {
        // Stub for Credential Manager or One Tap if implemented later
        throw NotImplementedError("Google Sign-In not yet implemented in data layer")
    }

    override suspend fun signUp(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun checkIfLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

}
