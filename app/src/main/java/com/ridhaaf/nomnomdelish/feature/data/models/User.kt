package com.ridhaaf.nomnomdelish.feature.data.models

data class User(
    val name: String,
    val email: String,
    val photoProfileUrl: String? = null,
)
