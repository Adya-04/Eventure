package com.example.eventuree.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.eventuree.R
import com.example.eventuree.ui.components.OnboardingBox

@Composable
fun WelcomeScreen2(onNext: () -> Unit, onSkip: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 45.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.screen3),
                contentDescription = ""
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            OnboardingBox(
                title = "We Have Modern Events Calendar Feature",
                description = "Sync events to your personal calendar, get reminders, and track deadlines.\nYour social and academic life, sorted!",
                imageRes = R.drawable.second_dot,
                nextTxt = "Next",
                onNext,
                onSkip
            )
        }
    }
}