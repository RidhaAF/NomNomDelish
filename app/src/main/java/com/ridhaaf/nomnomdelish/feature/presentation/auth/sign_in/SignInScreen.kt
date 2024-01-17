package com.ridhaaf.nomnomdelish.feature.presentation.auth.sign_in

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultButton
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultSpacer
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultTextField

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
        value = email,
        onValueChange = {
            email = it
        },
        placeholder = "Email",
    )
}

@Composable
fun PasswordTextField() {
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val visualTransformation = if (passwordVisibility) VisualTransformation.None
    else PasswordVisualTransformation()

    DefaultTextField(
        value = password,
        onValueChange = {
            password = it
        },
        placeholder = "Password",
        visualTransformation = visualTransformation,
        trailingIcon = {
            IconButton(
                onClick = {
                    passwordVisibility = !passwordVisibility
                },
            ) {
                val icon = if (passwordVisibility) Icons.Rounded.VisibilityOff
                else Icons.Rounded.Visibility
                val contentDescription = if (passwordVisibility) "Hide password"
                else "Show password"

                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                )
            }
        },
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