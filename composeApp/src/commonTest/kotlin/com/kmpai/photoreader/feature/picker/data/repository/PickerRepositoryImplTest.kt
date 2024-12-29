package com.kmpai.photoreader.feature.picker.data.repository


import com.kmpai.photoreader.feature.picker.data.datasource.PickerDatasource
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
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

class PickerRepositoryImplTest {

    @Test
    fun `sendImageAndStartConversation should return successful result with conversation`() = runBlocking {
        val datasource = mock<PickerDatasource>()
        val repository = PickerRepositoryImpl(datasource)
        val resultConversation = Result.success(Conversation(messages = emptyList(),""))

        everySuspend { datasource.sendImageAndStartConversation(any(), any()) } returns resultConversation

        val result = repository.sendImageAndStartConversation(extension = "jpg", imageByteArray = ByteArray(1))

        assertTrue(result.isSuccess)
        assertEquals(resultConversation, result)
        verifySuspend (atMost(1)) { //Call expected
            datasource.sendImageAndStartConversation(any(), any())
        }
    }

    @Test
    fun `sendImageAndStartConversation should return failure result when datasource fails`() = runBlocking {
        val datasource = mock<PickerDatasource>()
        val repository = PickerRepositoryImpl(datasource)

        everySuspend { datasource.sendImageAndStartConversation(any(), any()) } returns Result.failure(Exception("Error fetching description"))

        val result = repository.sendImageAndStartConversation(extension = "jpg", imageByteArray = ByteArray(1))

        assertTrue(result.isFailure)
        assertEquals("Error fetching description", result.exceptionOrNull()?.message)
        verifySuspend(atMost(1)) { //Call expected
            datasource.sendImageAndStartConversation(any(), any())
        }
    }
}