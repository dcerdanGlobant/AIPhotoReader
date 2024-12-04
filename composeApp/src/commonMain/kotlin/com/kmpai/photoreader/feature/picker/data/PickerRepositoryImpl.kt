package com.kmpai.photoreader.feature.picker.data

import androidx.compose.ui.graphics.ImageBitmap
import com.kmpai.photoreader.feature.picker.domain.model.Picture
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository

class PickerRepositoryImpl: PickerRepository {
    override fun getPictureDescription(bitmap: ImageBitmap): Result<Picture> {
        return Result.success(Picture("Content description IA"))
    }
}