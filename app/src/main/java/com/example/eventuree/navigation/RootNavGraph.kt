package com.example.eventuree.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.eventuree.data.models.Events
import com.example.eventuree.ui.EventDetailsScreen
import com.example.eventuree.ui.MainScreen
import com.example.eventuree.viewmodels.AuthViewModel
import com.example.eventuree.viewmodels.EventDetailsViewModel
import com.example.eventuree.viewmodels.PrefsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(navController: NavHostController, startDestination : String) {

    val authViewModel : AuthViewModel = hiltViewModel()
    val prefsViewModel: PrefsViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(route = NavRoutes.Main.route) {
            MainScreen(rootNavController = navController)
        }

        composable(
            route = NavRoutes.EventDetails.route
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: return@composable
            val eventDetailsViewModel: EventDetailsViewModel = hiltViewModel()
            EventDetailsScreen(
                eventId = eventId,
                eventDetailsViewModel = eventDetailsViewModel
            )
        }

        onboardingNavGraph(navController,authViewModel, prefsViewModel)
    }
}

sealed class NavRoutes(val route: String) {
    data object Onboarding : NavRoutes("onboarding_graph")
    data object Main : NavRoutes("main_graph")
    data object EventDetails : NavRoutes("event_details/{eventId}") {
        fun createRoute(eventId: String) = "event_details/$eventId"
    }
    data object MyEvents : NavRoutes("my_events")
    data object Explore : NavRoutes("explore")
    data object Profile : NavRoutes("profile")
    data object ForYou : NavRoutes("for_you")

}