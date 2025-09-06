package com.example.eventuree.ui.onboarding

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eventuree.R
import com.example.eventuree.data.models.VerifyOTPRequest
import com.example.eventuree.ui.components.NextButton
import com.example.eventuree.ui.components.OtpInputField
import com.example.eventuree.ui.theme.Montserrat
import com.example.eventuree.utils.NetworkResult
import com.example.eventuree.viewmodels.AuthViewModel

@Preview
@Composable
fun OTPScreenPreview() {
    OTPScreen(
        email = "", onOTPConfirm = {},
        authViewModel = hiltViewModel()
    )
}

@Composable
fun OTPScreen(
    email: String = "",
    onOTPConfirm: () -> Unit,
    authViewModel: AuthViewModel
) {
    val otpLength = 4
    val otpValues = remember { mutableStateListOf<Int?>(null, null, null, null) }
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current
    val verifyOTPState by authViewModel.verifyOTPLiveData.collectAsState()
    var isButtonEnabled by remember { mutableStateOf(true) }

    var timerSeconds by remember { mutableStateOf(60) }
    var isTimerRunning by remember { mutableStateOf(true) }

    // Start countdown timer
    LaunchedEffect(key1 = isTimerRunning) {
        if (isTimerRunning) {
            while (timerSeconds > 0) {
                kotlinx.coroutines.delay(1000L)
                timerSeconds--
            }
            isTimerRunning = false
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
                .padding(28.dp)
        ) {
            Spacer(Modifier.width(12.dp))
            Text(
                text = "Verification",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF120D26)
                )
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = buildAnnotatedString {
                    append("We’ve sent you the verification\ncode on ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(email)
                    }
                },
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF120D26),
                    lineHeight = 28.sp
                )
            )
            Spacer(Modifier.height(40.dp))

            // OTP Input Fields
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                repeat(otpLength) { index ->
                    OtpInputField(
                        number = otpValues[index],
                        focusRequester = focusRequesters[index],
                        onFocusChanged = { hasFocus ->
                            if (hasFocus && otpValues[index] == null) {
                                // Clear previous fields if we're moving backward
                                for (i in index until otpLength) {
                                    otpValues[i] = null
                                }
                            }
                        },
                        onNumberChanged = { number ->
                            otpValues[index] = number
                            if (number != null && index < otpLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            } else if (index == otpLength - 1 && number != null) {
                                // Last field filled - you might want to submit here
                                focusManager.clearFocus()
                                //Submit Functionality handled in NExt button
                            }
                        },
                        onKeyboardBack = {
                            if (index > 0) {
                                otpValues[index - 1] = null
                                focusRequesters[index - 1].requestFocus()
                            }
                        },
                        modifier = Modifier.width(60.dp)
                    )

                    if (index < otpLength - 1) {
                        Spacer(Modifier.width(16.dp))
                    }
                }
            }
            Spacer(Modifier.height(80.dp))
            // Resend OTP text
            Text(
                text = if (isTimerRunning) "Resend OTP in 0:${
                    timerSeconds.toString().padStart(2, '0')
                }"
                else "Resend OTP",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    color = if (isTimerRunning) Color.Gray else Color(0xFF5669FF) // dim when disabled
//                            Color(0xFF120D26)
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .let {
                        if (!isTimerRunning) {
                            it.clickable {
                                // Simulate resend logic here
                                Toast.makeText(context, "OTP Resent", Toast.LENGTH_SHORT).show()
                                timerSeconds = 60
                                isTimerRunning = true
                            }
                        } else it
                    }
            )
            Spacer(Modifier.weight(1f))
            // Loading or Error State
            when (verifyOTPState) {
                is NetworkResult.Loading -> {
                    isButtonEnabled = false
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .align(Alignment.CenterHorizontally),
                        color = Color(0xFF5669FF)
                    )
                }

                is NetworkResult.Success -> {
                    isButtonEnabled = true
                    Toast.makeText(context, "OTP Verified Successfully", Toast.LENGTH_SHORT).show()
                    onOTPConfirm()
                }

                is NetworkResult.Error -> {
                    isButtonEnabled = true
                    val errorMsg = verifyOTPState.message ?: "OTP Verification failed"
                    LaunchedEffect(verifyOTPState) {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                    }
                }

                is NetworkResult.Start<*> -> Unit

            }
            NextButton(
                text = "CONTINUE",
                enabled = isButtonEnabled,
                onClick = {
                    val otp = otpValues.joinToString("") { it?.toString() ?: "" }
                    if (otp.length == otpLength) {
                        authViewModel.verifyOTP(VerifyOTPRequest(email, otp))
                    } else {
                        Toast.makeText(context, "Please enter complete OTP", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
        }
    }

    // Auto-focus the first field when the screen loads
    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }
}