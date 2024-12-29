package com.kmpai.photoreader.feature.picker.domain.usecase

import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode.Companion.atMost
import dev.mokkery.verifySuspend
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue

class GetPictureDescriptionTest {

    @Test
    fun `getPictureDescriptionResult returns a picture`() = runBlocking {
        val repository = mock<PickerRepository>(MockMode.autofill)
        everySuspend { repository.sendImageAndStartConversation(any(), any()) } returns Result.success(
            Conversation(messages = emptyList(), filename = "Example")
        )

        val getPictureDescriptionResult =
            GetPictureDescription(repository).invoke(image = ByteArray(2), extension = "")

        assertTrue { (getPictureDescriptionResult == Result.success(Conversation(messages = emptyList(), filename = "Example"))) }
        verifySuspend(atMost(1)) { //Call expected
            repository.sendImageAndStartConversation(any(), any())
        }
    }

    @Test
    fun `getPictureDescriptionResult returns a failure`() = runBlocking {
        val repository = mock<PickerRepository>(MockMode.autofill)
        everySuspend { repository.sendImageAndStartConversation(any(), any()) } returns Result.failure(
            Throwable(message = "Error Message for test")
        )

        val getPictureDescriptionResult =
            GetPictureDescription(repository).invoke(image = ByteArray(2), extension = "")

        assertTrue(getPictureDescriptionResult.isFailure)
        getPictureDescriptionResult.onFailure {
            assertTrue(it.message != null && it.message == "Error Message for test")
        }
        verifySuspend(atMost(1)) { //Call expected
            repository.sendImageAndStartConversation(any(), any())
        }
    }
}