package com.example.eventuree.ui.onboarding

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eventuree.R
import com.example.eventuree.data.models.SendOTPRequest
import com.example.eventuree.ui.components.InputBox
import com.example.eventuree.ui.components.NextButton
import com.example.eventuree.ui.theme.Montserrat
import com.example.eventuree.utils.GoogleSignInUtils
import com.example.eventuree.utils.NetworkResult
import com.example.eventuree.viewmodels.AuthViewModel
import com.example.eventuree.viewmodels.PrefsViewModel

@Preview
@Composable
fun SignupScreenPreview() {
    SignupScreen(
        onOTPSentComplete = { },
        onGoogleSignInComplete = { _, _, _, _, _ -> },
        onNavigateToHome = {},
        authViewModel = hiltViewModel(),
        prefsViewModel = hiltViewModel()
    )
}

@Composable
fun SignupScreen(
    onOTPSentComplete: (String) -> Unit,
    onGoogleSignInComplete: (String, String, String?, String?, String?) -> Unit,
    onSignInClick: () -> Unit = {},
    onNavigateToHome: () -> Unit,
    authViewModel: AuthViewModel,
    prefsViewModel: PrefsViewModel
) {
    var email by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(true) }

    val context = LocalContext.current
    val sendOTPState by authViewModel.sendOTPLiveData.collectAsState()
    val googleSignInState by authViewModel.googleSignInLiveData.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { /* Handle fallback account addition if needed, optional */ }

    // Clear states when the screen is first shown
    LaunchedEffect(Unit) {
        authViewModel.clearGoogleSignInState()
    }

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
                            // Existing user - save token and go directly to home
                            prefsViewModel.saveToken(response.token)
                            authViewModel.setLogin(true)
                            onNavigateToHome()
                        } else {
                            // New user - proceed to details screen
                            onGoogleSignInComplete(
                                response.token,
                                response.user.email,
                                response.user.id,
                                response.user.profilePic,
                                response.user.name
                            )
                        }
                    } else {
                        Toast.makeText(context, "Invalid token received", Toast.LENGTH_SHORT).show()
                    }
                } ?: run {
                    Toast.makeText(context, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
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

    LaunchedEffect(sendOTPState) {
        // API Response Handling
        when (sendOTPState) {
            is NetworkResult.Loading -> {
                isButtonEnabled = false
            }

            is NetworkResult.Success -> {
                isButtonEnabled = true
                Toast.makeText(context, "OTP Sent Successfully", Toast.LENGTH_SHORT).show()
                onOTPSentComplete(email)
            }

            is NetworkResult.Error -> {
                isButtonEnabled = true
                val errorMsg = sendOTPState.message ?: "OTP Send failed"
                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
            }

            is NetworkResult.Start<*> -> Unit
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
                text = "Sign up",
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

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "OR",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF9D9898)
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .shadow(3.dp, shape = RoundedCornerShape(12.dp))  // Shadow added here
                    .clip(RoundedCornerShape(20))
                    .background(color = Color.White)
                    .clickable {
                        GoogleSignInUtils.doGoogleSignIn(
                            context = context,
                            scope = coroutineScope,
                            launcher = launcher,
                            login = { idToken ->
                                authViewModel.signInWithGoogle(idToken)
                            }
                        )
                    }
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 28.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    Image(
                        painter = painterResource(R.drawable.google_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .size(38.dp)
                            .padding(8.dp)
                    )

                    Spacer(Modifier.width(10.dp))

                    Text(
                        text = "Signup with Google",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF120D26)
                        )
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Already have an account? Sign in",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF5669FF)
                ),
                modifier = Modifier.clickable { onSignInClick() }
            )
            Spacer(modifier = Modifier.weight(1f))

            // Loading indicator
            if (sendOTPState is NetworkResult.Loading ||
                googleSignInState is NetworkResult.Loading
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = Color(0xFF5669FF)
                )
            }

            NextButton(
                text = "SIGN UP",
                enabled = isButtonEnabled,
                onClick = {
                    if (email.isBlank()) {
                        Toast.makeText(context, "Email can't be empty", Toast.LENGTH_SHORT).show()
                    } else {
                        authViewModel.sendOTP(sendOTPRequest = SendOTPRequest(email))
                    }
                }
            )
        }
    }
}