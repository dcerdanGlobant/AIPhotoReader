@file:OptIn(ExperimentalCoroutinesApi::class)

package com.kmpai.photoreader.feature.picker.ui.screens.home.views

import androidx.compose.ui.graphics.ImageBitmap
import app.cash.turbine.test
import com.kmpai.photoreader.core.ui.utils.ImageUriProviderSingleton
import com.kmpai.photoreader.feature.picker.domain.model.Conversation
import com.kmpai.photoreader.feature.picker.domain.model.Message
import com.kmpai.photoreader.feature.picker.domain.model.RequestedPicture
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
import dev.mokkery.every
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
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should emit SharedPicture state if imageUri is not empty`() = runTest {
        //Given
        ImageUriProviderSingleton.provider.setImageUri("MyUriTest")
        //When
        viewModel = PickerViewModel(getPictureDescription, sendConversation)

        //Then
        viewModel.homeState.test {
            val state = awaitItem()
            assertEquals(expected = PickerHomeState.SharedPicture, actual = state)
            cancelAndConsumeRemainingEvents()
        }
        verifySuspend(VerifyMode.exactly(0)) { getPictureDescription.invoke(any(), any()) } //Not rule
        verifySuspend(VerifyMode.exactly(0)) { sendConversation.invoke(any()) } //Not rule*/
    }

    @Test
    fun `sendAnotherMessage should emit three states`() = runTest {
        //Given
        viewModel = PickerViewModel(getPictureDescription, sendConversation)
        //When
        //viewModel.getPictureData(RequestedPicture(byteArray = ByteArray(1), bitmap = ImageBitmap(100,100)))
        viewModel.sendAnotherMessage("Example")

        //Then
        viewModel.chatState.test {
           val state = awaitItem()
            assertEquals(expected = ChatState(isLoading = true), actual = state)
        }

        verifySuspend(VerifyMode.exactly(1)) { getPictureDescription.invoke(any(), any()) } //Not rule
        verifySuspend(VerifyMode.exactly(0)) { sendConversation.invoke(any()) } //Not rule*/
    }



    //region Emit functions
    @Test
    fun `launchGallery function emit launchGallery state`() = runTest {
        //Given
        viewModel = PickerViewModel(getPictureDescription, sendConversation)        //When
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
        //Given
        viewModel = PickerViewModel(getPictureDescription, sendConversation)        //When
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
        //Given
        viewModel = PickerViewModel(getPictureDescription, sendConversation)
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
        //Given
        viewModel = PickerViewModel(getPictureDescription, sendConversation)//When
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
        //Given
        viewModel = PickerViewModel(getPictureDescription, sendConversation)//When
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
        //Given
        viewModel = PickerViewModel(getPictureDescription, sendConversation)//When
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