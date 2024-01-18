package com.ridhaaf.nomnomdelish.feature.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ridhaaf.nomnomdelish.R

@Composable
fun GoogleButton(
    onClick: () -> Unit,
    text: String,
) {
    DefaultButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
        ),
        child = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Image(
                    modifier = Modifier.height(24.dp),
                    painter = painterResource(R.drawable.ic_google_g),
                    contentDescription = "Google",
                )
                DefaultSpacer(
                    horizontal = true,
                    size = 8,
                )
                Text(text)
            }
        },
    )
}