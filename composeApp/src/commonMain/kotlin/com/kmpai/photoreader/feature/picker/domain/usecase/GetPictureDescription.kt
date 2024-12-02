package com.kmpai.photoreader.feature.picker.domain.usecase

import com.kmpai.photoreader.feature.picker.domain.model.Picture
import com.kmpai.photoreader.feature.picker.domain.repository.ImageRepository
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository

class GetPictureDescription(private val repository: PickerRepository, private val imageRepository: ImageRepository) {
    suspend operator fun invoke(filename: String): Result<Picture> {
        return imageRepository.getImage(filename)?.let { repository.getPictureDescription(filename, it) }
            ?: return Result.failure(Exception("Image not found"))
    }
}
