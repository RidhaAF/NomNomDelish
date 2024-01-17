package com.ridhaaf.nomnomdelish.feature.presentation.auth.sign_up

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultButton
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultSpacer
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultTextField

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Title("Sign Up")
        DefaultSpacer()
        NameTextField()
        DefaultSpacer()
        EmailTextField()
        DefaultSpacer()
        PasswordTextField()
        DefaultSpacer()
        ConfirmPasswordTextField()
        DefaultSpacer()
        SignUpButton(viewModel = viewModel)
        DefaultSpacer()
        RedirectToSignIn(navController)
    }
}

@Composable
fun Title(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
fun NameTextField() {
    var name by remember { mutableStateOf("") }

    DefaultTextField(
        value = name,
        onValueChange = {
            name = it
        },
        placeholder = "Name",
    )
}

@Composable
fun EmailTextField() {
    var email by rememberSaveable { mutableStateOf("") }

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
fun ConfirmPasswordTextField() {
    var confirmPassword by remember { mutableStateOf("") }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }
    val visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None
    else PasswordVisualTransformation()

    DefaultTextField(
        value = confirmPassword,
        onValueChange = {
            confirmPassword = it
        },
        placeholder = "Confirm Password",
        visualTransformation = visualTransformation,
        trailingIcon = {
            IconButton(
                onClick = {
                    confirmPasswordVisibility = !confirmPasswordVisibility
                },
            ) {
                val icon = if (confirmPasswordVisibility) Icons.Rounded.VisibilityOff
                else Icons.Rounded.Visibility
                val contentDescription = if (confirmPasswordVisibility) "Hide confirm password"
                else "Show confirm password"

                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                )
            }
        },
    )
}

@Composable
fun SignUpButton(viewModel: SignUpViewModel) {
    DefaultButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {},
        text = "Sign Up",
    )
}

@Composable
fun RedirectToSignIn(navController: NavController?) {
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            navController?.popBackStack()
        },
    ) {
        Text(
            text = "Already have an account? Sign In",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}