package com.kmpai.photoreader.feature.picker.data.repository


import app.cash.turbine.test
import com.kmpai.photoreader.feature.picker.data.datasource.PickerDatasource
import com.kmpai.photoreader.feature.picker.domain.model.CommonResult
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode.Companion.atMost
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PickerRepositoryImplTest {

    @Test
    fun `sendImageAndStartConversation should return successful result with conversation`() = runBlocking {
        val datasource = mock<PickerDatasource>()
        val repository = PickerRepositoryImpl(datasource)
        val resultConversation = CommonResult.Success(Conversation(messages = emptyList(),""))

        everySuspend { datasource.sendImageAndStartConversation(any(), any()) } returns flowOf(resultConversation)

        val flowResult = repository.sendImageAndStartConversation(extension = "jpg", imageByteArray = ByteArray(1))

        flowResult.test {
            val value = awaitItem()
            assertEquals(resultConversation, value)
            awaitComplete()
        }
        verifySuspend (atMost(1)) { //Call expected
            datasource.sendImageAndStartConversation(any(), any())
        }
    }

    @Test
    fun `sendImageAndStartConversation should return failure result when datasource fails`() = runBlocking {
        val datasource = mock<PickerDatasource>()
        val repository = PickerRepositoryImpl(datasource)
        val conversation = CommonResult.Failure(Exception("Error fetching description"))

        everySuspend { datasource.sendImageAndStartConversation(any(), any()) } returns flowOf(CommonResult.Failure(Exception("Error fetching description")))

        val flowResult = repository.sendImageAndStartConversation(extension = "jpg", imageByteArray = ByteArray(1))

        flowResult.test {
            val value = awaitItem()
            assertTrue(value is CommonResult.Failure)
            assertEquals("Error fetching description", value.exception.message)
            awaitComplete()
        }
        verifySuspend(atMost(1)) { //Call expected
            datasource.sendImageAndStartConversation(any(), any())
        }
    }

    @Test
    fun `sendConversation should return successful result with conversation`() = runBlocking {
        val datasource = mock<PickerDatasource>()
        val repository = PickerRepositoryImpl(datasource)
        val resultConversation = CommonResult.Success(Conversation(messages = emptyList(),""))

        everySuspend { datasource.sendConversation(any()) } returns flowOf(resultConversation)

        val flowResult = repository.sendConversation(Conversation(messages = emptyList(),""))
        flowResult.test {
            val value = awaitItem()
            assertTrue(value is CommonResult.Success)
            assertEquals(resultConversation, value)
            awaitComplete()
        }

        verifySuspend (atMost(1)) { //Call expected
            datasource.sendConversation(any())
        }
    }
}