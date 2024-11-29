package com.kmpai.photoreader.feature.picker.ui.screens.home

import com.kmpai.photoreader.feature.picker.domain.model.Picture

data class PickerHomeState (
    val isLoading: Boolean = true,
    val picture: Picture? = null
)