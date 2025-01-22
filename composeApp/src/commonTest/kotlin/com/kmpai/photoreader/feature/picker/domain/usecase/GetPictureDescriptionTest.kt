package com.kmpai.photoreader.feature.picker.domain.usecase

import app.cash.turbine.test
import com.kmpai.photoreader.feature.picker.domain.model.CommonResult
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode.Companion.atMost
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class GetPictureDescriptionTest {

    @Test
    fun `getPictureDescriptionResult returns a picture`() = runBlocking {
        val repository = mock<PickerRepository>(MockMode.autofill)
        val resultConversation = CommonResult.Success(
            Conversation(messages = emptyList(), filename = "Example")
        )
        everySuspend { repository.sendImageAndStartConversation(any(), any()) } returns flowOf(resultConversation)

        val flowResult =
            GetPictureDescription(repository).invoke(image = ByteArray(2), extension = "")
        flowResult.test {
            val value = awaitItem()
            assertTrue { (value == CommonResult.Success(Conversation(messages = emptyList(), filename = "Example"))) }
            awaitComplete()
        }
        verifySuspend(atMost(1)) { //Call expected
            repository.sendImageAndStartConversation(any(), any())
        }
    }

    @Test
    fun `getPictureDescriptionResult returns a failure`() = runBlocking {
        val repository = mock<PickerRepository>(MockMode.autofill)
        val resultConversation = CommonResult.Failure(
            Throwable(message = "Error Message for test")
        )
        everySuspend { repository.sendImageAndStartConversation(any(), any()) } returns flowOf(resultConversation)

        val flowResult =
            GetPictureDescription(repository).invoke(image = ByteArray(2), extension = "")

        flowResult.test {
            val value = awaitItem()
            assertTrue(value is CommonResult.Failure)
            assertTrue(value.exception.message != null && value.exception.message == "Error Message for test")
            awaitComplete()
        }
        verifySuspend(atMost(1)) { //Call expected
            repository.sendImageAndStartConversation(any(), any())
        }
    }
}