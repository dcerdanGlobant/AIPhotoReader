package com.kmpai.photoreader.feature.picker.data.datasource

import com.kmpai.photoreader.feature.picker.data.rest.RestApi
import com.kmpai.photoreader.feature.picker.data.rest.mappers.toAPIMessages
import com.kmpai.photoreader.feature.picker.data.rest.mappers.appendConversation
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class PickerAPIDatasourceImpl(
    private val restApi: RestApi,
) : PickerDatasource {

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun sendImageAndStartConversation(
        extension: String,
        imageByteArray: ByteArray
    ): Result<Conversation> {
        try {
            val imageName = "${Uuid.random()}.$extension"
            val serverImageName = restApi.uploadImage(imageByteArray, imageName, "image/$extension")
            val conversation = Conversation(emptyList(), serverImageName)
            return send(conversation)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun sendConversation(conversation: Conversation): Result<Conversation> {
        return try {
            send(conversation)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun send(conversation: Conversation): Result<Conversation> {
        val response = restApi.sendMessagesToAI(conversation.toAPIMessages())
        return Result.success(conversation.appendConversation(response))
    }
}
