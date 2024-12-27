package com.kmpai.photoreader.feature.picker.data.rest.model

import kotlinx.serialization.Serializable

@Serializable
data class APIRequest(
    val model: String,
    val messages: List<APIMessage>,
    val revision: Long,
    val revisionName: String
)

