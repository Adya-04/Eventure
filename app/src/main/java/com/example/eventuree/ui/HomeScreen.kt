package com.example.eventuree.ui

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.eventuree.R
import com.example.eventuree.ui.theme.BlueMainColor
import com.example.eventuree.ui.theme.Montserrat
import com.example.eventuree.ui.theme.TopCardShape
import com.example.eventuree.utils.getDay
import com.example.eventuree.utils.getMonth
import com.example.eventuree.viewmodels.HomeViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val uiState by homeViewModel.uiState.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(uiState.userMessage) {
        uiState.userMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            homeViewModel.userMessageShown()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = BlueMainColor),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = TopCardShape.large
        ) {
            Spacer(Modifier.height(18.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                SearchBar()
            }
        }
        Spacer(Modifier.height(18.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Personalized Events",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Montserrat
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "See all",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = Montserrat,
                    color = Color.Gray
                ),
                modifier = Modifier.padding(end = 3.dp)
            )
            Icon(
                painter = painterResource(R.drawable.all_icon),
                contentDescription = "Icon",
                tint = null
            )
        }
        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            uiState.errorMessage != null -> {
                Text(
                    "Error: ${uiState.errorMessage}",
                    modifier = Modifier.padding(20.dp)
                )
            }

            else -> {
                // Personalized Events
                uiState.personalizedEvents?.let { data ->
                    val eventsList = data.events
                    if (eventsList.isNullOrEmpty()) {
                        Text(
                            text = "You've no personalized events",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    } else {
                        LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
                            items(eventsList.size) { index ->
                                val event = eventsList[index]
                                EventCard(
                                    title = event.name,
                                    location = event.venue,
                                    date = getDay(event.startTime),
                                    month = getMonth(event.startTime),
                                    goingCount = event.goingCount
                                )
                            }
                        }
                    }
                }
            }
        }

        //Rate Event Card
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD6FEFF)),
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .height(140.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        vertical = 12.dp,
                        horizontal = 20.dp
                    ) // Inner padding now applied to Column
                    .fillMaxSize()
            ) {
                Text(
                    text = "Rate this event",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = Montserrat
                    )
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = "Get badges for your profile",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = Montserrat
                    )
                )
                Spacer(Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFF00F8FF),
                            shape = RoundedCornerShape(2.dp)
                        )
                        .padding(vertical = 12.dp, horizontal = 20.dp)
                ) {
                    Text(
                        text = "RATE",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = Montserrat
                        )
                    )
                }

            }
        }

        Column(Modifier.padding(16.dp)) {
            Text(
                text = "Follow for Updates",
                style = TextStyle(
                    color = Color(0xFF120D26),
                    fontSize = 18.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium
                )
            )
            // Scrollable Circle Buttons

            uiState.societies?.let { societiesData ->
                if (!societiesData.societies.isNullOrEmpty()) {
                    LazyRow(
                        modifier = Modifier.padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(societiesData.societies.size) { index ->
                            val society = societiesData.societies[index]
                            val logoUrl = society.logo ?: ""

                            CategoryCirclewithFollow(
                                logoUrl = logoUrl,
                                name = society.name,
                                societyId = society.id,
                                onFollowClick = { homeViewModel.followSociety(society.id) }
                            )
                        }
                    }
                } else {
                    Text("No societies available")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "Search...",
    onValueChange: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 16.dp)
            .background(color = Color.Transparent, shape = RoundedCornerShape(25.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.search),
                contentDescription = "Search",
                tint = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                painter = painterResource(R.drawable.search_line),
                contentDescription = "Search",
                tint = null
            )
            Spacer(modifier = Modifier.width(16.dp))

            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                    onValueChange(it)
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (text.isEmpty()) {
                        Text(
                            text = hint,
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 20.sp,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    innerTextField()
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCard(
    title: String,
    location: String,
    date: String,
    month: String,
    goingCount: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(250.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.background(Color.White)) {
            Box {
                Image(
                    painter = painterResource(R.drawable.event_img), // replace with actual image
                    contentDescription = "Event Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = "$date\n$month",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .align(Alignment.TopStart)
                )

                Icon(
                    painter = painterResource(R.drawable.bookmark_icon),
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = title,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                // Avatars (fake for now)
                repeat(3) {
                    Image(
                        painter = painterResource(R.drawable.event_img), // Use real avatar image
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.White, CircleShape)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "+$goingCount Going",
                    color = Color(0xFF3F38DD),
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.location_icon),
                    contentDescription = "Location Icon",
                    tint = null,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = location,
                    color = Color(0xFF9593A4),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun CategoryCirclewithFollow(
    logoUrl: String,
    name: String,
    societyId: String,
    onFollowClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        // Circle with Logo
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.LightGray), // fallback background
            contentAlignment = Alignment.Center
        ) {
            // Load logo from URL
            AsyncImage(
                model = if (logoUrl.isNotEmpty()) logoUrl else R.drawable.event_img,
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
        }

        Text(
            text = name,
            style = TextStyle(
                color = Color(0xFF120D26),
                fontSize = 12.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(top = 4.dp)
        )

        Button(
            onClick = onFollowClick,
            modifier = Modifier
                .padding(top = 6.dp)
                .height(28.dp),
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
}
