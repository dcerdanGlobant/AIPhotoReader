package com.kmpai.photoreader.feature.picker.di

import coil3.Uri

expect class ImageUriProvider {
    var imageUrl: Uri?
    fun setImageUri(uri: Uri)
}

expect object ImageUriProviderSingleton {
    val provider: ImageUriProvider
}