package com.kmpai.photoreader.feature.picker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmpai.photoreader.core.ui.utils.ImageUriProviderSingleton
import com.kmpai.photoreader.feature.picker.domain.model.CommonResult
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import com.kmpai.photoreader.feature.picker.domain.model.Message
import com.kmpai.photoreader.feature.picker.domain.model.RequestedPicture
import com.kmpai.photoreader.feature.picker.domain.model.Role
import com.kmpai.photoreader.feature.picker.domain.usecase.GetPictureDescription
import com.kmpai.photoreader.feature.picker.domain.usecase.SendConversation
import com.kmpai.photoreader.feature.picker.ui.screens.chat.ChatState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PickerViewModel(
    private val getPictureDescription: GetPictureDescription,
    private val sendConversation: SendConversation
) : ViewModel() {
    private val _homeState: MutableStateFlow<PickerHomeState> =
        MutableStateFlow(PickerHomeState.PickPicture)
    val homeState: StateFlow<PickerHomeState> get() = _homeState.asStateFlow()

    private val _chatState: MutableStateFlow<ChatState> =
        MutableStateFlow(ChatState())
    val chatState: StateFlow<ChatState> get() = _chatState.asStateFlow()

    private val imageUri = ImageUriProviderSingleton.provider.imageUrl
    private var contentDescription: String = ""

    init {
        if (!imageUri.isNullOrEmpty()) {
            viewModelScope.launch {
                _homeState.emit(
                    PickerHomeState.SharedPicture
                )
            }
        }
    }

    fun getPictureData(requestedPicture: RequestedPicture) {
        viewModelScope.launch {
            _homeState.emit(
                PickerHomeState.PickedPicture(
                    isLoading = true,
                    picture = requestedPicture.bitmap, ""
                )
            )

            viewModelScope.launch {
                getPictureDescription(requestedPicture.byteArray, requestedPicture.extension).collect { result ->
                    when(result) {
                        is CommonResult.Failure -> {
                            _homeState.emit(
                                PickerHomeState.PickedPicture(
                                    isLoading = false,
                                    picture = requestedPicture.bitmap, "No data"
                                )
                            )
                        }
                        is CommonResult.Success -> {

                            val firstMessage = result.data.messages.firstOrNull()?.content

                            if (firstMessage != null) {
                                contentDescription = firstMessage
                            }
                            _homeState.emit(
                                PickerHomeState.PickedPicture(
                                    isLoading = false,
                                    picture = requestedPicture.bitmap,
                                    description = firstMessage
                                )
                            )
                            _chatState.emit(
                                ChatState(
                                    isLoading = false,
                                    picture = requestedPicture.bitmap,
                                    conversation = result.data
                                )
                            )
                        }
                    }

                }
            }
        }
    }

    fun sendAnotherMessage(conversation: Conversation?, message: String) {
        conversation?.let { oldConversation ->
            val messages = mutableListOf<Message>()
            messages.addAll(oldConversation.messages)
            messages.add(Message(Role.USER, message))
            val newConversation = Conversation(messages, oldConversation.filename)
            sendConversation(newConversation)
        }
    }

    fun resendLastMessage(conversation: Conversation?) {
        conversation?.let { oldConversation ->
            val messages = mutableListOf<Message>()
            messages.addAll(oldConversation.messages)
            val newConversation = Conversation(messages, oldConversation.filename)
            sendConversation(newConversation)
        }
    }

    private fun sendConversation(newConversation: Conversation) =
        viewModelScope.launch {
            _chatState.emit(
                ChatState(
                    isLoading = true,
                    picture = chatState.value.picture,
                    conversation = newConversation
                )
            )
            sendConversation.invoke(conversation = newConversation).collect { result ->
                when(result) {
                    is CommonResult.Success -> {
                        _chatState.emit(
                            ChatState(
                                isLoading = false,
                                picture = chatState.value.picture,
                                conversation = result.data
                            )
                        )
                    }

                    is CommonResult.Failure -> {
                        _chatState.emit(
                            ChatState(
                                isLoading = false,
                                isError = true,
                                picture = chatState.value.picture,
                                conversation = newConversation
                            )
                        )
                    }
                }
            }
        }


    fun launchGallery() {
        viewModelScope.launch {
            _homeState.emit(PickerHomeState.LaunchGalery)
        }
    }

    fun launchCamera() {
        viewModelScope.launch {
            _homeState.emit(PickerHomeState.LaunchCamera)
        }

    }

    fun showRationaleDialog() {
        viewModelScope.launch {
            _homeState.emit(PickerHomeState.ShowRationaleDialog)
        }
    }

    fun launchSettings() {
        viewModelScope.launch {
            _homeState.emit(PickerHomeState.LaunchSettings)
        }
    }

    fun showImageSourceOptionDialog() {
        viewModelScope.launch {
            _homeState.emit(PickerHomeState.ImageSourceOptionDialog)
        }
    }

    fun showPickImage() {
        viewModelScope.launch {
            _homeState.emit(PickerHomeState.PickPicture)
        }
    }
}
