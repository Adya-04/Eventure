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
fun WelcomeScreen1(onNext: () -> Unit, onSkip: () -> Unit) {
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
                painter = painterResource(R.drawable.screen1),
                contentDescription = ""
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            OnboardingBox(
                title = "Explore Upcoming\nCampus Events",
                description = "Never miss out again! See all events happening on campus - from workshops to parties, all in one place.",
                imageRes = R.drawable.first_dot,
                nextTxt = "Next",
                onNext,
                onSkip
            )
        }
    }
}