package com.ridhaaf.nomnomdelish.feature.presentation.auth.sign_in

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ridhaaf.nomnomdelish.R
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultButton
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultSpacer
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultTextField

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val state = viewModel.state.value

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Title("Sign In")
        DefaultSpacer()
        EmailTextField(viewModel)
        DefaultSpacer()
        PasswordTextField(viewModel)
        DefaultSpacer()
        SignInButton(
            viewModel = viewModel,
            state = state,
        )
        DefaultSpacer()
        OrSignInWith()
        DefaultSpacer()
        GoogleSignInButton()
        DefaultSpacer()
        RedirectToSignUp(navController)
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
fun EmailTextField(viewModel: SignInViewModel) {
    DefaultTextField(
        value = viewModel.email,
        onValueChange = {
            viewModel.onEvent(SignInEvent.OnEmailChange(it))
        },
        placeholder = "Email",
    )
}

@Composable
fun PasswordTextField(viewModel: SignInViewModel) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    DefaultTextField(
        value = viewModel.password,
        onValueChange = {
            viewModel.onEvent(SignInEvent.OnPasswordChange(it))
        },
        placeholder = "Password",
        isObscure = !passwordVisibility,
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
fun SignInButton(viewModel: SignInViewModel, state: SignInState) {
    val text = if (state.isLoading) "Signing In..." else "Sign In"

    DefaultButton(
        onClick = {
            viewModel.onEvent(SignInEvent.OnSignInClick)
        },
        child = {
            Text(text = text)
        },
    )
}

@Composable
fun OrSignInWith() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Divider(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .padding(end = 16.dp),
        )
        Text(
            text = "Or sign in with",
        )
        Divider(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .padding(start = 16.dp),
        )
    }
}

@Composable
fun GoogleSignInButton() {
    DefaultButton(
        onClick = {},
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
                Text(text = "Sign In with Google")
            }
        },
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