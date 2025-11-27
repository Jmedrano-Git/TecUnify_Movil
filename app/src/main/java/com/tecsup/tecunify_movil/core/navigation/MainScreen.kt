package com.tecsup.tecunify_movil.core.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.tecsup.tecunify_movil.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    rootNavController: NavHostController
) {
    val bottomNavController = rememberNavController()

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Spaces,
        BottomNavItem.Reservations,
        BottomNavItem.Schedule,
        BottomNavItem.Chat
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("TecUnify")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        rootNavController.navigate(Screen.Profile.route)
                    }) {
                        val user = FirebaseAuth.getInstance().currentUser
                        val photoUrl = user?.photoUrl

                        Image(
                            painter = rememberAsyncImagePainter(photoUrl),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            )
        },
        bottomBar = {
            TecUnifyBottomBar(
                navController = bottomNavController,
                items = items
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                SimpleCenterText("Inicio")
            }
            composable(BottomNavItem.Spaces.route) {
                SimpleCenterText("Espacios")
            }
            composable(BottomNavItem.Reservations.route) {
                SimpleCenterText("Mis Reservas")
            }
            composable(BottomNavItem.Schedule.route) {
                SimpleCenterText("Horarios")
            }
            composable(BottomNavItem.Chat.route) {
                SimpleCenterText("TeclA Chat")
            }
        }
    }
}

@Composable
private fun SimpleCenterText(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text)
    }
}