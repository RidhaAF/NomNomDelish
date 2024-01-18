package com.ridhaaf.nomnomdelish.feature.presentation.auth.sign_in

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

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel(),
    navController: NavController? = null,
) {
    val state = viewModel.state.value
    val error = state.error
    val googleState = viewModel.googleState.value
    val googleError = googleState.error
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                viewModel.signInWithGoogle(credentials)
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
        GoogleSignInButton(
            context = context,
            launcher = launcher,
            googleState = googleState,
        )
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
    OrSignWith("Or sign in with")
}

@Composable
fun GoogleSignInButton(
    context: Context,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    googleState: SignInWithGoogleState,
) {
    val text = if (googleState.isLoading) "Signing In..." else "Sign In with Google"

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