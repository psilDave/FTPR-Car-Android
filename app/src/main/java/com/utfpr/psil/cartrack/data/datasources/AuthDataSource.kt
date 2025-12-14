package com.utfpr.psil.cartrack.data.datasources

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.utfpr.psil.cartrack.data.model.PhoneAuthResultEvent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AuthDataSource {
    private val auth = FirebaseAuth.getInstance()

    private var authCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null

    init {
        auth.useAppLanguage()
    }

    fun phoneAuthFlow(): Flow<PhoneAuthResultEvent> = callbackFlow {
        authCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                close()
            }

            override fun onVerificationFailed(authException: FirebaseException) {
                trySend(PhoneAuthResultEvent.Error(authException))
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                trySend(PhoneAuthResultEvent.CodeSent(verificationId))
            }
        }
        awaitClose {
            authCallback = null
        }
    }

    fun sendVerificationCode(phoneNumber: String) {
        authCallback?.let { callback ->
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(AUTH_TIMEOUT_MINUTES, TimeUnit.MINUTES)
                .setCallbacks(callback)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    fun trySignInWithVerificationCode(verificationId: String, code: String) {
        try {
            val credential = PhoneAuthProvider.getCredential(verificationId, code)
            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(PhoneAuthResultEvent.VerificationCompleted)
                } else {
                    trySend(PhoneAuthResultEvent.Error(task.exception ?: Exception()))
                }
            }

        } catch (e: Exception) {

        }


    }

    private companion object {
        const val AUTH_TIMEOUT_MINUTES = 2L
    }


}