package com.kmpai.photoreader.feature.picker.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kmpai.photoreader.core.navigation.ScreenNavigatorConfig
import com.kmpai.photoreader.feature.picker.ui.screens.home.PickerHomeScreen

val pickerNavConfig = ScreenNavigatorConfig(
    route = "picker",
)

private val homePickerNavConfig = ScreenNavigatorConfig(
    "homePicker"
)

fun NavGraphBuilder.addPickerNavGraph(
    navController: NavController
) {
    navigation(startDestination = homePickerNavConfig.route, route = pickerNavConfig.route) {
        composable(route = homePickerNavConfig.route) {
            PickerHomeScreen()
        }
    }
}