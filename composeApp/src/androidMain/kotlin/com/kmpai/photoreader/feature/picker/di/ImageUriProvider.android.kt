package com.kmpai.photoreader.feature.picker.di

actual class ImageUriProvider {
    actual var imageUrl: String? = null

    actual fun setImageUri(uri: String) {
        imageUrl = uri
    }
}

actual object ImageUriProviderSingleton {
    actual val provider = ImageUriProvider()
}