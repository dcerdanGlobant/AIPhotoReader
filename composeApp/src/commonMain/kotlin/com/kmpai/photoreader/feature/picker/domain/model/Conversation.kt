package com.kmpai.photoreader.feature.picker.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Conversation(val messages: List<Message>, val filename: String) {
    fun copyWith(messages: List<Message> = this.messages, filename: String = this.filename): Conversation {
        return Conversation(messages, filename)
    }
}

@Serializable
data class Message(val role: Role, val content: String) {
    val isFromAssistant: Boolean
        get() = role == Role.ASSISTANT
}

enum class Role(val customName: String) {
    USER("user"),
    ASSISTANT("assistant")
}

sealed class CommonResult<out T> {
    data class Success<out T>(val data: T) : CommonResult<T>()
    data class Failure(val exception: Throwable) : CommonResult<Nothing>()
}

