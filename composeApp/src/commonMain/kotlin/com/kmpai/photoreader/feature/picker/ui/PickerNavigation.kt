package com.kmpai.photoreader.feature.picker.ui

import aiphotoreader.composeapp.generated.resources.Res
import aiphotoreader.composeapp.generated.resources.chat_ia
import aiphotoreader.composeapp.generated.resources.toolbar_home_picker
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kmpai.photoreader.core.extension.toTopBarConfig
import com.kmpai.photoreader.core.navigation.ScreenNavigatorConfig
import com.kmpai.photoreader.core.ui.TopBarConfig
import com.kmpai.photoreader.feature.picker.ui.screens.chat.ChatScreen
import com.kmpai.photoreader.feature.picker.ui.screens.home.PickerHomeScreen

val pickerNavConfig = ScreenNavigatorConfig(
    route = "picker",
)

private val homePickerNavConfig = ScreenNavigatorConfig(
    "homePicker",
    title = Res.string.toolbar_home_picker
)

private val chatIANavConfig = ScreenNavigatorConfig(
    "chatIA",
    title = Res.string.chat_ia,
    topBarBackEnabled = true
)

fun NavGraphBuilder.addPickerNavGraph(
    navController: NavController,
    onTopBarConfig: ((topBarConfig: TopBarConfig) -> Unit),
) {

    navigation(startDestination = homePickerNavConfig.route, route = pickerNavConfig.route) {
        composable(route = homePickerNavConfig.route) {
            onTopBarConfig.invoke(homePickerNavConfig.toTopBarConfig())
            PickerHomeScreen(
                onOpenChat = { navController.navigate(route = chatIANavConfig.route)})
        }

        composable(route = chatIANavConfig.route) {
            onTopBarConfig.invoke(chatIANavConfig.toTopBarConfig())
            ChatScreen(
            )
        }
    }
}