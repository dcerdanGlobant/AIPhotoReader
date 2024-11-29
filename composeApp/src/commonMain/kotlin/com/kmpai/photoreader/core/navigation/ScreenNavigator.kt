package com.kmpai.photoreader.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kmpai.photoreader.feature.picker.ui.addPickerNavGraph
import com.kmpai.photoreader.feature.picker.ui.pickerNavConfig

@Composable
fun ScreenNavigator() {
    val navController: NavHostController = rememberNavController()
    Scaffold { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = pickerNavConfig.route,
                modifier = Modifier.fillMaxSize(),
            ) {
                addPickerNavGraph(navController)
            }
        }
    }
}