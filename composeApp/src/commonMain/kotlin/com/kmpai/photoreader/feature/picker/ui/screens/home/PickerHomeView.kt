package com.kmpai.photoreader.feature.picker.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import coil3.compose.rememberAsyncImagePainter
import com.kmpai.photoreader.feature.picker.di.ImageUriProvider
import com.kmpai.photoreader.feature.picker.di.ImageUriProviderSingleton

@Composable
fun PickerHomeView(homeState: PickerHomeState) {

    val imageUri = ImageUriProviderSingleton.provider.imageUrl

    if(homeState.isLoading) {
        CircularProgressIndicator()
    } else {
        Image(
            painter = rememberAsyncImagePainter(imageUri),
            contentDescription = null
        )
    }
}