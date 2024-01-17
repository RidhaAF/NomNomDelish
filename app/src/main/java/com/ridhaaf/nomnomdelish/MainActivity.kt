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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ridhaaf.nomnomdelish.features.presentation.auth.sign_in.SignInScreen
import com.ridhaaf.nomnomdelish.features.presentation.auth.sign_up.SignUpScreen
import com.ridhaaf.nomnomdelish.features.presentation.routes.Routes
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
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SIGN_IN,
    ) {
        composable(Routes.SIGN_IN) {
            SignInScreen(
                modifier = modifier,
                navController = navController,
            )
        }
        composable(Routes.SIGN_UP) {
            SignUpScreen(
                modifier = modifier,
                navController = navController,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    NomNomDelishTheme {
        App()
    }
}