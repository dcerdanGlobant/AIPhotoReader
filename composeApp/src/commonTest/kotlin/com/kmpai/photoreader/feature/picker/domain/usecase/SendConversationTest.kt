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

class SendConversationTest {

    @Test
    fun `SendConversation returns a conversation`() = runBlocking {
        val repository = mock<PickerRepository>(MockMode.autofill)
        val conversation = Conversation(messages = emptyList(), filename = "Example")
        everySuspend { repository.sendConversation(any()) } returns Result.success(
            conversation
        )

        val sendConversation =
            SendConversation(repository).invoke(conversation)

        assertTrue { (sendConversation == Result.success(conversation)) }
        verifySuspend(atMost(1)) { //Call expected
            repository.sendConversation(any())
        }
    }
}