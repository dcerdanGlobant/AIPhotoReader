package com.kmpai.photoreader.core.ui.utils

class ImageUriProvider {
    var imageUrl: String? = null

    fun setImageUri(uri: String) {
        imageUrl = uri
    }
}

object ImageUriProviderSingleton {
    val provider: ImageUriProvider = ImageUriProvider()
}