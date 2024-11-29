package com.kmpai.photoreader.feature.picker.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PickerHomeScreen (
    viewModel: PickerHomeViewModel = koinViewModel<PickerHomeViewModel>()
) {
    val homeState by viewModel.homeState.collectAsState()

    PickerHomeView(homeState)
}