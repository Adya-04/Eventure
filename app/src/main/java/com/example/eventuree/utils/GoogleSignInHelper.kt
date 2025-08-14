package com.example.eventuree.utils

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialOption
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.eventuree.R

class GoogleSignInUtils {

    companion object {
        private const val TAG = "GoogleSignInUtils"

        fun doGoogleSignIn(
            context: Context,
            scope: CoroutineScope,
            launcher: ManagedActivityResultLauncher<Intent, ActivityResult>?,
            login: (idToken: String) -> Unit
        ) {
            val credentialManager = CredentialManager.create(context)
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(getCredentialOptions(context))
                .build()

            scope.launch {
                try {

                    val result = credentialManager.getCredential(context, request)

                    when (val credential = result.credential) {
                        is CustomCredential -> {
                            if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                                val googleIdTokenCredential =
                                    GoogleIdTokenCredential.createFrom(credential.data)
                                val googleTokenId = googleIdTokenCredential.idToken

                                if (googleTokenId != null) {
                                    Log.d(TAG, "ID Token retrieved: $googleTokenId")
                                    login(googleTokenId)
                                } else {
                                    Log.e(TAG, "ID Token is null.")
                                }
                            } else {
                                Log.w(TAG, "Unknown CustomCredential type.")
                            }
                        }
                        else -> {
                            Log.w(TAG, "Unhandled credential type: ${credential.javaClass.simpleName}")
                        }
                    }

                } catch (e: NoCredentialException) {
                    Log.w(TAG, "No credentials found. Launching fallback intent.")
                    launcher?.launch(getIntent())
                } catch (e: GetCredentialException) {
                    Log.e(TAG, "Credential retrieval failed: ${e.message}", e)
                } catch (e: Exception) {
                    Log.e(TAG, "Unexpected error during Google Sign-In: ${e.message}", e)
                }
            }
        }

        private fun getIntent(): Intent {
            return Intent(Settings.ACTION_ADD_ACCOUNT).apply {
                putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
            }
        }

        private fun getCredentialOptions(context: Context): CredentialOption {
            val clientId = context.getString(R.string.web_client_id)
            return GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setAutoSelectEnabled(false)
                .setServerClientId(clientId)
                .build()
        }
    }
}
