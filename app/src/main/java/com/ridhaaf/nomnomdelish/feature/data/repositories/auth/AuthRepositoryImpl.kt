package com.ridhaaf.nomnomdelish.feature.data.repositories.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.ridhaaf.nomnomdelish.core.utils.Resource
import com.ridhaaf.nomnomdelish.feature.data.models.User
import com.ridhaaf.nomnomdelish.feature.domain.repositories.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
) : AuthRepository {
    override fun isAuthenticated(): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())

            val result = firebaseAuth.currentUser != null

            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override fun signUp(
        name: String,
        email: String,
        password: String,
    ): Flow<Resource<AuthResult>> = flow {
        try {
            emit(Resource.Loading())

            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            val user = result.user
            insertUser(
                user?.uid ?: "",
                name,
                email,
            )

            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override fun signIn(
        email: String,
        password: String,
    ): Flow<Resource<AuthResult>> = flow {
        try {
            emit(Resource.Loading())

            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override fun signInWithGoogle(credential: AuthCredential): Flow<Resource<AuthResult>> = flow {
        try {
            emit(Resource.Loading())

            val result = firebaseAuth.signInWithCredential(credential).await()

            val user = result.user
            insertUser(
                user?.uid ?: "",
                user?.displayName ?: "",
                user?.email ?: "",
                user?.photoUrl.toString(),
            )

            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    private suspend fun insertUser(
        id: String,
        name: String,
        email: String,
        photoProfileUrl: String? = null,
    ) {
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "photoProfileUrl" to photoProfileUrl,
        )
        firebaseFirestore.collection("users").document(id).set(user).await()
    }

    override fun signOut(): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            val result = firebaseAuth.signOut()

            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    override fun getCurrentUser(): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())

            val result = firebaseAuth.currentUser
            if (result != null) {
                val isSignedInWithGoogle =
                    result.providerData.any { it.providerId == GoogleAuthProvider.PROVIDER_ID }

                val user = User()
                if (isSignedInWithGoogle) {
                    user.name = result.displayName ?: ""
                    user.email = result.email ?: ""
                    user.photoProfileUrl = result.photoUrl.toString()
                } else {
                    val id = result.uid
                    val document = firebaseFirestore.collection("users").document(id).get().await()

                    user.name = document["name"] as String? ?: ""
                    user.email = document["email"] as String? ?: ""
                    user.photoProfileUrl = document["photoProfileUrl"] as String?
                }
                emit(Resource.Success(user))
            } else {
                emit(Resource.Error("User not found"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}