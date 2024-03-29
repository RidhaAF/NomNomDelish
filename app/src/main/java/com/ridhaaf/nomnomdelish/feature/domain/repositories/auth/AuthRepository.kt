package com.ridhaaf.nomnomdelish.feature.domain.repositories.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.ridhaaf.nomnomdelish.core.utils.Resource
import com.ridhaaf.nomnomdelish.feature.data.models.auth.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isAuthenticated(): Flow<Resource<Boolean>>

    fun signUp(
        name: String,
        email: String,
        password: String,
    ): Flow<Resource<AuthResult>>

    fun signIn(
        email: String,
        password: String,
    ): Flow<Resource<AuthResult>>

    fun signInWithGoogle(credential: AuthCredential): Flow<Resource<AuthResult>>

    fun signOut(): Flow<Resource<Unit>>

    fun getCurrentUser(): Flow<Resource<User>>
}