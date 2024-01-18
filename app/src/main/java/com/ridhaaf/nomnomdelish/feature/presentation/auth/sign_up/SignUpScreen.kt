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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultButton
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultSpacer
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultTextField
import com.ridhaaf.nomnomdelish.feature.presentation.components.GoogleButton
import com.ridhaaf.nomnomdelish.feature.presentation.components.OrSignWith
import java.util.Locale

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val state = viewModel.state.value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Title("Sign Up")
        DefaultSpacer()
        NameTextField(viewModel)
        DefaultSpacer()
        EmailTextField(viewModel)
        DefaultSpacer()
        PasswordTextField(viewModel)
        DefaultSpacer()
        ConfirmPasswordTextField(viewModel)
        DefaultSpacer()
        SignUpButton(
            viewModel = viewModel,
            state = state,
        )
        DefaultSpacer()
        OrSignUpWith()
        DefaultSpacer()
        GoogleSignUpButton()
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
fun NameTextField(viewModel: SignUpViewModel) {
    DefaultTextField(
        value = viewModel.name,
        onValueChange = {
            viewModel.onEvent(SignUpEvent.OnNameChange(it))
        },
        placeholder = "Name",
    )
}

@Composable
fun EmailTextField(viewModel: SignUpViewModel) {
    DefaultTextField(
        value = viewModel.email,
        onValueChange = {
            viewModel.onEvent(SignUpEvent.OnEmailChange(it))
        },
        placeholder = "Email",
    )
}

@Composable
fun PasswordTextFieldContent(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    DefaultTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        isObscure = !passwordVisibility,
        trailingIcon = {
            IconButton(
                onClick = {
                    passwordVisibility = !passwordVisibility
                },
            ) {
                val icon = if (passwordVisibility) Icons.Rounded.VisibilityOff
                else Icons.Rounded.Visibility
                val contentDescription =
                    if (passwordVisibility) "Hide ${placeholder.lowercase(Locale.getDefault())}"
                    else "Show ${placeholder.lowercase(Locale.getDefault())}"

                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                )
            }
        },
    )
}

@Composable
fun PasswordTextField(viewModel: SignUpViewModel) {
    PasswordTextFieldContent(
        value = viewModel.password,
        onValueChange = {
            viewModel.onEvent(SignUpEvent.OnPasswordChange(it))
        },
        placeholder = "Password",
    )
}

@Composable
fun ConfirmPasswordTextField(viewModel: SignUpViewModel) {
    PasswordTextFieldContent(
        value = viewModel.confirmPassword,
        onValueChange = {
            viewModel.onEvent(SignUpEvent.OnConfirmPasswordChange(it))
        },
        placeholder = "Confirm Password",
    )
}

@Composable
fun SignUpButton(viewModel: SignUpViewModel, state: SignUpState) {
    val text = if (state.isLoading) "Signing Up..." else "Sign Up"

    DefaultButton(
        onClick = {
            viewModel.onEvent(SignUpEvent.OnSignUpClick)
        },
        child = {
            Text(text = text)
        },
    )
}

@Composable
fun OrSignUpWith() {
    OrSignWith("Or sign up with")
}

@Composable
fun GoogleSignUpButton() {
    GoogleButton(
        onClick = {},
        text = "Sign Up with Google",
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