package com.kmpai.photoreader.feature.picker.di

import androidx.compose.ui.graphics.ImageBitmap

expect class ImageUriProvider {
    var imageUrl: String?
    fun setImageUri(uri: String)
}

expect object ImageUriProviderSingleton {
    val provider: ImageUriProvider
}