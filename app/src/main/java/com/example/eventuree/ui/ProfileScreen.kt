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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.eventuree.R
import com.example.eventuree.ui.theme.BlueMainColor
import com.example.eventuree.ui.theme.Montserrat
import com.example.eventuree.ui.theme.TopCardShape
import com.example.eventuree.viewmodels.PrefsViewModel
import com.example.eventuree.viewmodels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    prefsViewModel: PrefsViewModel
) {
    val userId = prefsViewModel.userId.collectAsState(initial = "")
    val uiState = profileViewModel.uiState.collectAsState()

    LaunchedEffect(userId.value) {
        if (userId.value.isNotBlank()) {
            profileViewModel.loadProfile(userId.value)
        }
    }

    if (uiState.value.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = BlueMainColor)
        }
        return
    }

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
            // Profile Image with fallback
            if (!uiState.value.profileImageUrl.isNullOrBlank()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(uiState.value.profileImageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Profile Image",
                    placeholder = painterResource(id = R.drawable.dummy),
                    error = painterResource(id = R.drawable.dummy),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.dummy),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
            }


            // Name
            Text(
                text = uiState.value.userName,
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
                    text = uiState.value.aboutMe.ifBlank { "No information provided." },
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

                if (uiState.value.following.isNotEmpty()) {
                    // Scrollable Society Circles
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        uiState.value.following.forEach { societyName ->
                            SocietyCircle(societyName = societyName)
                        }
                    }
                } else {
                    Text(
                        text = "Not following any societies yet.",
                        style = TextStyle(
                            color = Color(0xFF747688),
                            fontSize = 14.sp,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }
        }

        // Error message overlay
        uiState.value.errorMessage?.let { errorMessage ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = errorMessage,
                    style = TextStyle(
                        color = Color.Red,
                        fontSize = 14.sp,
                        fontFamily = Montserrat
                    ),
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(8.dp))
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun SocietyCircle(societyName: String) {
    // Generate a consistent color based on society name hash
    val color = generateColorFromName(societyName)
    val displayName = if (societyName.length > 4) {
        societyName.take(4).uppercase()
    } else {
        societyName.uppercase()
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = displayName,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Text(
            text = societyName,
            style = TextStyle(
                color = Color(0xFF120D26),
                fontSize = 12.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .padding(top = 4.dp)
                .width(80.dp),
            maxLines = 1
        )
    }
}

// Helper function to generate consistent colors from society names
private fun generateColorFromName(name: String): Color {
    val hash = name.hashCode()
    return Color(
        red = (hash and 0xFF) / 255f,
        green = ((hash shr 8) and 0xFF) / 255f,
        blue = ((hash shr 16) and 0xFF) / 255f
    ).copy(alpha = 0.8f)
}