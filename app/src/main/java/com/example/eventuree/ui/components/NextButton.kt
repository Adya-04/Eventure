package com.example.eventuree.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventuree.ui.theme.BlueMainColor
import com.example.eventuree.ui.theme.Montserrat
import com.example.eventuree.ui.theme.inputBoxShape
import com.example.eventuree.R

@Preview
@Composable
fun NextButtonPreview() {
    NextButton("SIGN IN", onClick = {})
}

@Composable
fun NextButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(enabled = enabled) { onClick() },
        shape = inputBoxShape.medium,
        colors = CardDefaults.cardColors(containerColor = BlueMainColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier.align(Alignment.Center),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium
                )
            )

            Image(
                painter = painterResource(R.drawable.next_icon),
                contentDescription = "Next Button",
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}