package com.kmpai.photoreader.feature.picker.ui.screens.chat

import androidx.compose.ui.graphics.ImageBitmap

data class ChatState(val isLoading: Boolean = true, val picture: ImageBitmap? = null, val description: String? = null)
