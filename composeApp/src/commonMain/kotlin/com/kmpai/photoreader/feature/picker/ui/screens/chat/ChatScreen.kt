package com.kmpai.photoreader.feature.picker.ui.screens.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.kmpai.photoreader.feature.picker.ui.screens.home.PickerViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatScreen(
    viewModel: PickerViewModel = koinViewModel<PickerViewModel>(),
    ) {

    val chatState by viewModel.chatState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.surface),
        contentAlignment = Alignment.Center
    ) {
        Column {
            chatState.picture?.let {
                Image(
                    bitmap = it,
                    contentDescription = chatState.description,
                    modifier = Modifier.size(100.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            if(chatState.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}