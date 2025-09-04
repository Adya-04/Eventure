package com.example.eventuree.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.eventuree.R
import com.example.eventuree.ui.components.NextButton
import com.example.eventuree.ui.theme.Montserrat

data class EventDetails(
    val imageRes: Int,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val address: String,
    val organizer: String,
    val attendeesCount: Int,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun EventDetailsScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Header Image
        Image(
            painter = painterResource(id = R.drawable.event_img),
            contentDescription = "Event Banner",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(280.dp)
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back_arrow), // Your back arrow icon
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 8.dp)
            )

            Text(
                text = " Event Details",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium
                )
            )
        }
        Icon(
            painter = painterResource(R.drawable.isbookmarked),
            contentDescription = null,
            tint = null,
            modifier = Modifier
                .padding(top = 16.dp, end = 18.dp)
                .size(34.dp)
                .align(Alignment.TopEnd)
        )
        // GoingInviteCard overlapping at bottom of image
        GoingInviteCard(
            modifier = Modifier
                .offset(y = 180.dp) // Half overlaps 280.dp header image
                .align(Alignment.TopCenter)
                .zIndex(1f) // Ensure it appears above the image
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 210.dp)
                .background(Color.White)
        ) {
            // Event Title
            Text(
                "International Band Music Concert",
                modifier = Modifier
                    .padding(top = 36.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 30.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold
                )
            )

            // Date & Time
            InfoRow(
                iconPainter = painterResource(id = R.drawable.dateandtime_event_info), // your SVG drawable
                title = "14 December, 2021",
                subtitle = "Tuesday, 4:00PM - 9:00PM"
            )


            // Location
            InfoRow(
                iconPainter = painterResource(id = R.drawable.location_event_info),
                title = "Gala Convention Center",
                subtitle = "36 Guild Street London, UK"
            )

            // Organizer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dummy),
                    contentDescription = "Organizer",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )

                Column(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f)
                ) {
                    Text("Ashfaq Sayem", fontWeight = FontWeight.SemiBold)
                    Text("Organizer", color = Color.Gray, fontSize = 12.sp)
                }

                Button(
                    modifier = Modifier.height(28.dp),
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8EAFE)),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Follow",
                        style = TextStyle(
                            color = Color(0xFF5F6FFF),
                            fontSize = 12.sp,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }

            // About Event
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text("About Event",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium
                    ))
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Enjoy your favorite dishes and a lovely time with your friends and family. Food from local food trucks will be available for purchase.",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(modifier = Modifier.padding(18.dp)){
                NextButton(
                    text = "RSVP NOW",
                    enabled = true,
                    onClick = {

                    }
                )
            }

        }
    }
}

@Composable
fun GoingInviteCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        shape = RoundedCornerShape(32.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFF7F7F7),
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                OverlappingImages()

                // This will partially overlap the images
                Text(
                    text = "+20 Going",
                    color = Color(0xFF5B3EFF),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .offset(x = 100.dp) // Adjust x to move over the last image
                )
            }

            Spacer(modifier = Modifier.weight(1f)) // Push Invite button to right

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5669FF)),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
                modifier = Modifier.height(32.dp)
            ) {
                Text(
                    text = "Invite",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}


@Composable
fun OverlappingImages() {
    val imageSize = 40.dp

    Box {
        // Third image - bottom layer, rightmost
        Image(
            painter = painterResource(id = R.drawable.dummy),
            contentDescription = "Profile 3",
            modifier = Modifier
                .size(imageSize)
                .offset(x = 50.dp)
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape)
        )

        // Second image - middle layer
        Image(
            painter = painterResource(id = R.drawable.dummy),
            contentDescription = "Profile 2",
            modifier = Modifier
                .size(imageSize)
                .offset(x = 25.dp)
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape)
        )

        // First image - top layer (fully visible)
        Image(
            painter = painterResource(id = R.drawable.dummy),
            contentDescription = "Profile 1",
            modifier = Modifier
                .size(imageSize)
                .offset(x = 0.dp)
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape)
        )
    }
}


@Composable
fun InfoRow(iconPainter: Painter, title: String, subtitle: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = iconPainter,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(modifier = Modifier.padding(start = 12.dp)) {
            Text(
                title, style = TextStyle(
                    color = Color(0xFF120D26),
                    fontSize = 16.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(Modifier.height(4.dp))
            Text(
                subtitle, style = TextStyle(
                    color = Color(0xFF747688),
                    fontSize = 12.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}
