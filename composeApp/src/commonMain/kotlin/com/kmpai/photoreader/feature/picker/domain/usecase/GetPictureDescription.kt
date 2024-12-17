package com.kmpai.photoreader.feature.picker.domain.usecase

import com.kmpai.photoreader.feature.picker.domain.model.Picture
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository
import com.kmpai.photoreader.di.KoinProvider

class GetPictureDescription(private val repository: PickerRepository) {
    suspend operator fun invoke(image: ByteArray, extension: String): Result<Picture> {
        return repository.getPictureDescription(extension, image)
    }
}

object KoinDependency {
    fun getPictureDescription(): GetPictureDescription {
        return KoinProvider.koinApplication.koin.get()
    }
}