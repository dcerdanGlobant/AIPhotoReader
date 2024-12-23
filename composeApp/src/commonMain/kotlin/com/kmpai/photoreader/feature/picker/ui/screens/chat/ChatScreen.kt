package com.kmpai.photoreader.feature.picker.ui.screens.chat

import aiphotoreader.composeapp.generated.resources.Res
import aiphotoreader.composeapp.generated.resources.compose_multiplatform
import aiphotoreader.composeapp.generated.resources.globant_logo_short
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.kmpai.photoreader.feature.picker.ui.screens.home.PickerViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import com.kmpai.photoreader.feature.picker.domain.model.Message
import com.kmpai.photoreader.feature.picker.domain.model.Role
import org.jetbrains.compose.resources.painterResource

@Composable
fun ChatScreen(
    viewModel: PickerViewModel = koinViewModel<PickerViewModel>(),
) {

    val chatState by viewModel.chatState.collectAsState()
    ChatContent(chatState, onSendMessage = {
        viewModel.sendAnotherMessage(it)
    }, onResendMessage = {
        viewModel.resendLastMessage()
    })
}

@Composable
fun ChatContent(chatState: ChatState, onSendMessage: (String) -> Unit = {}, onResendMessage: () -> Unit = {}) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            chatState.picture?.let {
                Image(
                    bitmap = it,
                    contentDescription = "Image uploaded by the user",
                    modifier = Modifier.size(100.dp).clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
            } ?: Box(
                modifier = Modifier.size(100.dp)
                    .border(1.dp, shape = RoundedCornerShape(4.dp), color = MaterialTheme.colorScheme.outline)
            )

            if (chatState.isLoading) {
                CircularProgressIndicator()
            }
            ConversationsList(
                chatState = chatState,
                modifier = Modifier.weight(1f),
                onResendMessage = onResendMessage
            )
            ChatInputArea(onSendMessage = onSendMessage)
        }
    }
}

@Composable
private fun ConversationsList(chatState: ChatState, modifier: Modifier, onResendMessage: () -> Unit) {
    val messages = chatState.conversation?.messages ?: emptyList();
    LazyColumn(modifier = modifier.padding(top = 16.dp)) {
        items(messages) { message ->
            val isMessageFromAI = message.role == Role.ASSISTANT
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                val islastMessage = message.equals(messages.last());
                if (isMessageFromAI) {
                    Icon(
                        painter = painterResource(Res.drawable.globant_logo_short),
                        contentDescription = "AI Icon",
                        modifier = Modifier.size(48.dp).padding(end = 8.dp)
                    )
                }
                if (islastMessage && !isMessageFromAI && chatState.isError) {
                    Text(
                        color = Color.Red,
                        text = message.content,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Right
                    )
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Error Icon",
                        modifier = Modifier.size(48.dp).padding(end = 8.dp)
                            .clickable(onClick = { onResendMessage() })
                    )
                } else {
                    Text(
                        text = message.content,
                        modifier = Modifier.weight(1f),
                        textAlign = if (isMessageFromAI) TextAlign.Left else TextAlign.Right
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatInputArea(onSendMessage: (String) -> Unit) {
    var messageText by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = messageText,
            onValueChange = { messageText = it },
            modifier = Modifier.weight(1f),
            placeholder = { Text("Ask a question to the AI") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onSend = {
                    onSendMessage(messageText)
                    messageText = ""
                }
            )
        )
        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = {
                onSendMessage(messageText)
                messageText = ""
            }
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
        }
    }
}
