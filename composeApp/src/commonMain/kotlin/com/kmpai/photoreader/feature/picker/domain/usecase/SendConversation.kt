package com.kmpai.photoreader.feature.picker.domain.usecase

import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository

class SendConversation(private val repository: PickerRepository) {
    suspend operator fun invoke(conversation: Conversation): Result<Conversation> {
        return repository.sendConversation(conversation)
    }
}
