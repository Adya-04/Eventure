package com.example.eventure.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.eventure.R

@Composable
@Preview
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.app_bg),
            contentDescription = "App background",
            modifier = Modifier.fillMaxSize()
        )

        Image(
            painter = painterResource(R.drawable.eventure_logo),
            contentDescription = "App Logo"
        )
    }
}