@file:OptIn(ExperimentalCoroutinesApi::class)

package com.kmpai.photoreader.feature.picker.ui.screens.home.views

import app.cash.turbine.test
import com.kmpai.photoreader.core.ui.utils.ImageUriProviderSingleton
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository
import com.kmpai.photoreader.feature.picker.domain.usecase.GetPictureDescription
import com.kmpai.photoreader.feature.picker.domain.usecase.SendConversation
import com.kmpai.photoreader.feature.picker.ui.screens.home.PickerHomeState
import com.kmpai.photoreader.feature.picker.ui.screens.home.PickerViewModel
import dev.mokkery.answering.calls
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PickerViewModelTest {

    private lateinit var getPictureDescription: GetPictureDescription
    private lateinit var sendConversation: SendConversation
    private lateinit var viewModel: PickerViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    private val repository =
        mock<PickerRepository> {
            everySuspend { sendImageAndStartConversation(any(),any()) } returns  Result.success(Conversation(
                emptyList(),""))
            everySuspend { sendConversation(any()) } returns  Result.success(Conversation(
                emptyList(),""))
        }

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getPictureDescription = GetPictureDescription(repository)
        sendConversation = SendConversation(repository)
        viewModel = PickerViewModel(getPictureDescription, sendConversation)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should emit SharedPicture state if imageUri is not empty`() = runTest {

        //everySuspend { ImageUriProviderSingleton.provider.imageUrl } returns "someUri"

        //Then after init
        viewModel.homeState.test {
            val state = awaitItem()
            assertEquals(expected = PickerHomeState.PickPicture, actual = state)
            cancelAndConsumeRemainingEvents()
        }
        verifySuspend(VerifyMode.exactly(0)) { getPictureDescription.invoke(any(), any()) } //Not rule
        verifySuspend(VerifyMode.exactly(1)) { sendConversation.invoke(any()) } //Not rule
    }
}