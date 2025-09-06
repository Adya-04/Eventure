package com.example.eventuree.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.eventuree.data.models.EventItem
import com.example.eventuree.ui.theme.BlueMainColor
import com.example.eventuree.ui.theme.Montserrat
import com.example.eventuree.ui.theme.TopCardShape
import com.example.eventuree.R
import com.example.eventuree.ui.components.EventCalendar
import com.example.eventuree.ui.components.EventCardforUpcoming
import com.example.eventuree.ui.components.EventDetails

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MyEventsScreen() {
    var selectedTab by remember { mutableStateOf("Upcoming") }

    Column(Modifier.fillMaxSize()) {
        // Header Card
        Card(
            colors = CardDefaults.cardColors(BlueMainColor),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = TopCardShape.large
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                EventTabs(selectedTab) { selectedTab = it }
            }
        }

        // Content Area
        when (selectedTab) {
            "Upcoming" -> UpcomingEventsScreen()
            else -> PastEventsScreen()
        }
    }
}

@Composable
fun EventTabs(selected: String, onSelect: (String) -> Unit) {
    Row(
        Modifier
            .padding(16.dp)
            .background(Color(0xFF403ACD), RoundedCornerShape(50))
            .padding(4.dp)
    ) {
        listOf("Upcoming", "Past Events").forEach { tab ->
            Box(
                Modifier
                    .clip(RoundedCornerShape(50))
                    .background(if (tab == selected) Color.White else Color.Transparent)
                    .clickable { onSelect(tab) }
                    .padding(horizontal = 36.dp, vertical = 12.dp)
            ) {
                Text(
                    text = tab.uppercase(),
                    color = if (tab == selected) Color(0xFF5669FF) else Color.White,
                    fontFamily = Montserrat,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UpcomingEventsScreen() {
    val eventList = listOf(
        EventDetails(
            date = "6 Dec 2023",
            title = "GDG Hackathon",
            logoResId = R.drawable.event_img,
            time = "11:30 am – 5:00 pm",
            location = "AB-1 Hall No. 3"
        ),
        EventDetails(
            date = "6 Dec 2023",
            title = "GDG Hackathon",
            logoResId = R.drawable.event_img,
            time = "11:30 am – 5:00 pm",
            location = "AB-1 Hall No. 3"
        ),
        EventDetails(
            date = "6 Dec 2023",
            title = "GDG Hackathon",
            logoResId = R.drawable.event_img,
            time = "11:30 am – 5:00 pm",
            location = "AB-1 Hall No. 3"
        )
    )

    LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 10.dp)) {
        item {
            Spacer(Modifier.height(12.dp))
            EventCalendar()
            Spacer(Modifier.height(16.dp))
        }
        items(eventList) { item ->
            EventCardforUpcoming(item)
        }
    }
}

@Composable
fun PastEventsScreen() {
    val pastEvents = listOf(
        EventItem("6 Nov 2023", "DSC", R.drawable.event_img, 5),
        EventItem("10 Nov 2023", "DevFest", R.drawable.event_img, 3)
    )

    LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 10.dp)) {
        items(pastEvents) { item ->
            EventCard(item)
        }
    }
}

@Composable
fun EventCard(item: EventItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo
            Image(
                painter = painterResource(id = item.logoResId),
                contentDescription = "Event Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(70.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.date,
                    style = TextStyle(
                        color = Color(0xFF5669FF),
                        fontSize = 12.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = item.title,
                    style = TextStyle(
                        color = Color(0xFF120D26),
                        fontSize = 15.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(5) { i ->
                        val starColor = if (i < item.rating) Color(0xFFFFD700) else Color.LightGray
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Star",
                            tint = starColor,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Button(
                onClick = { /* Handle rate now button click */ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A43EC)),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .height(36.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp) // Reduce internal padding
            ) {
                Text(
                    text = "RATE NOW",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 12.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium
                    ))
            }
        }
    }
}

