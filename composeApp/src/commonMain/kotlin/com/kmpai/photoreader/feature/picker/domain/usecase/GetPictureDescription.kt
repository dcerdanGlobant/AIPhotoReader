package com.kmpai.photoreader.feature.picker.domain.usecase

import com.kmpai.photoreader.feature.picker.domain.model.Picture
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository

class GetPictureDescription(private val repository: PickerRepository) {
    suspend operator fun invoke(): Result<Picture> {
        return repository.getPictureDescription()
    }
}