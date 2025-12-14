package com.utfpr.psil.cartrack.data.model

import com.google.firebase.auth.PhoneAuthCredential

sealed class PhoneAuthResultEvent {
    data class CodeSent(val verificationId: String) : PhoneAuthResultEvent()
    data object VerificationCompleted : PhoneAuthResultEvent()
    data class Error(val exception: Exception) : PhoneAuthResultEvent()
}