package com.example.eventuree.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.eventuree.ui.theme.BlueMainColor
import com.example.eventuree.ui.theme.TopCardShape
import com.example.eventuree.R
import com.example.eventuree.data.models.EventItem
import com.example.eventuree.data.models.Events
import com.example.eventuree.ui.theme.Montserrat
import com.example.eventuree.utils.formatEventDate
import com.example.eventuree.viewmodels.ExploreViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExploreScreen(exploreViewModel: ExploreViewModel) {

    val uiState by exploreViewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            colors = CardDefaults.cardColors(containerColor = BlueMainColor),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = TopCardShape.large
        ) {
            CategoryChips { category ->
                when (category) {
                    "All" -> exploreViewModel.fetchAllEvents(1, 10)
                    "Trending" -> exploreViewModel.fetchTrendingEvents()
                    "Technical" -> exploreViewModel.fetchFilteredEvents("technical", 1, 10)
                    "Cultural" -> exploreViewModel.fetchFilteredEvents("cultural", 1, 10)
                }
            }
        }
        Spacer(Modifier.height(18.dp))

        when {
            uiState.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.errorMessage != null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Error: ${uiState.errorMessage}",
                        color = Color.Red,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            else -> {
                val eventsList = when (uiState.selectedCategory) {
                    "All" -> uiState.allEvents?.events
                    "Trending" -> uiState.trendingEvents?.events
                    "Technical" -> uiState.technicalEvents?.events
                    "Cultural" -> uiState.culturalEvents?.events
                    else -> emptyList()
                }
                if (eventsList.isNullOrEmpty()) {
                    Text(
                        text = "You've no events",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                } else {
                    LazyColumn {
                        items(eventsList.size) { index ->
                            EventItem(event = eventsList[index])
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChips(onCategorySelected: (String) -> Unit) {
    val categories = listOf(
        Triple(R.drawable.all_event_icon, "All", Color(0xFF6B4F3A)),
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
                    .clickable { onCategorySelected(label) }
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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventItem(event: Events) {
    val formattedDate = formatEventDate(event.startTime)

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
            val imageModel = if (event.eventPic.isNullOrEmpty()) {
                R.drawable.event_img // <-- your default drawable
            } else {
                event.eventPic
            }

            AsyncImage(
                model = imageModel,
                contentDescription = null,
                modifier = Modifier
                    .width(70.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = formattedDate,
                    style = TextStyle(
                        color = Color(0xFF5669FF),
                        fontSize = 12.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium
                    )
                )
                Text(
                    text = event.name,
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
                        text = event.venue,
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

