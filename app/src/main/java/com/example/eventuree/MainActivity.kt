package com.example.eventuree

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.eventuree.navigation.NavRoutes
import com.example.eventuree.navigation.RootNavGraph
import com.example.eventuree.ui.theme.EventureTheme
import com.example.eventuree.viewmodels.PrefsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle splash screen
        val splashScreen = installSplashScreen().apply {
            setKeepOnScreenCondition { true }
        }

        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setContent {
            EventureTheme {
                val navController = rememberNavController()
                val prefsViewModel: PrefsViewModel = hiltViewModel()

                val token by prefsViewModel.token.collectAsState(initial = "")

                    // Determine initial destination
                    val startDestination = if (token.isNotEmpty()) {
                        NavRoutes.Main.route
                    } else {
                        NavRoutes.Onboarding.route
                    }

                LaunchedEffect(Unit) {
                    splashScreen.setKeepOnScreenCondition { false }
                }

                RootNavGraph(navController,startDestination)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EventureTheme {
        Greeting("Android")
    }
}