package com.ridhaaf.nomnomdelish.feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ridhaaf.nomnomdelish.feature.data.models.User

@Composable
fun DefaultPhotoProfile(
    modifier: Modifier = Modifier,
    user: User,
    iconSize: Dp = 48.dp,
) {
    val photoModifier = modifier.clip(CircleShape)

    if (user.photoProfileUrl != null) {
        AsyncImage(
            model = user.photoProfileUrl,
            contentDescription = user.name,
            modifier = photoModifier,
        )
    } else {
        Box(
            modifier = photoModifier.background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Rounded.Person,
                contentDescription = user.name,
                modifier = Modifier.size(iconSize),
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
    }
}