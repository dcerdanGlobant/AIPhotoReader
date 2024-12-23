package com.kmpai.photoreader.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kmpai.photoreader.core.ui.AppTopBar
import com.kmpai.photoreader.core.ui.TopBarConfig
import com.kmpai.photoreader.feature.picker.ui.addPickerNavGraph
import com.kmpai.photoreader.feature.picker.ui.pickerNavConfig
import com.kmpai.photoreader.feature.picker.ui.screens.home.PickerViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ScreenNavigator(viewModel: PickerViewModel = koinViewModel<PickerViewModel>()) {
    val navController: NavHostController = rememberNavController()
    var topBarConfig by remember { mutableStateOf(TopBarConfig()) }

    Scaffold(
        topBar = {
            AppTopBar(
                topBarConfig,
                onBackPressed = { navController.navigateUp() },
            )
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = pickerNavConfig.route,
                modifier = Modifier.fillMaxSize(),
            ) {
                addPickerNavGraph(navController, viewModel) { topBarConfig = it }
            }
        }
    }
}