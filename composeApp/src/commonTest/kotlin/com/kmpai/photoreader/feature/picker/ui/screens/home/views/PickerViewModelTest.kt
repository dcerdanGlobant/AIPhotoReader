@file:OptIn(ExperimentalCoroutinesApi::class)

package com.kmpai.photoreader.feature.picker.ui.screens.home.views

import app.cash.turbine.test
import com.kmpai.photoreader.core.ui.utils.ImageUriProviderSingleton
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import com.kmpai.photoreader.feature.picker.domain.model.Message
import com.kmpai.photoreader.feature.picker.domain.model.Role
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository
import com.kmpai.photoreader.feature.picker.domain.usecase.GetPictureDescription
import com.kmpai.photoreader.feature.picker.domain.usecase.SendConversation
import com.kmpai.photoreader.feature.picker.ui.screens.chat.ChatState
import com.kmpai.photoreader.feature.picker.ui.screens.home.PickerHomeState
import com.kmpai.photoreader.feature.picker.ui.screens.home.PickerViewModel
import dev.mokkery.MockMode
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

    private val repository = mock<PickerRepository>(MockMode.autofill)

    @BeforeTest
    fun setUp() {
        //Given
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
    fun `resendLastMessage emit ChatState and update the older conversation`() = runTest {
        //Given
        //everySuspend { viewModel.chatState.value } returns ChatState(conversation = Conversation(messages = listOf(Message(role = Role.USER, content = "LastMessage")), filename = "fileName.jpg"))

        viewModel.resendLastMessage()

        //Then
        /*viewModel.chatState.test {
            val state = awaitItem()
            assertEquals(expected = Conversation(messages = listOf(Message(role = Role.USER, content = "LastMessage")), filename = "fileName.jpg"), actual = state.conversation)
            val stateUpdatedFromServer = awaitItem() //Mock is empty
            assertEquals(expected = Conversation(emptyList(),""), actual = stateUpdatedFromServer.conversation)
            cancelAndConsumeRemainingEvents()
        }
        verifySuspend(VerifyMode.exactly(0)) { getPictureDescription.invoke(any(), any()) } //Not rule
        verifySuspend(VerifyMode.exactly(1)) { sendConversation.invoke(any()) } //Not rule*/
    }



    //region Emit functions
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


    @Test
    fun `launchGallery function emit launchGallery state`() = runTest {
        //When
        viewModel.launchGallery()
        //Then
        viewModel.homeState.test {
            val state = awaitItem()
            assertEquals(expected = PickerHomeState.LaunchGalery, actual = state)
            cancelAndConsumeRemainingEvents()
        }
        verifySuspend(VerifyMode.exactly(0)) { getPictureDescription.invoke(any(), any()) } //Not rule
        verifySuspend(VerifyMode.exactly(5)) { sendConversation.invoke(any()) } //Not rule
    }

    @Test
    fun `launchCamera function emit launchCamera state`() = runTest {
        //When
        viewModel.launchCamera()
        //Then
        viewModel.homeState.test {
            val state = awaitItem()
            assertEquals(expected = PickerHomeState.LaunchCamera, actual = state)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `launchSettings function emit launchSettings state`() = runTest {
        //When
        viewModel.launchSettings()
        //Then
        viewModel.homeState.test {
            val state = awaitItem()
            assertEquals(expected = PickerHomeState.LaunchSettings, actual = state)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `showRationaleDialog function emit showRationaleDialog state`() = runTest {
        //When
        viewModel.showRationaleDialog()
        //Then
        viewModel.homeState.test {
            val state = awaitItem()
            assertEquals(expected = PickerHomeState.ShowRationaleDialog, actual = state)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `showImageSourceOptionDialog function emit showImageSourceOptionDialog state`() = runTest {
        //When
        viewModel.showImageSourceOptionDialog()
        //Then
        viewModel.homeState.test {
            val state = awaitItem()
            assertEquals(expected = PickerHomeState.ImageSourceOptionDialog, actual = state)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `showPickImage function emit showPickImage state`() = runTest {
        //When
        viewModel.showPickImage()
        //Then
        viewModel.homeState.test {
            val state = awaitItem()
            assertEquals(expected = PickerHomeState.PickPicture, actual = state)
            cancelAndConsumeRemainingEvents()
        }
    }
    //endregion
}