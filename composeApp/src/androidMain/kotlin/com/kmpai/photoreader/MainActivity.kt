package com.kmpai.photoreader

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import coil3.toCoilUri
import com.kmpai.photoreader.core.ui.App
import com.kmpai.photoreader.feature.picker.di.ImageUriProviderSingleton

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
        handleIncomingShare(intent)
    }

    private fun handleIncomingShare(intent: Intent) {
        if (intent.action == Intent.ACTION_SEND && intent.type?.startsWith("image/*") == true) {
            val imageUri: Uri? = intent.getParcelableExtra(Intent.EXTRA_STREAM)
            imageUri?.let {
                handleImage(imageUri)
            }
        }
    }

    private fun handleImage(imageUri: Uri) {
        ImageUriProviderSingleton.provider.setImageUri(imageUri.toCoilUri())
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}