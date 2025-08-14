package com.example.eventuree.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.eventuree.R
import com.example.eventuree.ui.theme.Montserrat

@Preview(showBackground = true)
@Composable
fun EventCardPreview() {
    val sampleEvent = EventDetails(
        date = "6 Dec 2023",
        title = "GDG Hackathon",
        logoResId = R.drawable.event_img,
        time = "11:30 am – 5:00 pm",
        location = "AB-1 Hall No. 3"
    )

    EventCardforUpcoming(item = sampleEvent)
}


data class EventDetails(
    val date: String,         // e.g., "6 Dec 2023"
    val title: String,        // e.g., "GDG Hackathon"
    val logoResId: Int,       // Drawable resource ID
    val time: String,         // e.g., "11:30 am – 5:00 pm"
    val location: String      // e.g., "AB-1 Hall No. 3"
)


@Composable
fun EventCardforUpcoming(item: EventDetails) {

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
                painter = painterResource(R.drawable.event_img),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(90.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))
            Column {
                // Date (hardcoded weekday for now, could be parsed if needed)
                Text(
                    text = "Wednesday, ${item.date}", // Ensure item.date includes "6 Dec 2023"
                    style = TextStyle(
                        color = Color(0xFF595959),
                        fontSize = 12.sp,
                        fontFamily =
                            Montserrat,
                        fontWeight = FontWeight.Medium
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = item.title,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Time Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.clock), // Replace with your clock icon
                        contentDescription = "Time",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp).padding(2.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = item.time,
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 12.sp,
                            fontFamily = Montserrat
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Location Row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.location), // Replace with your location icon
                        contentDescription = "Location",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp).padding(1.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = item.location,
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 12.sp,
                            fontFamily = Montserrat
                        )
                    )
                }
            }
        }
    }
}
