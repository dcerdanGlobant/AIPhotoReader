package com.kmpai.photoreader.feature.picker.data.rest.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class APIResponse (
    val id: String,
    val choices: List<Choice>,
    val created: Long,
    val model: String,
    @SerialName("object")
    val imageResponseObject: String,
    @SerialName("system_fingerprint")
    val systemFingerprint: String,
    val usage: Usage,
)

@Serializable
data class Choice (
    @SerialName("finish_reason")
    val finishReason: String,
    val index: Long,
    val message: APIMessage
)

@Serializable
data class Usage (
    @SerialName("completion_tokens")
    val completionTokens: Long,

    @SerialName("prompt_tokens")
    val promptTokens: Long,

    @SerialName("total_tokens")
    val totalTokens: Long,

    @SerialName("completion_tokens_details")
    val completionTokensDetails: CompletionTokensDetails,

    @SerialName("prompt_tokens_details")
    val promptTokensDetails: PromptTokensDetails
)

@Serializable
data class CompletionTokensDetails (
    @SerialName("accepted_prediction_tokens")
    val acceptedPredictionTokens: Long,

    @SerialName("audio_tokens")
    val audioTokens: Long,

    @SerialName("reasoning_tokens")
    val reasoningTokens: Long,

    @SerialName("rejected_prediction_tokens")
    val rejectedPredictionTokens: Long
)

@Serializable
data class PromptTokensDetails (
    @SerialName("audio_tokens")
    val audioTokens: Long,

    @SerialName("cached_tokens")
    val cachedTokens: Long
)