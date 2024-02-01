package com.ridhaaf.nomnomdelish.feature.data.repositories.recipe

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.ridhaaf.nomnomdelish.core.utils.Resource
import com.ridhaaf.nomnomdelish.feature.data.datasources.remote.api.recipe.RecipeApi
import com.ridhaaf.nomnomdelish.feature.data.models.recipe.Recipe
import com.ridhaaf.nomnomdelish.feature.domain.repositories.recipe.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipeApi,
    private val apiKey: String,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
) : RecipeRepository {
    override fun getRandomRecipe(): Flow<Resource<Recipe>> = flow {
        emit(Resource.Loading())

        try {
            val randomRecipe = api.getRandomRecipe(apiKey)
            emit(Resource.Success(randomRecipe.toRecipe()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Oops, something went wrong!"))
        }
    }

    override fun getRecipeById(id: String): Flow<Resource<Recipe>> = flow {
        emit(Resource.Loading())

        try {
            val recipe = api.getRecipeById(apiKey, id)
            emit(Resource.Success(recipe.toRecipe()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Oops, something went wrong!"))
        }
    }

    override fun searchRecipe(query: String): Flow<Resource<Recipe>> = flow {
        emit(Resource.Loading())

        try {
            val recipe = api.searchRecipe(apiKey, query)
            emit(Resource.Success(recipe.toRecipe()))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Oops, something went wrong!"))
        }
    }

    override suspend fun addFavoriteRecipe(id: String) {
        try {
            val userDocument = getUserFavoriteRecipes().await()

            if (userDocument.isEmpty) {
                val favoriteId = favoritesCollection().document().id
                val favoriteData = hashMapOf(
                    "userId" to getUserId(), "recipesId" to mutableListOf(id)
                )

                favoritesCollection().document(favoriteId).set(favoriteData).addOnSuccessListener {
                    println("Success add favorite recipe")
                }.addOnFailureListener {
                    println("Failed add favorite recipe")
                }
            } else {
                val existingDocumentId = userDocument.documents[0].id

                favoritesCollection().document(existingDocumentId).update(
                    "recipesId", FieldValue.arrayUnion(id)
                ).addOnSuccessListener {
                    println("Success add favorite recipe")
                }.addOnFailureListener {
                    println("Failed add favorite recipe")
                }
            }
        } catch (e: Exception) {
            throw Exception(e.localizedMessage ?: "Oops, something went wrong!")
        }
    }

    override suspend fun removeFavoriteRecipe(id: String) {
        try {
            val userDocument = getUserFavoriteRecipes().await()

            val existingDocumentId = userDocument.documents[0].id

            if (existingDocumentId.isNotEmpty()) {
                favoritesCollection().document(existingDocumentId).update(
                    "recipesId", FieldValue.arrayRemove(id)
                ).addOnSuccessListener {
                    println("Success remove favorite recipe")
                }.addOnFailureListener {
                    println("Failed remove favorite recipe")
                }
            }
        } catch (e: Exception) {
            throw Exception(e.localizedMessage ?: "Oops, something went wrong!")
        }
    }

    override fun getFavoriteRecipes(): Flow<Resource<List<String>>> = flow {
        emit(Resource.Loading())

        try {
            val userDocument = getUserFavoriteRecipes().await()

            val recipesId = (userDocument.documents.getOrNull(0)?.get("recipesId") as? List<String>)
                ?: emptyList()
            emit(Resource.Success(recipesId))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Oops, something went wrong!"))
        }
    }

    private fun getUserId(): String {
        return firebaseAuth.currentUser?.uid ?: ""
    }

    private fun favoritesCollection(): CollectionReference {
        return firebaseFirestore.collection("favorites")
    }

    private fun getUserFavoriteRecipes(): Task<QuerySnapshot> {
        val currentUserId = getUserId()

        return favoritesCollection().whereEqualTo("userId", currentUserId).limit(1).get()
    }
}