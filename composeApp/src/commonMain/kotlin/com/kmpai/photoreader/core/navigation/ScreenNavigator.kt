package com.kmpai.photoreader.navigation

import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun ScreenNavigator() {
    val navController: NavHostController = rememberNavController()
    Scaffold { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = homeNavConfig.route,
                modifier = Modifier.fillMaxSize(),
            ) {
                addHomeNavGraph(navController)
            }
        }
    }
}