package com.kmpai.photoreader.feature.picker.data.rest.mappers

import com.kmpai.photoreader.feature.picker.data.rest.model.APIMessage
import com.kmpai.photoreader.feature.picker.data.rest.model.ApiMessageBuilder
import com.kmpai.photoreader.feature.picker.data.rest.model.ApiResponseBuilder
import com.kmpai.photoreader.feature.picker.data.rest.model.ChoiceBuilder
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import com.kmpai.photoreader.feature.picker.domain.model.Message
import com.kmpai.photoreader.feature.picker.domain.model.Role
import kotlin.test.Test
import kotlin.test.assertEquals

class ConversationMapperTest {
    private val messageContent = "This is a message content"
    private val filename = "image.jpg"

    @Test
    fun `appendConversation should add message to conversation`() {
        // Arrange
        val originalConversation = Conversation(messages = listOf(Message(role = Role.USER, content = "Dime que ves")),filename= filename,)
        val response = ApiResponseBuilder().choices(listOf(ChoiceBuilder().message(ApiMessageBuilder().withRole(Role.ASSISTANT.customName).withContent(messageContent).build()) .build())).build()

        // Act
        val updatedConversation = originalConversation.appendConversation(response)

        // Assert
        val expectedMessages = listOf(Message(Role.USER,"Dime que ves"),Message(Role.ASSISTANT, messageContent))
        assertEquals(expectedMessages, updatedConversation.messages)
    }

    @Test
    fun `toAPIMessages should convert messages and add user message at the beginning`() {
        // Arrange
        val conversation = Conversation(
            messages = listOf(Message(Role.USER, "Hello"), Message(Role.ASSISTANT, "Hi there!")),
                filename = filename)

        // Act
        val apiMessages = conversation.toAPIMessages("en")

        // Assert
        val expectedMessages = listOf(
            ApiMessageBuilder().withRole(Role.USER.customName).withContent( "Analyze using the language corresponding to the code en, the image {file: $filename}").build(),
            APIMessage(Role.USER.customName, "Hello {file: $filename}"),
            APIMessage(Role.ASSISTANT.customName, "Hi there!"),)
        assertEquals(expectedMessages, apiMessages)
    }

    @Test
    fun `toAPIMessages should use en as default code`() {
        // Arrange
        val conversation = Conversation(
            messages = listOf(Message(Role.USER, "Hello"), Message(Role.ASSISTANT, "Hi there!")),
            filename = filename
        )

        // Act
        val apiMessages = conversation.toAPIMessages("")

        // Assert
        val expectedMessages = listOf(
            ApiMessageBuilder().withRole(Role.USER.customName)
                .withContent("Analyze using the language corresponding to the code en, the image {file: $filename}")
                .build(),
            APIMessage(Role.USER.customName, "Hello {file: $filename}"),
            APIMessage(Role.ASSISTANT.customName, "Hi there!"),
        )
        assertEquals(expectedMessages, apiMessages)
    }

    @Test
    fun `toAPIMessages should use the correct language code`() {
        // Arrange
        val conversation = Conversation(
            messages = listOf(Message(Role.USER, "Hello"), Message(Role.ASSISTANT, "Hi there!")),
            filename = filename
        )

        // Act
        val apiMessages = conversation.toAPIMessages("es")

        // Assert
        val expectedMessages = listOf(
            ApiMessageBuilder().withRole(Role.USER.customName)
                .withContent("Analyze using the language corresponding to the code es, the image {file: $filename}")
                .build(),
            APIMessage(Role.USER.customName, "Hello {file: $filename}"),
            APIMessage(Role.ASSISTANT.customName, "Hi there!"),
        )
        assertEquals(expectedMessages, apiMessages)
    }
}