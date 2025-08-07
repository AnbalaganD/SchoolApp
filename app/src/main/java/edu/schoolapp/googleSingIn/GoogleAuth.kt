package edu.schoolapp.googleSingIn

data class GoogleAuth(
    val userId: String,
    val provider: String,
    val token: String,
    val userName: String,
    val email: String?
)