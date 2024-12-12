package com.kmpai.photoreader

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kmpai.photoreader.core.ui.App
import com.kmpai.photoreader.core.ui.utils.parcelable
import com.kmpai.photoreader.core.ui.utils.ImageUriProviderSingleton

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(darkTheme = isSystemInDarkTheme(),
                dynamicColor = true)
        }
        handleIncomingShare(intent)
    }

    private fun handleIncomingShare(intent: Intent) {
        if (intent.action == Intent.ACTION_SEND && intent.type?.startsWith("image/") == true) {
            val imageUri: Uri? = intent.parcelable(Intent.EXTRA_STREAM)
            imageUri?.let {
                handleImage(imageUri)
            }
        }
    }

    private fun handleImage(imageUri: Uri) {
        ImageUriProviderSingleton.provider.setImageUri(imageUri.toString())
    }
}



@Preview
@Composable
fun AppAndroidPreview() {
    App(darkTheme = false,
        dynamicColor = true)
}