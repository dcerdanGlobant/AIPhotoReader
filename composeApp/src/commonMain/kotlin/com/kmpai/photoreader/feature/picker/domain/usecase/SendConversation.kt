package com.kmpai.photoreader.feature.picker.domain.usecase

import com.kmpai.photoreader.feature.picker.domain.model.CommonResult
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository
import kotlinx.coroutines.flow.Flow

class SendConversation(private val repository: PickerRepository) {
    suspend operator fun invoke(conversation: Conversation): Flow<CommonResult<Conversation>> {
        return repository.sendConversation(conversation)
    }
}
