package com.kmpai.photoreader.feature.picker.ui.screens.home

import androidx.compose.ui.graphics.ImageBitmap

sealed class PickerHomeState {
    data object PickPicture: PickerHomeState()
    data object ImageSourceOptionDialog: PickerHomeState()
    data object LaunchCamera : PickerHomeState()
    data object LaunchGalery : PickerHomeState()
    data object ShowRationaleDialog : PickerHomeState()
    data object LaunchSettings: PickerHomeState()
    data object SharedPicture: PickerHomeState()
    data class PickedPicture(val isLoading: Boolean = true, val picture: ImageBitmap, val description: String? = null): PickerHomeState()
}