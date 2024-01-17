package com.ridhaaf.nomnomdelish.features.presentation.auth.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ridhaaf.nomnomdelish.features.presentation.components.DefaultButton
import com.ridhaaf.nomnomdelish.features.presentation.components.DefaultSpacer
import com.ridhaaf.nomnomdelish.features.presentation.components.DefaultTextField

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Title()
        DefaultSpacer()
        EmailTextField()
        DefaultSpacer()
        PasswordTextField()
        DefaultSpacer()
        SignInButton()
        DefaultSpacer()
        RedirectToSignUp(navController)
    }
}

@Composable
fun Title() {
    Text(
        text = "Sign In",
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
fun EmailTextField() {
    var email by remember { mutableStateOf("") }

    DefaultTextField(
        modifier = Modifier.fillMaxWidth(),
        value = email,
        onValueChange = {
            email = it
        },
        placeholder = {
            Placeholder(text = "Email")
        },
    )
}

@Composable
fun PasswordTextField() {
    var password by remember { mutableStateOf("") }

    DefaultTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = {
            password = it
        },
        placeholder = {
            Placeholder(text = "Password")
        },
        visualTransformation = PasswordVisualTransformation(),
    )
}

@Composable
fun Placeholder(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
    )
}

@Composable
fun SignInButton() {
    DefaultButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {},
        text = "Sign In",
    )
}

@Composable
fun RedirectToSignUp(navController: NavController?) {
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            navController?.navigate("sign-up")
        },
    ) {
        Text(
            text = "Don't have an account? Sign Up",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen()
}