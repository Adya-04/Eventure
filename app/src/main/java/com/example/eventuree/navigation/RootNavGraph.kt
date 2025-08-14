package com.example.eventuree.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.eventuree.ui.HomeScreen
import com.example.eventuree.viewmodels.AuthViewModel

@Composable
fun RootNavGraph(navController: NavHostController, startDestination : String) {

    val authViewModel : AuthViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(route = NavRoutes.Main.route) {
            HomeScreen()
        }
        onboardingNavGraph(navController,authViewModel)
    }
}

sealed class NavRoutes(val route: String) {
    data object Onboarding : NavRoutes("onboarding_graph")
    data object Main : NavRoutes("main_graph")
//    data object SplashScreen : NavRoutes("splash_screen")
}