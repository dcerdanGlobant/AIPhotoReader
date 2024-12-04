package com.kmpai.photoreader.feature.picker.domain.repository

import androidx.compose.ui.graphics.ImageBitmap
import com.kmpai.photoreader.feature.picker.domain.model.Picture

interface PickerRepository {
    fun getPictureDescription(bitmap: ImageBitmap): Result<Picture>
}