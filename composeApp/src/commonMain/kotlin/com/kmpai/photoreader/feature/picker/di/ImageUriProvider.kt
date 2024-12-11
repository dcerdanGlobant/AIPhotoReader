package com.kmpai.photoreader.feature.picker.di

class ImageUriProvider {
    var imageUrl: String? = null

    fun setImageUri(uri: String) {
        imageUrl = uri
    }
}

object ImageUriProviderSingleton {
    val provider: ImageUriProvider = ImageUriProvider()
}