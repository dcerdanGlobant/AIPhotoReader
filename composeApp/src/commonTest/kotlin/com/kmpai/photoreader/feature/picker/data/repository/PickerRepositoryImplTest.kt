package com.kmpai.photoreader.feature.picker.data.repository


import com.kmpai.photoreader.feature.picker.data.datasource.PickerDatasource
import com.kmpai.photoreader.feature.picker.domain.model.Picture
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
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
    fun `should return successful result with picture description`() = runBlocking {
        val datasource = mock<PickerDatasource>()
        val repository = PickerRepositoryImpl(datasource)
        val expectedPicture = Picture("Description")

        everySuspend { datasource.getPictureDescription(any(), any()) } returns Result.success(expectedPicture)

        val result = repository.getPictureDescription(extension = "jpg", imageByteArray = ByteArray(1))

        assertTrue(result.isSuccess)
        assertEquals(expectedPicture, result.getOrNull())
        verifySuspend (atMost(1)) { //Call expected
            datasource.getPictureDescription(any(), any())
        }
    }

    @Test
    fun `should return failure result when datasource fails`() = runBlocking {
        val datasource = mock<PickerDatasource>()
        val repository = PickerRepositoryImpl(datasource)

        everySuspend { datasource.getPictureDescription(any(), any()) } returns Result.failure(Exception("Error fetching description"))

        val result = repository.getPictureDescription(extension = "jpg", imageByteArray = ByteArray(1))

        assertTrue(result.isFailure)
        assertEquals("Error fetching description", result.exceptionOrNull()?.message)
        verifySuspend(atMost(1)) { //Call expected
            datasource.getPictureDescription(any(), any())
        }
    }
}