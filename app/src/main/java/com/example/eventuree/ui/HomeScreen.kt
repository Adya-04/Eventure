package com.example.eventuree.ui

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.eventuree.ui.theme.BlueMainColor
import com.example.eventuree.ui.theme.TopCardShape
import kotlinx.coroutines.launch
import com.example.eventuree.R
import com.example.eventuree.data.models.BottomNavItem
import com.example.eventuree.data.models.FollowSocietyResponse
import com.example.eventuree.ui.components.MenuItem
import com.example.eventuree.ui.theme.Montserrat
import com.example.eventuree.utils.NetworkResult
import com.example.eventuree.utils.getDay
import com.example.eventuree.utils.getMonth
import com.example.eventuree.viewmodels.MainViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val items = listOf(
        BottomNavItem(
            title = "For You",
            selectedIcon = R.drawable.home_selected_icon,
            unselectedIcon = R.drawable.home_unselected_icon,
            screen = { ScreenContent() },
            topBar = {
                TopBar(
                    title = "", onOpenDrawer = {
                        scope.launch { drawerState.open() }
                    },
                    actionIconResId = R.drawable.notification_icon
                )
            }
        ),
        BottomNavItem(
            title = "Explore",
            selectedIcon = R.drawable.explore_sel_icon,
            unselectedIcon = R.drawable.explore_unsel_icon,
            screen = { ExploreScreen() },
            topBar = {
                TopBar(
                    title = " Explore", onOpenDrawer = {
                        scope.launch { drawerState.open() }
                    },
                    actionIconResId = R.drawable.search
                )
            }
        ),
        BottomNavItem(
            title = "My Events",
            selectedIcon = R.drawable.event_sel_icon,
            unselectedIcon = R.drawable.event_unsel_icon,
            screen = { MyEventsScreen() },
            topBar = {
                TopBar(title = " My Events", onOpenDrawer = {
                    scope.launch { drawerState.open() }
                })
            }
        ),
        BottomNavItem(
            title = "Profile",
            selectedIcon = R.drawable.profile_sel_icon,
            unselectedIcon = R.drawable.profile_unsel_icon,
            screen = { ProfileScreen() },
            topBar = {
                TopBar(title = " Profile", onOpenDrawer = {
                    scope.launch { drawerState.open() }
                })
            }
        )
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = RoundedCornerShape(
                    topEnd = 16.dp,
                    bottomEnd = 16.dp
                ),
                drawerContainerColor = BlueMainColor
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(250.dp)
                        .background(BlueMainColor)
                ) {
                    DrawerContent()
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                items[selectedItemIndex].topBar()
            },
            bottomBar = {
                NavigationBar(containerColor = Color.White) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                            },
                            icon = {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    val iconRes =
                                        if (selectedItemIndex == index) item.selectedIcon else item.unselectedIcon
                                    Icon(
                                        painter = painterResource(id = iconRes),
                                        contentDescription = item.title,
                                        tint = null
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = item.title,
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            fontFamily = Montserrat,
                                            fontWeight = FontWeight.Normal
                                        ),
                                        color = if (selectedItemIndex == index) Color.Black else Color.Gray
                                    )
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent,
                                selectedIconColor = Color.Unspecified,
                                unselectedIconColor = Color.Unspecified
                            ),
                            modifier = Modifier
                                .height(80.dp)
                                // Add this to control the shape of the selection indicator
                                .padding(8.dp)
                                .background(
                                    if (selectedItemIndex == index) Color.LightGray.copy(alpha = 0.4f) else Color.Transparent,
                                    shape = RoundedCornerShape(6.dp) // Adjust this value to make corners less rounded
                                )
                        )
                    }
                }
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items[selectedItemIndex].screen()
            }
        }
    }
}

@Composable
fun DrawerContent(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)
    ) {
        Spacer(Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.profile_icon),
                contentDescription = "Profile",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(20.dp))
        Text(
            text = "Adya Singh",
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Montserrat,
                color = Color.White
            )
        )
        Spacer(Modifier.height(40.dp))

        MenuItem("My Profile", R.drawable.my_profile_icon)
        Spacer(Modifier.height(25.dp))
        MenuItem("Calendar", R.drawable.calendar_icon)
        Spacer(Modifier.height(25.dp))
        MenuItem("Bookmark", R.drawable.bookmark_icon)
        Spacer(Modifier.height(25.dp))
        MenuItem("Sign Out", R.drawable.signout_icon)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String = "", onOpenDrawer: () -> Unit,
    actionIconResId: Int? = null
) {
    Box(
        modifier = Modifier
            .background(BlueMainColor)
    ) {
        TopAppBar(
            navigationIcon = {
                Icon(
                    painter = painterResource(R.drawable.menu_icon),
                    contentDescription = "Menu",
                    modifier = Modifier
                        .size(35.dp)
                        .padding(start = 12.dp)
                        .clickable {
                            onOpenDrawer()
                        },
                    tint = Color.White
                )
            },
            title = {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 26.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 10.dp)
                )
            },
            actions = {
                actionIconResId?.let {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            painter = painterResource(it),
                            contentDescription = "Action Icon",
                            tint = null
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent() {
//    val eventsState by mainViewModel.getPersEvents.collectAsState()
//    val societiesState by mainViewModel.getSocieties.collectAsState()
//    val followState by mainViewModel.followSociety.collectAsState()
    val mainViewModel: MainViewModel = hiltViewModel()
    val uiState by mainViewModel.uiState.collectAsState()
    val userMessage by mainViewModel.userMessage.collectAsState()
    val context = LocalContext.current

    // Handle user messages (snackbar/toast)
    LaunchedEffect(userMessage) {
        userMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            mainViewModel.userMessageShown()
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
        when (uiState.personalizedEvents) {
            is NetworkResult.Loading -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is NetworkResult.Success -> {
                val data = (uiState.personalizedEvents as NetworkResult.Success).data
                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
                    data?.events?.let { eventsList ->
                        items(eventsList.size) { index ->
                            val event = eventsList[index]
                            EventCard(
                                title = event.name,
                                location = event.venue,
                                date = getDay(event.startTime),
                                month = getMonth(event.startTime), // you can parse event.startTime properly into month
                                goingCount = event.goingCount
                            )
                        }
                    }
                }
            }

            is NetworkResult.Error -> {
                Text(
                    "Error: ${uiState.userMessage}",
                    modifier = Modifier.padding(20.dp)
                )
            }

            else -> {}
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
            when (uiState.societies) {
                is NetworkResult.Loading -> {
                    Text("Loading societies...", modifier = Modifier.padding(16.dp))
                }

                is NetworkResult.Success -> {
                    val societies = (uiState.societies as NetworkResult.Success).data?.societies
                    if (!societies.isNullOrEmpty()) {
                        LazyRow(
                            modifier = Modifier.padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(societies.size) { index ->
                                val society = societies[index]
                                val logoUrl = society.logo ?: "" // default empty

                                CategoryCirclewithFollow(
                                        logoUrl = logoUrl,
                                        name = society.name,
                                        societyId = society.id,
                                        onFollowClick = {mainViewModel.followSociety(society.id)}
                                    )

                            }
                        }
                    }
                }

                is NetworkResult.Error -> {
                    Text("Error: ${uiState.userMessage}")
                }

                else -> {}
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
                model =  if (logoUrl.isNotEmpty()) logoUrl else R.drawable.event_img,
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


