package com.kmpai.photoreader.feature.picker.domain.model

data class Conversation(val messages: List<Message>, val filename: String)

data class Message(val role: Role, val content: String)

enum class Role(val customName: String) {
    USER("user"),
    ASSISTANT("assistant")
}