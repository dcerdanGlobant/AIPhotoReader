package com.kmpai.photoreader.feature.picker.data.datasource

import com.kmpai.photoreader.feature.picker.data.rest.RestApiInterface
import com.kmpai.photoreader.feature.picker.data.rest.model.ApiResponseBuilder
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import com.kmpai.photoreader.feature.picker.domain.model.Message
import com.kmpai.photoreader.feature.picker.domain.model.Role
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode.Companion.atMost
import dev.mokkery.verifySuspend
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PickerAPIDatasourceImplTest {

    @Test
    fun `sendImageAndStartConversation should successfully get a conversation`() = runBlocking {
        val restApi = mock<RestApiInterface>()
        val datasource = PickerAPIDatasourceImpl(restApi)
        everySuspend { restApi.uploadImage(image = any(), filename = any(), contentType = any()) } returns "uploaded_image.jpg"
        everySuspend { restApi.sendMessagesToAI(messages = any()) } returns ApiResponseBuilder().build()

        val result = datasource.sendImageAndStartConversation("jpg", ByteArray(1))
        assertTrue(result.isSuccess)
        assertEquals(expected =
            Result.success(
                Conversation(
                    messages = listOf(Message(role = Role.ASSISTANT, content = "ContentBuilder")),
                    filename = "uploaded_image.jpg"
                )
            ), actual = result
        )

        verifySuspend(atMost(1)) { //Call expected
            restApi.uploadImage(any(), any(),any())
            restApi.sendMessagesToAI(any())
        }
    }

    @Test
    fun `sendImageAndStartConversation should return failure when uploadImage fails`() = runBlocking {
        val restApi = mock<RestApiInterface>()
        val datasource = PickerAPIDatasourceImpl(restApi)
        // NOT MOCKED and go to Failure everySuspend { restApi.uploadImage(image = any(), filename = any(), contentType = any()) } returns "uploaded_image.jpg"

        val result = datasource.sendImageAndStartConversation("jpg", ByteArray(1))
        assertTrue(result.isFailure)

        verifySuspend(atMost(1)) { //Call expected
            restApi.uploadImage(any(), any(),any())
        }
        }

    @Test
    fun `sendConversation should successfully get a conversation`() = runBlocking {
        val restApi = mock<RestApiInterface>()
        val datasource = PickerAPIDatasourceImpl(restApi)
        everySuspend { restApi.sendMessagesToAI(messages = any()) } returns ApiResponseBuilder().build()

        val result = datasource.sendConversation(Conversation(emptyList(),"uploaded_image.jpg"))
        assertTrue(result.isSuccess)
        assertEquals(expected =
        Result.success(
            Conversation(
                messages = listOf(Message(role = Role.ASSISTANT, content = "ContentBuilder")), //Because ApiResponseBuilder().build()
                filename = "uploaded_image.jpg"
            )
        ), actual = result
        )

        verifySuspend(atMost(1)) { //Call expected
            restApi.sendMessagesToAI(any())
        }
    }

    @Test
    fun `sendConversation should return failure when call fails`() = runBlocking {
        val restApi = mock<RestApiInterface>()
        val datasource = PickerAPIDatasourceImpl(restApi)
        //NOT MOCKED and go to Failure everySuspend { restApi.sendMessagesToAI(messages = any()) } returns ApiResponseBuilder().build()

        val result = datasource.sendConversation(Conversation(emptyList(),"uploaded_image.jpg"))
        assertTrue(result.isFailure)

        verifySuspend(atMost(1)) { //Call expected
            restApi.sendMessagesToAI(any())
        }
    }
}