package com.kmpai.photoreader.feature.picker.domain.usecase

import com.kmpai.photoreader.feature.picker.domain.model.CommonResult
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository
import kotlinx.coroutines.flow.Flow

class GetPictureDescription(private val repository: PickerRepository) {
    suspend operator fun invoke(image: ByteArray, extension: String): Flow<CommonResult<Conversation>> {
        return repository.sendImageAndStartConversation(extension, image)
    }
}
