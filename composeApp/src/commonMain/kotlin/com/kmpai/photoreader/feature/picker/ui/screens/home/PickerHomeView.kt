package com.kmpai.photoreader.feature.picker.ui.screens.home

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun PickerHomeView(homeState: PickerHomeState) {
    if (homeState.isLoading) {
        CircularProgressIndicator()
    } else {
        if (homeState.picture == null) {
            Text("Permission Denied")
        } else Text("TODO UI")
    }
}