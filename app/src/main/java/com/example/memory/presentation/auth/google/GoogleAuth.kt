package com.example.memory.presentation.auth.google

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.example.memory.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoogleAuth(
    private val context: Context,
    private val activity: Activity
) {

    private val credentialManager = CredentialManager.create(context)

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun signInWithGoogle(
        onResult: (Boolean, FirebaseUser?, String?) -> Unit
    ) {
        try {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(true)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(context, request)
            val credential = result.credential

            handleSignIn(credential, "GoogleAuth") { success, user ->
                onResult(success, user, null)
            }

        } catch (e: GetCredentialException) {
            Log.e("GoogleAuth", "Credential exception: ${e.localizedMessage}")
            onResult(false, null, e.localizedMessage)
        } catch (e: Exception) {
            Log.e("GoogleAuth", "Exception: ${e.localizedMessage}")
            onResult(false, null, e.localizedMessage)
        }
    }


//    fun signOut() {
//        firebaseAuth.signOut()
//    }

    private suspend fun handleSignIn(
        credential: Credential,
        TAG: String, onResult:
            (Boolean, FirebaseUser?) -> Unit) {
        // Check if credential is of type Google ID
        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            // Create Google ID Token
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

            // Sign in to Firebase with using the token
            val (success,user) = firebaseAuthWithGoogle(googleIdTokenCredential.idToken,TAG)
            onResult(success,user)
        } else {
            Log.w(TAG, "Credential is not of type Google ID!")
        }
    }

    private suspend fun firebaseAuthWithGoogle(idToken: String, TAG:String): Pair<Boolean, FirebaseUser?> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            val user = result.user
            Pair(true, user)
        }catch (e:Exception){
            Pair(false, null)
        }
    }


}
