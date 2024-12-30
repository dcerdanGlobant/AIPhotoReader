package com.kmpai.photoreader.feature.picker.data.rest.model

import kotlinx.serialization.Serializable

@Serializable
data class APIMessage (
    val role: String,
    val content: String
)
