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
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SendConversationTest {

    @Test
    fun `SendConversation returns a conversation`() = runBlocking {
        val repository = mock<PickerRepository>(MockMode.autofill)
        val conversation = Conversation(messages = emptyList(), filename = "Example")
        everySuspend { repository.sendConversation(any()) } returns flowOf(CommonResult.Success(
            conversation
        ))

        val sendConversation =
            SendConversation(repository).invoke(conversation)

        sendConversation.test {
            val value = awaitItem()
            assertEquals(expected = CommonResult.Success(conversation), actual = value )
            awaitComplete()
        }
        verifySuspend(atMost(1)) { //Call expected
            repository.sendConversation(any())
        }
    }
}