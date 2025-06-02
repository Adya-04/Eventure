package com.example.eventure.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventure.R
import com.example.eventure.ui.components.InputBox
import com.example.eventure.ui.components.NextButton
import com.example.eventure.ui.theme.Montserrat

@Composable
@Preview
fun SigninScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.app_bg),
            contentDescription = "App background",
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

            InputBox(text = "abc@gmail.com", icon = R.drawable.mail_icon)
            Spacer(modifier = Modifier.height(18.dp))

            InputBox(text = "Your password", icon = R.drawable.password_icon)
            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Forgot Password?",
                modifier = Modifier.align(Alignment.End),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF120D26)
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 28.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
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
                    fontSize = 18.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF5669FF)
                )
            )
            Spacer(modifier = Modifier.weight(1f))

            NextButton("SIGN IN")
        }
    }
}