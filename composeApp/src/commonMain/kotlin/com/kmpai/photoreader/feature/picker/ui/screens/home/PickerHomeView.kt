package com.kmpai.photoreader.feature.picker.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import coil3.compose.rememberAsyncImagePainter
import com.kmpai.photoreader.core.ui.utils.ImageUriProviderSingleton

@Composable
fun PickerHomeView(homeState: PickerHomeState) {

    val imageUri = ImageUriProviderSingleton.provider.imageUrl

    Image(
        painter = rememberAsyncImagePainter(imageUri),
        contentDescription = null
    )
}
