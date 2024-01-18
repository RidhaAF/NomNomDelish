package com.ridhaaf.nomnomdelish.feature.domain.usecases.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.ridhaaf.nomnomdelish.core.utils.Resource
import com.ridhaaf.nomnomdelish.feature.data.models.User
import com.ridhaaf.nomnomdelish.feature.domain.repositories.auth.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthUseCase(private val repository: AuthRepository) {
    fun signUp(
        name: String,
        email: String,
        password: String,
    ): Flow<Resource<AuthResult>> {
        return repository.signUp(name, email, password)
    }

    fun signIn(
        email: String,
        password: String,
    ): Flow<Resource<AuthResult>> {
        return repository.signIn(email, password)
    }

    fun signInWithGoogle(credential: AuthCredential): Flow<Resource<AuthResult>> {
        return repository.signInWithGoogle(credential)
    }

    fun signOut(): Flow<Resource<Unit>> {
        return repository.signOut()
    }

    fun getCurrentUser(): Flow<Resource<User>> {
        return repository.getCurrentUser()
    }
}