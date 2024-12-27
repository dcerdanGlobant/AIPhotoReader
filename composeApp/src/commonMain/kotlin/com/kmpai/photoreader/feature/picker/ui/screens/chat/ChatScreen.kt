package com.kmpai.photoreader.feature.picker.ui.screens.chat

import aiphotoreader.composeapp.generated.resources.Res
import aiphotoreader.composeapp.generated.resources.globant_logo_short
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Surface
import androidx.compose.ui.text.style.TextAlign
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
                onResendClicked = onResendMessage
            )
            ChatInputArea(onSendMessage = onSendMessage)
        }
    }
}

@Composable
private fun ConversationsList(chatState: ChatState, modifier: Modifier, onResendClicked: () -> Unit) {
    val messages = chatState.conversation?.messages ?: emptyList()
    LazyColumn(modifier = modifier.padding(top = 16.dp)) {
        items(messages) { message ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.End
            ) {
                if (message.isFromAssistant) {
                    AIMessage(message.content, Modifier.weight(1f).padding(end = 48.dp))
                } else {
                    val islastMessage = message == messages.last()
                    if (islastMessage && chatState.isError) {
                        HumanMessageWithError(message.content, Modifier.padding(start = 48.dp), onResendClicked)
                    } else {
                        HumanMessage(message.content, Modifier.padding(start = 48.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun AIMessage(message: String, modifier: Modifier) {
    Icon(
        painter = painterResource(Res.drawable.globant_logo_short),
        contentDescription = "AI Icon",
        modifier = Modifier.size(48.dp).padding(end = 8.dp)
    )
    Text(
        text = message,
        modifier = modifier,
        textAlign = TextAlign.Left
    )
}

@Composable
private fun HumanMessageWithError(message: String, modifier: Modifier, onResendClicked: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0x50CCCCCC),
        modifier = modifier
            .padding(8.dp)
            .wrapContentSize(Alignment.TopEnd).clickable(onClick = { onResendClicked() })
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                color = Color.Red,
                text = message,
                modifier = Modifier.padding(end = 4.dp)
            )
            Icon(
                imageVector = Icons.Filled.Refresh,
                tint = Color.Red,
                contentDescription = "Resend message Icon",
                modifier = Modifier.size(24.dp)
            )
        }
    }

}

@Composable
private fun HumanMessage(message: String, modifier: Modifier) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0x50CCCCCC),
        modifier = modifier
            .padding(8.dp)
            .wrapContentSize(Alignment.TopEnd)
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(16.dp),
        )
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
