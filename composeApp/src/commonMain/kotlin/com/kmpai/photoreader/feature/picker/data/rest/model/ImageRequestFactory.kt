package com.kmpai.photoreader.feature.picker.data.rest.model

class ImageRequestFactory {
    
    companion object {
        private const val ASSISTANT = "saia:assistant:ImageAssistant"
        private const val ROLE = "user"
        private const val REVISION = 1L
        private const val REVISION_NAME = "1"
    }
    
    fun create(fileName: String) : ImageRequest {
        return ImageRequest(
            model = ASSISTANT,
            messages = listOf(Message(ROLE, "Analyse an image {file: $fileName}")),
            revision = REVISION,
            revisionName = REVISION_NAME,
        )
    }
}