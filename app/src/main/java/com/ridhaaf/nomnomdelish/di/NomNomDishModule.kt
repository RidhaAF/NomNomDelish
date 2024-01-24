package com.ridhaaf.nomnomdelish.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ridhaaf.nomnomdelish.feature.data.datasources.remote.api.recipe.RecipeApi
import com.ridhaaf.nomnomdelish.feature.data.repositories.auth.AuthRepositoryImpl
import com.ridhaaf.nomnomdelish.feature.data.repositories.recipe.RecipeRepositoryImpl
import com.ridhaaf.nomnomdelish.feature.domain.repositories.auth.AuthRepository
import com.ridhaaf.nomnomdelish.feature.domain.repositories.recipe.RecipeRepository
import com.ridhaaf.nomnomdelish.feature.domain.usecases.auth.AuthUseCase
import com.ridhaaf.nomnomdelish.feature.domain.usecases.recipe.RecipeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NomNomDishModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideDotEnv(): Dotenv {
        return dotenv {
            directory = "/assets"
            filename = "env"
        }
    }

    @Provides
    @Singleton
    fun provideAuthUseCase(
        repository: AuthRepository,
    ): AuthUseCase {
        return AuthUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
    ): AuthRepository {
        return AuthRepositoryImpl(auth, firestore)
    }

    @Provides
    @Singleton
    fun provideRecipeUseCase(
        repository: RecipeRepository,
    ): RecipeUseCase {
        return RecipeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(
        api: RecipeApi,
        dotenv: Dotenv,
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
    ): RecipeRepository {
        val apiKey = dotenv["API_KEY"]

        return RecipeRepositoryImpl(api, apiKey, auth, firestore)
    }

    @Provides
    @Singleton
    fun provideRecipeApi(dotenv: Dotenv): RecipeApi {
        val baseUrl = dotenv["BASE_URL"]

        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(RecipeApi::class.java)
    }
}