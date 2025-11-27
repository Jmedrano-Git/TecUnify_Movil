package com.tecsup.tecunify_movil.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tecsup.tecunify_movil.feature.auth.AuthViewModel
import com.tecsup.tecunify_movil.feature.auth.LoginScreen
import com.tecsup.tecunify_movil.feature.profile.ProfileScreen
import com.tecsup.tecunify_movil.feature.splash.SplashScreen
import kotlinx.coroutines.delay

// Rutas
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home")
    object Profile : Screen("profile")
}

@Composable
fun TecUnifyNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    onGoogleSignInClick: () -> Unit,
    isDarkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit
) {
    val authState by authViewModel.authState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        // -------------------
        //     SPLASH
        // -------------------
        composable(Screen.Splash.route) {
            SplashScreen()

            LaunchedEffect(Unit) {
                delay(1500)

                if (authViewModel.isLoggedIn()) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                } else {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            }
        }

        // -------------------
        //     LOGIN
        // -------------------
        composable(Screen.Login.route) {
            LoginScreen(
                state = authState,
                onGoogleClick = {
                    onGoogleSignInClick()
                }
            )
        }

        // -------------------
        //     HOME
        // -------------------
        composable(Screen.Home.route) {
            MainScreen(rootNavController = navController)
        }

        // -------------------
        //     PROFILE
        // -------------------
        composable(Screen.Profile.route) {
            ProfileScreen(
                user = authViewModel.currentUser,
                isDarkMode = isDarkMode,
                onBack = {
                    navController.popBackStack()
                },
                onLogout = {
                    authViewModel.signOut()
                    navController.navigate(Screen.Login.route){
                        popUpTo(Screen.Home.route){inclusive = true}
                    }
                },
                onToggleDarkMode = onToggleDarkMode
            )
        }
    }
}