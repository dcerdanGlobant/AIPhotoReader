package com.kmpai.photoreader.feature.picker.di

import coil3.Uri

actual class ImageUriProvider {
    actual var imageUrl: Uri? = null

    actual fun setImageUri(uri: Uri) {
        imageUrl = uri
    }
}

actual object ImageUriProviderSingleton {
    actual val provider = ImageUriProvider()
}