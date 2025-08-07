package edu.schoolapp.googleSingIn

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import edu.schoolapp.R
import kotlinx.coroutines.tasks.await

class GoogleAuthenticationService {
    suspend fun signIn(context: Context): GoogleAuth? {
        val signInWithGoogleOption = GetSignInWithGoogleOption
            .Builder(serverClientId = context.getString(R.string.default_web_client_id))
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()

        val result = CredentialManager.create(context).getCredential(
            request = request,
            context = context
        )

        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(result.credential.data)
        val credential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
        val authResult = Firebase.auth.signInWithCredential(credential).await()

        authResult.user?.let { user ->
            authResult.credential?.let { credential ->
                val provider = credential.provider
                val token = user.getIdToken(true).await().token
                val userName = user.displayName
                val email = user.email
                val userId = user.uid

                return GoogleAuth(
                    userId = userId,
                    provider = provider,
                    token = token.orEmpty(),
                    userName = userName.orEmpty(),
                    email = email
                )
            }
        }
        return null
    }

    fun logout() {
        Firebase.auth.signOut()
    }
}