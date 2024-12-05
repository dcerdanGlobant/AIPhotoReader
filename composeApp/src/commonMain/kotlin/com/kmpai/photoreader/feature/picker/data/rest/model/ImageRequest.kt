package com.kmpai.photoreader.feature.picker.data.rest.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageRequest(
    val model: String,
    val messages: List<Message>,
    val revision: Long,
    val revisionName: String
)

