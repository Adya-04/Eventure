package com.example.eventuree.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventuree.ui.theme.Montserrat
import com.example.eventuree.ui.theme.inputBoxShape

@Composable
fun MultilineInputBox(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = inputBoxShape.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(2.dp, Color(0xFFE4DFDF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = "Input icon",
                modifier = Modifier
                    .size(24.dp)
                    .padding(top = 8.dp)
            )
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(104.dp)
                    .padding(vertical = 8.dp)
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        color = Color(0xFF120D26),
                        fontSize = 18.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium
                    ),
                    cursorBrush = SolidColor(Color(0xFF120D26)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart),
                    decorationBox = { innerTextField ->
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = Color(0xFF747688),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = Montserrat,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                        innerTextField()
                    },
                    singleLine = false,
                    maxLines = 4,
                    enabled = enabled
                )
            }
        }
    }
}