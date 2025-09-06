package com.example.eventuree.ui.onboarding

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eventuree.R
import com.example.eventuree.data.models.LoginRequest
import com.example.eventuree.ui.components.InputBox
import com.example.eventuree.ui.components.NextButton
import com.example.eventuree.ui.components.PasswordInputBox
import com.example.eventuree.ui.theme.Montserrat
import com.example.eventuree.utils.GoogleSignInUtils
import com.example.eventuree.utils.NetworkResult
import com.example.eventuree.viewmodels.AuthViewModel
import com.example.eventuree.viewmodels.IAuthViewModel
import com.example.eventuree.viewmodels.PrefsViewModel

//@Preview
//@Composable
//fun SigninScreenPreview() {
//    SigninScreen(
//        onSignInComplete = {}, onSignupClick = {},
//        authViewModel = AuthViewModelPreview(),
//
//    )
//}

@Composable
fun SigninScreen(
    onSignInComplete: () -> Unit,
    onSignupClick: () -> Unit,
    authViewModel: IAuthViewModel = hiltViewModel<AuthViewModel>(),
    prefsViewModel: PrefsViewModel
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val loginState by authViewModel.loginLiveData.collectAsState()
    val googleSignInState by authViewModel.googleSignInLiveData.collectAsState()
    var isButtonEnabled by remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { /* Handle fallback account addition if needed, optional */ }

// Handle Google Sign-In state changes
    LaunchedEffect(googleSignInState) {
        when (googleSignInState) {
            is NetworkResult.Loading -> {
                isButtonEnabled = false
            }
            is NetworkResult.Success -> {
                isButtonEnabled = true
                googleSignInState.data?.let { response ->
                    if (response.token.isNotEmpty()) {
                        if (response.isLoggingInWithGoogle) {
                            // Existing user - proceed with login
                            Log.d("Login token ", response.token)
                            prefsViewModel.saveToken(response.token)
                            authViewModel.setLogin(true)
                            onSignInComplete()
                        } else {
                            // New user - show message to create account
                            Toast.makeText(
                                context,
                                "Account doesn't exist. Please sign up first",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(context, "Invalid token received", Toast.LENGTH_SHORT).show()
                    }
                } ?: run {
                    Toast.makeText(context, "Google Sign-In failed: No data received", Toast.LENGTH_SHORT).show()
                }
            }
            is NetworkResult.Error -> {
                isButtonEnabled = true
                val errorMsg = googleSignInState.message ?: "Google Sign-In failed"
                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.app_bg),
            contentDescription = "App background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(22.dp))
            Image(
                painter = painterResource(R.drawable.logo_only),
                contentDescription = "",
                modifier = Modifier.size(60.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Eventure",
                style = TextStyle(
                    fontSize = 38.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF37364A)
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Sign in",
                modifier = Modifier.align(Alignment.Start),
                style = TextStyle(
                    fontSize = 28.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(modifier = Modifier.height(20.dp))

            InputBox(
                value = email.trim(),
                onValueChange = { email = it.trim() },
                placeholder = "abc@gmail.com",
                icon = R.drawable.mail_icon,
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(18.dp))

            PasswordInputBox(
                value = password.trim(),
                onValueChange = { password = it.trim() },
                placeholder = "Your password"
            )


            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Forgot Password?",
                modifier = Modifier.align(Alignment.End),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF120D26)
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "OR",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF9D9898)
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(50))
                    .padding(horizontal = 28.dp)
                    .clickable{
                        GoogleSignInUtils.doGoogleSignIn(
                            context = context,
                            scope = coroutineScope,
                            launcher = launcher,
                            login = { idToken ->
                                authViewModel.signInWithGoogle(idToken)
                            }
                        )
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(R.drawable.google_icon),
                    contentDescription = "",
                    modifier = Modifier.padding(8.dp)
                )

                Spacer(Modifier.width(10.dp))

                Text(
                    text = "Login with Google",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF120D26)
                    )
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Don't have an account? Sign up",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF5669FF)
                ),
                modifier = Modifier.clickable{ onSignupClick()}
            )
            Spacer(modifier = Modifier.weight(1f))
            // API Response Handling
            when (loginState) {
                is NetworkResult.Loading -> {
                    isButtonEnabled = false
                    CircularProgressIndicator(
                        modifier = Modifier.padding(top = 16.dp),
                        color = Color(0xFF5669FF)
                    )
                }

                is NetworkResult.Success -> {
                    Log.d("Token", loginState.data?.accessToken.toString())
                    isButtonEnabled = true
                    loginState.data?.let {
                        prefsViewModel.saveToken(it.accessToken)
                    }
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    authViewModel.setLogin(true)
                    onSignInComplete()
                }

                is NetworkResult.Error -> {
                    isButtonEnabled = true
                    val errorMsg = loginState.message ?: "Login failed"
                    LaunchedEffect(loginState) {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                    }

                }

                is NetworkResult.Start<*> -> Unit
            }

            NextButton(
                text = "SIGN IN",
                enabled = isButtonEnabled,
                onClick = {
                    if (email.isBlank()) {
                        Toast.makeText(context, "Email can't be empty", Toast.LENGTH_SHORT).show()
                    } else if (password.isBlank()) {
                        Toast.makeText(context, "Password can't be empty", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        authViewModel.login(LoginRequest(email, password))
                    }
                }
            )
        }
    }
}