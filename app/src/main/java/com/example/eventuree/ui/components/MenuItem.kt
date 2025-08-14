package com.example.eventuree.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.example.eventuree.R
import com.example.eventuree.ui.theme.Montserrat

@Composable
@Preview
fun MenuItemPreview() {
    MenuItem("My Profile", R.drawable.profile_icon)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuItem(text: String, icon: Int) {
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(18.dp)) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "Menu Icon",
            tint = Color.White
        )
        Text(
            text = text,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        )
    }
}