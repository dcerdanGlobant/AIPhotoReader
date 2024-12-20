package com.kmpai.photoreader.feature.picker.ui

import android.content.ContentResolver
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.kmpai.photoreader.core.ui.utils.UriUtils
import com.kmpai.photoreader.core.ui.utils.ImageUriProviderSingleton

@Composable
actual fun rememberSharedManager(onResult: (SharedImage?) -> Unit) {

    val context = LocalContext.current
    val contentResolver: ContentResolver = context.contentResolver
    ImageUriProviderSingleton.provider.imageUrl?.let {
        onResult.invoke(
            SharedImage(
                UriUtils.getBitmapFromUri(
                    it.toUri(),
                    contentResolver
                )
            )
        )
    }
}