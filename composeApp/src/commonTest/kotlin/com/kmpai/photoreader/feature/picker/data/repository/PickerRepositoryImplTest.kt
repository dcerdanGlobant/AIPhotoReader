package com.kmpai.photoreader.feature.picker.data.repository


import com.kmpai.photoreader.feature.picker.data.datasource.PickerDatasource
import com.kmpai.photoreader.feature.picker.domain.model.Picture
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PickerRepositoryImplTest {

    @Test
    fun `should return successful result with picture description`() = runBlocking {
        val datasource = mock<PickerDatasource>()
        val repository = PickerRepositoryImpl(datasource)
        val imageBytes = byteArrayOf(1, 2, 3)
        val extension = "jpg"
        val expectedPicture = Picture("Description")

        everySuspend { datasource.getPictureDescription(extension, imageBytes) } returns Result.success(expectedPicture)

        val result = repository.getPictureDescription(extension, imageBytes)

        assertTrue(result.isSuccess)
        assertEquals(expectedPicture, result.getOrNull())
    }

    @Test
    fun `should return failure result when datasource fails`() = runBlocking {
        val datasource = mock<PickerDatasource>()
        val repository = PickerRepositoryImpl(datasource)
        val imageBytes = byteArrayOf(1, 2, 3)
        val extension = "jpg"

        everySuspend { datasource.getPictureDescription(extension, imageBytes) } returns Result.failure(Exception("Error fetching description"))

        val result = repository.getPictureDescription(extension, imageBytes)

        assertTrue(result.isFailure)
        assertEquals("Error fetching description", result.exceptionOrNull()?.message)
    }

    @Test
    fun `should handle exceptions from datasource`() = runBlocking {
        val datasource = mock<PickerDatasource>()
        val repository = PickerRepositoryImpl(datasource)
        val imageBytes = byteArrayOf(1, 2, 3)
        val extension = "jpg"

        everySuspend { datasource.getPictureDescription(extension, imageBytes) } throws RuntimeException("Unexpected error")

        val result = repository.getPictureDescription(extension, imageBytes)

        assertTrue(result.isFailure)
    }
}