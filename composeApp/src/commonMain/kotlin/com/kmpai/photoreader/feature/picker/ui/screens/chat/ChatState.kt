package com.kmpai.photoreader.feature.picker.ui.screens.chat

import androidx.compose.ui.graphics.ImageBitmap
import com.kmpai.photoreader.feature.picker.domain.model.Conversation

data class ChatState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val picture: ImageBitmap? = null,
    val conversation: Conversation? = null
)
