package com.example.eventuree.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.eventuree.ui.HomeScreen
import com.example.eventuree.viewmodels.AuthViewModel
import com.example.eventuree.viewmodels.MainViewModel
import com.example.eventuree.viewmodels.PrefsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(navController: NavHostController, startDestination : String) {

    val authViewModel : AuthViewModel = hiltViewModel()
    val prefsViewModel: PrefsViewModel = hiltViewModel()
    val mainViewModel: MainViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(route = NavRoutes.Main.route) {
            HomeScreen(
                mainViewModel
            )
        }
        onboardingNavGraph(navController,authViewModel, prefsViewModel)
    }
}

sealed class NavRoutes(val route: String) {
    data object Onboarding : NavRoutes("onboarding_graph")
    data object Main : NavRoutes("main_graph")
//    data object SplashScreen : NavRoutes("splash_screen")
}