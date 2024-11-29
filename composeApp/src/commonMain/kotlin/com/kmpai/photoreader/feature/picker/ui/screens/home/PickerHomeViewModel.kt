package com.kmpai.photoreader.feature.picker.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmpai.photoreader.feature.picker.domain.model.Picture
import com.kmpai.photoreader.feature.picker.domain.usecase.GetPictureDescription
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PickerHomeViewModel (
    private val getPictureDescription: GetPictureDescription
) : ViewModel() {
    private val _homeState: MutableStateFlow<PickerHomeState> = MutableStateFlow(PickerHomeState())
    val homeState: StateFlow<PickerHomeState> get() = _homeState.asStateFlow()

    init {
        getPictureData()
    }

    private fun getPictureData() {
        viewModelScope.launch {
            delay(2000)
            getPictureDescription.invoke()
                .onSuccess {
                    _homeState.emit(
                        PickerHomeState(
                            isLoading = false,
                            Picture("","")
                        )
                    )
                }
                .onFailure {
                    _homeState.emit(
                        PickerHomeState(
                            isLoading = false,
                            Picture("","")
                        )
                    )
                }
        }
    }

}