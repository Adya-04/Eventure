package com.example.eventuree.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.eventuree.ui.theme.BlueMainColor
import kotlinx.coroutines.launch
import com.example.eventuree.R
import com.example.eventuree.data.models.BottomNavItem
import com.example.eventuree.ui.components.MenuItem
import com.example.eventuree.ui.theme.Montserrat
import com.example.eventuree.viewmodels.ExploreViewModel
import com.example.eventuree.viewmodels.HomeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val navController = rememberNavController()

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val items = listOf(
        BottomNavItem(
            title = "For You",
            selectedIcon = R.drawable.home_selected_icon,
            unselectedIcon = R.drawable.home_unselected_icon,
            route = "home"
        ),
        BottomNavItem(
            title = "Explore",
            selectedIcon = R.drawable.explore_sel_icon,
            unselectedIcon = R.drawable.explore_unsel_icon,
            route = "explore"
        ),
        BottomNavItem(
            title = "My Events",
            selectedIcon = R.drawable.event_sel_icon,
            unselectedIcon = R.drawable.event_unsel_icon,
            route = "my_events"
        ),
        BottomNavItem(
            title = "Profile",
            selectedIcon = R.drawable.profile_sel_icon,
            unselectedIcon = R.drawable.profile_unsel_icon,
            route = "profile"
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
                val currentDestination =
                    navController.currentBackStackEntryAsState().value?.destination?.route
                when (currentDestination) {
                    "home" -> TopBar(
                        title = "",
                        onOpenDrawer = { scope.launch { drawerState.open() } },
                        actionIconResId = R.drawable.notification_icon
                    )
                    "explore" -> TopBar(
                        title = "Explore",
                        onOpenDrawer = { scope.launch { drawerState.open() } },
                        actionIconResId = R.drawable.search
                    )
                    "my_events" -> TopBar(
                        title = "My Events",
                        onOpenDrawer = { scope.launch { drawerState.open() } }
                    )
                    "profile" -> TopBar(
                        title = "Profile",
                        onOpenDrawer = { scope.launch { drawerState.open() } }
                    )
                }
            },
            bottomBar = {
                NavigationBar(containerColor = Color.White) {
                    val currentDestination =
                        navController.currentBackStackEntryAsState().value?.destination?.route

                    items.forEach { item ->
                        val selected = currentDestination == item.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    val iconRes =
                                        if (selected) item.selectedIcon else item.unselectedIcon
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
                                        color = if (selected) Color.Black else Color.Gray
                                    )
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .height(80.dp)
                                .padding(8.dp)
                        )
                    }
                }
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(padding)
            ) {
                composable("home") {
                    val homeViewModel: HomeViewModel = hiltViewModel()
                    HomeScreen(homeViewModel = homeViewModel)
                }
                composable("explore") {
                    val exploreViewModel: ExploreViewModel = hiltViewModel()
                    ExploreScreen(exploreViewModel, onEventClick = {})
                }
                composable("my_events") {
                    MyEventsScreen()
                }
                composable("profile") {
                    ProfileScreen(
                        prefsViewModel = hiltViewModel(),
                        profileViewModel = hiltViewModel()
                    )
                }
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


