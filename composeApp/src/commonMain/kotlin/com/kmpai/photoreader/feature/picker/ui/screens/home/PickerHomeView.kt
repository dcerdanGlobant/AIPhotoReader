package com.kmpai.photoreader.feature.picker.ui.screens.home

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun PickerHomeView(homeState: PickerHomeState) {
    if (homeState.isLoading) {
        CircularProgressIndicator()
    } else {
        Text("TODO UI: ${homeState.picture?.contentDescription ?: "No description"}")
    }
}