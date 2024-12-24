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
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import com.kmpai.photoreader.feature.picker.domain.model.Message
import com.kmpai.photoreader.feature.picker.domain.model.Role
import com.kmpai.photoreader.feature.picker.ui.screens.chat.ChatContent
import com.kmpai.photoreader.feature.picker.ui.screens.chat.ChatState

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = true
            )
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
    ChatContent(
        ChatState(
            isLoading = false,
            isError = true,
            conversation = Conversation(
                listOf(
                    Message(
                        Role.ASSISTANT,
                        "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor."
                    ),
                    Message(
                        Role.USER,
                        "Help me more."
                    ),
                    Message(
                        Role.ASSISTANT,
                        "Short message"
                    ),
                    Message(
                        Role.USER,
                        "Another message long. consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Lorem ipsum dolor sit amet"
                    ),
                    Message(
                        Role.ASSISTANT,
                        "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor."
                    ),
                    Message(
                        Role.USER,
                        "I don't understand."
                    ),
                ),
                "image.jpg"
            )
        )
    )
}