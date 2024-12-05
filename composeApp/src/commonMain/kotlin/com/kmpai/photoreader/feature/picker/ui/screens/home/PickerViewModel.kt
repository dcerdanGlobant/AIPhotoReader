package com.kmpai.photoreader.feature.picker.ui.screens.home

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmpai.photoreader.feature.picker.domain.model.RequestedPicture
import com.kmpai.photoreader.feature.picker.domain.usecase.GetPictureDescription
import com.kmpai.photoreader.feature.picker.ui.screens.chat.ChatState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PickerViewModel(
    private val getPictureDescription: GetPictureDescription
) : ViewModel() {
    private val _homeState: MutableStateFlow<PickerHomeState> =
        MutableStateFlow(PickerHomeState.PickPicture)
    val homeState: StateFlow<PickerHomeState> get() = _homeState.asStateFlow()

    private val _chatState: MutableStateFlow<ChatState> =
        MutableStateFlow(ChatState())
    val chatState: StateFlow<ChatState> get() = _chatState.asStateFlow()

    private var picture: ImageBitmap? = null
    private var contentDescription: String = ""


    fun getPictureData(requestedPicture: RequestedPicture) {
        picture = requestedPicture.bitmap
        viewModelScope.launch {
            _homeState.emit(
                PickerHomeState.PickedPicture(
                    isLoading = true,
                    picture = requestedPicture.bitmap, ""
                )
            )
            getPictureDescription.invoke(requestedPicture.byteArray, requestedPicture.extension)
                .onSuccess {
                    contentDescription = it.contentDescription ?: ""
                    _homeState.emit(
                        PickerHomeState.PickedPicture(
                            isLoading = false,
                            picture = requestedPicture.bitmap, it.contentDescription
                        )
                    )
                }
                .onFailure {
                    _homeState.emit(
                        PickerHomeState.PickedPicture(
                            isLoading = false,
                            picture = requestedPicture.bitmap, "No data"
                        )
                    )
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