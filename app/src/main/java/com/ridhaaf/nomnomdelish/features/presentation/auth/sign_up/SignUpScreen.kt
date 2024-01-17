package com.ridhaaf.nomnomdelish.features.presentation.auth.sign_up

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
import com.ridhaaf.nomnomdelish.features.presentation.components.DefaultButton
import com.ridhaaf.nomnomdelish.features.presentation.components.DefaultSpacer
import com.ridhaaf.nomnomdelish.features.presentation.components.DefaultTextField

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Title()
        DefaultSpacer()
        NameTextField()
        DefaultSpacer()
        EmailTextField()
        DefaultSpacer()
        PasswordTextField()
        DefaultSpacer()
        ConfirmPasswordTextField()
        DefaultSpacer()
        SignUpButton()
        DefaultSpacer()
        RedirectToSignIn()
    }
}

@Composable
fun Title() {
    Text(
        text = "Sign Up",
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
fun NameTextField() {
    var name by remember { mutableStateOf("") }

    DefaultTextField(
        modifier = Modifier.fillMaxWidth(),
        value = name,
        onValueChange = {
            name = it
        },
        placeholder = {
            Placeholder(text = "Name")
        },
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
fun ConfirmPasswordTextField() {
    var confirmPassword by remember { mutableStateOf("") }

    DefaultTextField(
        modifier = Modifier.fillMaxWidth(),
        value = confirmPassword,
        onValueChange = {
            confirmPassword = it
        },
        placeholder = {
            Placeholder(text = "Confirm Password")
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
fun SignUpButton() {
    DefaultButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {},
        text = "Sign Up",
    )
}

@Composable
fun RedirectToSignIn() {
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = {},
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