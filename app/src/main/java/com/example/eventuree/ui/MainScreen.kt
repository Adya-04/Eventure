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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.eventuree.ui.theme.BlueMainColor
import kotlinx.coroutines.launch
import com.example.eventuree.R
import com.example.eventuree.data.models.BottomNavItem
import com.example.eventuree.navigation.NavRoutes
import com.example.eventuree.ui.components.MenuItem
import com.example.eventuree.ui.theme.Montserrat
import com.example.eventuree.viewmodels.EventDetailsViewModel
import com.example.eventuree.viewmodels.ExploreViewModel
import com.example.eventuree.viewmodels.HomeViewModel
import com.example.eventuree.viewmodels.PrefsViewModel
import com.example.eventuree.viewmodels.ProfileViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(rootNavController: NavHostController) {

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }
    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem(
            title = "For You",
            selectedIcon = R.drawable.home_selected_icon,
            unselectedIcon = R.drawable.home_unselected_icon,
            route = NavRoutes.ForYou.route
        ),
        BottomNavItem(
            title = "Explore",
            selectedIcon = R.drawable.explore_sel_icon,
            unselectedIcon = R.drawable.explore_unsel_icon,
            route = NavRoutes.Explore.route
        ),
        BottomNavItem(
            title = "My Events",
            selectedIcon = R.drawable.event_sel_icon,
            unselectedIcon = R.drawable.event_unsel_icon,
            route = NavRoutes.MyEvents.route
        ),
        BottomNavItem(
            title = "Profile",
            selectedIcon = R.drawable.profile_sel_icon,
            unselectedIcon = R.drawable.profile_unsel_icon,
            route = NavRoutes.Profile.route
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

                    items.forEachIndexed { index, item ->

                        NavigationBarItem(
                            selected = selectedNavigationIndex.intValue == index,
                            onClick = {
                                selectedNavigationIndex.intValue = index
                                navController.navigate(item.route)
                            },
                            icon = {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    val iconRes =
                                        if (selectedNavigationIndex.intValue == index)
                                            item.selectedIcon
                                        else item.unselectedIcon
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
                                        color = if (selectedNavigationIndex.intValue == index) Color.Black else Color.Gray
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
        ) { paddingValues ->
            val graph =
                navController.createGraph(startDestination = NavRoutes.ForYou.route) {
                    composable(route = NavRoutes.ForYou.route) {
                        val homeViewModel: HomeViewModel = hiltViewModel()
                        HomeScreen(homeViewModel)
                    }

                    composable(route = NavRoutes.Profile.route) {
                        val profileViewModel: ProfileViewModel = hiltViewModel()
                        val prefsViewModel: PrefsViewModel = hiltViewModel()
                        ProfileScreen(
                            profileViewModel = profileViewModel,
                            prefsViewModel = prefsViewModel
                        )
                    }

                    composable(route = NavRoutes.Explore.route) {
                        val exploreViewModel: ExploreViewModel = hiltViewModel()
                        ExploreScreen(
                            exploreViewModel = exploreViewModel,
                            navController = rootNavController
                        )
                    }

                    composable(route = NavRoutes.MyEvents.route) {
                        MyEventsScreen()
                    }
                }

            NavHost(
                navController = navController,
                graph = graph,
                modifier = Modifier.padding(paddingValues)
            )
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


