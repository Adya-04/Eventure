package com.example.eventuree.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventuree.ui.theme.BlueMainColor
import com.example.eventuree.ui.theme.TopCardShape
import com.example.eventuree.R
import com.example.eventuree.ui.theme.Montserrat

@Preview(showBackground = true)
@Composable
fun ExploreScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            colors = CardDefaults.cardColors(containerColor = BlueMainColor),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = TopCardShape.large
        ) {
            CategoryChips()
        }
        Spacer(Modifier.height(18.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EventListScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChips() {
    val categories = listOf(
        Triple(R.drawable.hot_icon, "Trending", Color(0xFFD11D11)),
        Triple(R.drawable.technical_icon, "Technical", Color(0xFFCA8C20)), // orange shade
        Triple(R.drawable.music_icon, "Cultural", Color(0xFF0AB22C))
    )

    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        categories.forEach { (icon, label, color) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .background(color, shape = RoundedCornerShape(50))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = label,
                    color = Color.White,
                    fontSize = 16.sp,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}

@Composable
fun EventListScreen() {
    val events = listOf(
        Event(
            "Jo Malone London’s Mother’s Day Presents",
            "Wed, Apr 28 • 5:30 PM",
            "Radius Gallery - Santa Cruz, CA",
            R.drawable.event_img
        ),
        Event(
            "A Virtual Evening of Smooth Jazz",
            "Sat, May 1 • 2:00 PM",
            "Lot 13 • Oakland, CA",
            R.drawable.event_img
        ),
        Event(
            "Women’s Leadership Conference 2021",
            "Sat, Apr 24 • 1:30 PM",
            "53 Bush St • San Francisco, CA",
            R.drawable.event_img
        ),
        Event(
            "International Kids Safe Parents Night Out",
            "Fri, Apr 23 • 6:00 PM",
            "Lot 13 • Oakland, CA",
            R.drawable.event_img
        ),
        Event(
            "Collectivity Plays the Music of Jimi",
            "Mon, Jun 21 • 10:00 PM",
            "Lot 13 • Oakland, CA",
            R.drawable.event_img
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(events) { event ->
            EventItem(event)
        }
    }
}

data class Event(
    val title: String,
    val dateTime: String,
    val location: String,
    val imageRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventItem(event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = event.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(70.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = event.dateTime,
                    style = TextStyle(
                        color = Color(0xFF5669FF),
                        fontSize = 12.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = event.title,
                    style = TextStyle(
                        color = Color(0xFF120D26),
                        fontSize = 15.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.location_icon),
                        contentDescription = null,
                        tint = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = event.location,
                        style = TextStyle(
                            color = Color(0xFF747688),
                            fontSize = 12.sp,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }

            }
        }
    }
}

