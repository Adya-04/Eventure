package com.example.eventuree.navigation

import android.net.Uri
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.eventuree.ui.onboarding.OTPScreen
import com.example.eventuree.ui.onboarding.SignUpDetailsScreen
import com.example.eventuree.ui.onboarding.SigninScreen
import com.example.eventuree.ui.onboarding.SignupScreen
import com.example.eventuree.ui.onboarding.WelcomeScreen1
import com.example.eventuree.ui.onboarding.WelcomeScreen2
import com.example.eventuree.ui.onboarding.WelcomeScreen3
import com.example.eventuree.viewmodels.AuthViewModel
import com.example.eventuree.viewmodels.PrefsViewModel

fun NavGraphBuilder.onboardingNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    prefsViewModel: PrefsViewModel
) {
    navigation(
        route = NavRoutes.Onboarding.route,
        startDestination = OnboardingRoutes.WELCOME1.route
    ) {
        composable(route = OnboardingRoutes.WELCOME1.route) {
            WelcomeScreen1(
                onNext = {
                    navController.navigate(OnboardingRoutes.WELCOME2.route)
                },
                onSkip = {
                    navController.navigate(OnboardingRoutes.SIGNIN.route)
                }
            )
        }

        composable(route = OnboardingRoutes.WELCOME2.route) {
            WelcomeScreen2(
                onNext = {
                    navController.navigate(OnboardingRoutes.WELCOME3.route)
                },
                onSkip = {
                    navController.navigate(OnboardingRoutes.SIGNIN.route)
                }
            )
        }

        composable(route = OnboardingRoutes.WELCOME3.route) {
            WelcomeScreen3(
                onNext = {
                    navController.navigate(OnboardingRoutes.SIGNIN.route) {
                        popUpTo(OnboardingRoutes.WELCOME1.route) {
                            inclusive = true
                        }
                    }
                },
                onSkip = {
                    navController.navigate(OnboardingRoutes.SIGNIN.route)
                }
            )
        }

        composable(route = OnboardingRoutes.SIGNIN.route) {
            SigninScreen(
                onSignInComplete = {
                    navController.navigate(NavRoutes.Main.route) {
                        popUpTo(NavRoutes.Onboarding.route) {
                            inclusive = true
                        }
                    }
                },
                onSignupClick = {
                    navController.navigate(OnboardingRoutes.SIGNUP.route)
                },
                authViewModel = authViewModel,
                prefsViewModel = prefsViewModel
            )
        }

        composable(route = OnboardingRoutes.SIGNUP.route) {
            SignupScreen(
                onOTPSentComplete = { email ->
                    navController.navigate("${OnboardingRoutes.OTP.route}/$email")
                },
                onGoogleSignInComplete = { token, email, userId, profilePic, name ->
                    // For Google signup, navigate directly to details screen with token
                    navController.navigate(
                        "${OnboardingRoutes.SIGNUP_DETAIL.route}/$email" +
                                "?token=$token" +
                                "&userId=${Uri.encode(userId)}" +
                                "&profilePic=${Uri.encode(profilePic)}" +
                                "&name=$name"
                    ) {
                        popUpTo(OnboardingRoutes.WELCOME1.route) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(NavRoutes.Main.route) {
                        popUpTo(OnboardingRoutes.WELCOME1.route) { inclusive = true }
                    }
                },
                onSignInClick = {
                    navController.navigate(OnboardingRoutes.SIGNIN.route)
                },
                authViewModel = authViewModel,
                prefsViewModel = prefsViewModel
            )
        }

        composable(
            route = "${OnboardingRoutes.OTP.route}/{email}",
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                }
            )) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            OTPScreen(
                email = email,
                onOTPConfirm = {
                    navController.navigate("${OnboardingRoutes.SIGNUP_DETAIL.route}/$email") {
                        popUpTo(OnboardingRoutes.WELCOME1.route) {
                            inclusive = true
                        }
                    }
                },
                authViewModel = authViewModel
            )
        }

        composable(
            route = "${OnboardingRoutes.SIGNUP_DETAIL.route}/{email}?token={token}&userId={userId}&profilePic={profilePic}&name={name}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("token") {
                    type = NavType.StringType
                    nullable = true  // Token is only required for Google signup
                },
                navArgument("userId") {
                    type = NavType.StringType
                    nullable = true  // userId is only required for Google signup
                },
                navArgument("profilePic") {
                    type = NavType.StringType
                    nullable = true  // profilePic is only required for Google signup
                },
                navArgument("name") {
                    type = NavType.StringType
                    nullable = true  // name is only required for Google signup
                }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val token = backStackEntry.arguments?.getString("token")
            val userId = backStackEntry.arguments?.getString("userId")
            val profilePic = backStackEntry.arguments?.getString("profilePic")
            val name = backStackEntry.arguments?.getString("name")

            SignUpDetailsScreen(
                email = email,
                initialToken = token,
                userId = userId,
                profilePicUri = profilePic,
                name = name,
                onDetailsSubmit = {
                    navController.navigate(NavRoutes.Main.route) {
                        popUpTo(NavRoutes.Onboarding.route) {
                            inclusive = true
                        }
                    }
                },
                authViewModel = authViewModel,
                prefsViewModel = prefsViewModel
            )
        }
    }
}

sealed class OnboardingRoutes(val route: String) {
    data object WELCOME1 : OnboardingRoutes("welcome_1")
    data object WELCOME2 : OnboardingRoutes("welcome_2")
    data object WELCOME3 : OnboardingRoutes("welcome_3")
    data object SIGNIN : OnboardingRoutes("sign_in")
    data object SIGNUP : OnboardingRoutes("sign_up")
    data object OTP : OnboardingRoutes("otp")
    data object SIGNUP_DETAIL : OnboardingRoutes("signup_detail")
}