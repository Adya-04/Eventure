package com.example.eventuree.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import com.example.eventuree.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventuree.ui.theme.BlueMainColor
import com.example.eventuree.ui.theme.BottomCardShape
import com.example.eventuree.ui.theme.Montserrat

@Preview
@Composable
fun OnboardingBoxPreview() {
    OnboardingBox(
        title = "Explore Upcoming\nCampus Events",
        description = "Never miss out again! See all events happening on campus - from workshops to parties, all in one place.",
        imageRes = R.drawable.first_dot,
        nextTxt = "Next",
        onNext = {},
        onSkip = {}
    )
}

@Composable
fun OnboardingBox(
    title: String, description: String, imageRes: Int, nextTxt: String,
    onNext: () -> Unit, onSkip: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = BlueMainColor),
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp), shape = BottomCardShape.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = Color.White,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 26.sp,
                    lineHeight = 38.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Text(
                text = description,
                color = Color.White,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 26.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(Modifier.height(60.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Skip",
                    color = Color(0xFFABB4FF),
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 26.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.clickable{onSkip()}
                )
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = ""
                )
                Text(
                    text = nextTxt,
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 26.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.clickable{ onNext()}
                )
            }
        }
    }
}