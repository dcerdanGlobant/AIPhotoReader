package com.kmpai.photoreader.feature.picker.usecase

import com.kmpai.photoreader.feature.picker.domain.model.Picture
import com.kmpai.photoreader.feature.picker.domain.repository.PickerRepository
import com.kmpai.photoreader.feature.picker.domain.usecase.GetPictureDescription
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertTrue


class GetPictureDescriptionTest {


    @Test
    fun `returns a picture`() = runBlocking {

        val repository = mock<PickerRepository>(MockMode.autofill)
        everySuspend { repository.getPictureDescription(any(),any())} returns Result.success(Picture(contentDescription = "Example"))

        val getPictureDescriptionResult = GetPictureDescription(repository).invoke(image = ByteArray(2), extension = "")

        assertTrue { (getPictureDescriptionResult == Result.success(Picture(contentDescription = "Example"))) }
        verifySuspend {
            repository.getPictureDescription(any(),any())
        }
    }
}