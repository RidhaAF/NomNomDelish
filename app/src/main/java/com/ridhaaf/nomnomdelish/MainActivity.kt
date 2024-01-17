package com.ridhaaf.nomnomdelish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ridhaaf.nomnomdelish.features.presentation.auth.sign_up.SignUpScreen
import com.ridhaaf.nomnomdelish.ui.theme.NomNomDelishTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NomNomDelishTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App(modifier: Modifier = Modifier) {
    SignUpScreen(modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    NomNomDelishTheme {
        App()
    }
}