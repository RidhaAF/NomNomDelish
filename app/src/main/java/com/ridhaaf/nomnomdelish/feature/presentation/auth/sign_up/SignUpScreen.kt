package com.ridhaaf.nomnomdelish.feature.presentation.auth.sign_up

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.ridhaaf.nomnomdelish.R
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultButton
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultSpacer
import com.ridhaaf.nomnomdelish.feature.presentation.components.DefaultTextField
import com.ridhaaf.nomnomdelish.feature.presentation.components.GoogleButton
import com.ridhaaf.nomnomdelish.feature.presentation.components.OrSignWith
import com.ridhaaf.nomnomdelish.feature.presentation.routes.Routes
import java.util.Locale

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val state = viewModel.state.value
    val error = state.error
    val googleState = viewModel.googleState.value
    val googleError = googleState.error
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.isAuth()) {
        if (viewModel.isAuth()) {
            redirectAfterSignUp(navController)
        }
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                viewModel.signUpWithGoogle(credentials)
            } catch (it: ApiException) {
                println(it)
            }
        }

    LaunchedEffect(key1 = error) {
        if (error.isNotBlank()) {
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(key1 = googleError) {
        if (googleError.isNotBlank()) {
            Toast.makeText(context, googleError, Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(key1 = state.isSignUpSuccess) {
        if (state.isSignUpSuccess) {
            redirectAfterSignUp(navController)
        }
    }

    LaunchedEffect(key1 = googleState.isSignUpWithGoogleSuccess) {
        if (googleState.isSignUpWithGoogleSuccess) {
            redirectAfterSignUp(navController)
            viewModel.resetState()
        }
    }

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
        GoogleSignUpButton(
            context = context,
            launcher = launcher,
            googleState = googleState,
        )
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
    val text = if (state.isLoading) "Signing up..." else "Sign up"

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
fun GoogleSignUpButton(
    context: Context,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    googleState: SignUpWithGoogleState,
) {
    val text = if (googleState.isLoading) "Signing up..." else "Sign up with Google"

    GoogleButton(
        onClick = {
            val googleSignInOptions =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                    .requestIdToken(context.getString(R.string.web_client_id)).build()

            val googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)

            launcher.launch(googleSignInClient.signInIntent)
        },
        text = text,
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

private fun redirectAfterSignUp(navController: NavController?) {
    navController?.navigate(Routes.HOME) {
        popUpTo(Routes.HOME) {
            inclusive = true
        }
    }
}