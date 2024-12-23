package com.kmpai.photoreader.feature.picker.data.rest.model

class ImageResponseBuilder {
    private var id: String = "idBuilder"
    private var choices: List<Choice> = listOf(ChoiceBuilder().build())
    private var created: Long = 0L
    private var model: String = "modelBuilder"
    private var imageResponseObject: String = "imageResponseObjectBuilder"
    private var systemFingerprint: String = "systemFingerprintBuilder"
    private var usage: Usage = UsageBuilder().build()

    fun id(id: String) = apply { this.id = id }
    fun choices(choices: List<Choice>) = apply { this.choices = choices }
    fun created(created: Long) = apply { this.created = created }
    fun model(model: String) = apply { this.model = model }
    fun imageResponseObject(imageResponseObject: String) = apply { this.imageResponseObject = imageResponseObject }
    fun systemFingerprint(systemFingerprint: String) = apply { this.systemFingerprint = systemFingerprint }
    fun usage(usage: Usage) = apply { this.usage = usage }

    fun build(): ImageResponse {
        return ImageResponse(id, choices, created, model, imageResponseObject, systemFingerprint, usage)
    }
}
class ChoiceBuilder {
    private var finishReason: String? = "finishReasonBuilder"
    private var index: Long? = 0L
    private var message: Message? = MessageBuilder().build()

    fun finishReason(finishReason: String) = apply { this.finishReason = finishReason }
    fun index(index: Long) = apply { this.index = index }
    fun message(message: Message) = apply { this.message = message }

    fun build(): Choice = Choice(
        finishReason = finishReason ?: throw IllegalArgumentException("finishReason is required"),
        index = index ?: throw IllegalArgumentException("index is required"),
        message = message ?: throw IllegalArgumentException("message is required")
    )
}

class MessageBuilder {
    private var role: String? = "RoleBuilder"
    private var content: String? = "ContentBuilder"

    fun withRole(role: String) = apply { this.role = role }
    fun withContent(content: String) = apply { this.content = content }

    fun build(): Message {
        requireNotNull(role) { "Role must be set" }
        requireNotNull(content) { "Content must be set" }
        return Message(role!!, content!!)
    }
}

class UsageBuilder {
    private var completionTokens: Long = 0L
    private var promptTokens: Long = 0L
    private var totalTokens: Long = 0L
    private var completionTokensDetails: CompletionTokensDetails? = CompletionTokensDetailsBuilder().build()
    private var promptTokensDetails: PromptTokensDetails? = PromptDetailsBuilder().build()

    fun completionTokens(completionTokens: Long) = apply { this.completionTokens = completionTokens }
    fun promptTokens(promptTokens: Long) = apply { this.promptTokens = promptTokens }
    fun totalTokens(totalTokens: Long) = apply { this.totalTokens = totalTokens }
    fun completionTokensDetails(completionTokensDetails: CompletionTokensDetails) = apply { this.completionTokensDetails = completionTokensDetails }
    fun promptTokensDetails(promptTokensDetails: PromptTokensDetails) = apply { this.promptTokensDetails = promptTokensDetails }

    fun build(): Usage {
        requireNotNull(completionTokensDetails) { "completionTokensDetails must not be null" }
        requireNotNull(promptTokensDetails) { "promptTokensDetails must not be null" }
        return Usage(completionTokens, promptTokens, totalTokens, completionTokensDetails!!, promptTokensDetails!!)
    }
}

class CompletionTokensDetailsBuilder {
    private var acceptedPredictionTokens: Long? = 0L
    private var audioTokens: Long? = 0L
    private var reasoningTokens: Long? = 0L
    private var rejectedPredictionTokens: Long? = 0L

    fun withAcceptedPredictionTokens(acceptedPredictionTokens: Long) = apply {
        this.acceptedPredictionTokens = acceptedPredictionTokens
    }

    fun withAudioTokens(audioTokens: Long) = apply {
        this.audioTokens = audioTokens
    }

    fun withReasoningTokens(reasoningTokens: Long) = apply {
        this.reasoningTokens = reasoningTokens
    }

    fun withRejectedPredictionTokens(rejectedPredictionTokens: Long) = apply {
        this.rejectedPredictionTokens = rejectedPredictionTokens
    }

    fun build(): CompletionTokensDetails {
        requireNotNull(acceptedPredictionTokens) { "acceptedPredictionTokens must not be null" }
        requireNotNull(audioTokens) { "audioTokens must not be null" }
        requireNotNull(reasoningTokens) { "reasoningTokens must not be null" }
        requireNotNull(rejectedPredictionTokens) { "rejectedPredictionTokens must not be null" }

        return CompletionTokensDetails(
            acceptedPredictionTokens = acceptedPredictionTokens!!,
            audioTokens = audioTokens!!,
            reasoningTokens = reasoningTokens!!,
            rejectedPredictionTokens = rejectedPredictionTokens!!
        )
    }
}

class PromptDetailsBuilder {
    private var audioTokens: Long = 0L
    private var cachedTokens: Long = 0L

    fun setAudioTokens(audioTokens: Long): PromptDetailsBuilder = apply {
        this.audioTokens = audioTokens
    }

    fun setCachedTokens(cachedTokens: Long): PromptDetailsBuilder = apply {
        this.cachedTokens = cachedTokens
    }

    fun build(): PromptTokensDetails {
        return PromptTokensDetails(audioTokens, cachedTokens)
    }
}