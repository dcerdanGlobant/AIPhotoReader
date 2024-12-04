package com.kmpai.photoreader.feature.picker.domain.usecase

import androidx.compose.ui.graphics.ImageBitmap
import com.kmpai.photoreader.feature.picker.domain.model.Picture
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository

class GetPictureDescription(private val repository: PickerRepository) {
    suspend operator fun invoke(bitmap: ImageBitmap): Result<Picture> {
        return repository.getPictureDescription(bitmap)
    }
}