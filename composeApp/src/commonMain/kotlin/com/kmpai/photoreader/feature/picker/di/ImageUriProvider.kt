package com.kmpai.photoreader.feature.picker.di

import coil3.Uri

expect class ImageUriProvider {
    var imageUrl: String?
    fun setImageUri(uri: String)
}

expect object ImageUriProviderSingleton {
    val provider: ImageUriProvider
}