package com.kmpai.photoreader.feature.picker.ui.screens.home

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmpai.photoreader.feature.picker.domain.usecase.GetPictureDescription
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PickerHomeViewModel(
    private val getPictureDescription: GetPictureDescription
) : ViewModel() {
    private val _homeState: MutableStateFlow<PickerHomeState> =
        MutableStateFlow(PickerHomeState.PickPicture)
    val homeState: StateFlow<PickerHomeState> get() = _homeState.asStateFlow()
    private var picture: ImageBitmap? = null
    private var contentDescription: String = ""


    fun getPictureData(bitmap: ImageBitmap) {
        picture = bitmap
        viewModelScope.launch {
            _homeState.emit(
                PickerHomeState.PickedPicture(
                    isLoading = true,
                    picture = bitmap, ""
                )
            )
            delay(2000)
            getPictureDescription.invoke(bitmap)
                .onSuccess {
                    contentDescription = it.contentDescription ?: ""
                    _homeState.emit(
                        PickerHomeState.PickedPicture(
                            isLoading = false,
                            picture = bitmap, it.contentDescription
                        )
                    )
                }
                .onFailure {
                    _homeState.emit(
                        PickerHomeState.PickedPicture(
                            isLoading = false,
                            picture = bitmap, "No data"
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

    fun openChat() {
        viewModelScope.launch {
            picture?.let {
                _homeState.emit(
                    PickerHomeState.OpenChat(
                        picture = it,
                        description = contentDescription
                    )
                )
            }

        }
    }


}