package com.example.eventuree.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventuree.R
import com.example.eventuree.ui.theme.BlueMainColor
import com.example.eventuree.ui.theme.Montserrat
import com.example.eventuree.ui.theme.TopCardShape

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Card(
            colors = CardDefaults.cardColors(containerColor = BlueMainColor),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = TopCardShape.large
        ) {}

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, start = 10.dp, end = 10.dp)
        ) {
            // Profile Image
            Image(
                painter = painterResource(id = R.drawable.dummy),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )

            // Name
            Text(
                text = "Abhinav Gupta",
                style = TextStyle(
                    color = Color(0xFF120D26),
                    fontSize = 24.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.padding(top = 8.dp)
            )

            // Edit Profile Button
            OutlinedButton(
                onClick = { /* TODO */ },
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.3.dp, Color(0xFF5669FF)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF5669FF)
                ),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit_icon), // Replace with actual edit icon
                    contentDescription = "Edit",
                    modifier = Modifier.size(18.dp),
                    tint = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Edit Profile",
                    style = TextStyle(
                        color = Color(0xFF5669FF),
                        fontSize = 16.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            // About Me Section
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "About Me",
                    style = TextStyle(
                        color = Color(0xFF120D26),
                        fontSize = 18.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = "Enjoy your favorite dish and a lovely your friends and family and have a great time. Food from local food trucks will be available for purchase. ",
                    style = TextStyle(
                        color = Color(0xFF3C3E56),
                        fontSize = 16.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
            Spacer(Modifier.height(18.dp))

            // Following Section
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Following",
                        style = TextStyle(
                            color = Color(0xFF120D26),
                            fontSize = 18.sp,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Medium
                        )
                    )
                        Text("  See All ▸",
                            style = TextStyle(
                                color = Color(0xFF747688),
                                fontSize = 14.sp,
                                fontFamily = Montserrat,
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.padding(start = 8.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Scrollable Circle Buttons
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = 12.dp), // Vertical padding only
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CategoryCircle(Color(0xFFE74C3C), "GDSC")
                    CategoryCircle(Color(0xFFF39C12), "VERVE")
                    CategoryCircle(Color(0xFF27AE60), "PFC")
                    CategoryCircle(Color(0xFF3498DB), "EDC")
                    CategoryCircle(Color(0xFF9B59B6), "CSI")  // Add more items if needed
                    CategoryCircle(Color(0xFF1ABC9C), "DSC")  // Extra scrolling content
                    Spacer(modifier = Modifier.width(16.dp)) // Optional: trailing space
                }
            }
        }
    }
}

// Helper composable for category circles
@Composable
fun CategoryCircle(color: Color, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = label,
            style = TextStyle(
                color = Color(0xFF120D26),
                fontSize = 12.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}