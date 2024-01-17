package com.ridhaaf.nomnomdelish.feature.presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
    ) {
        Text(text = text)
    }
}