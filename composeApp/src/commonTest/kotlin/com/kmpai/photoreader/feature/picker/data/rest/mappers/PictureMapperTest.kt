package com.kmpai.photoreader.feature.picker.data.rest.mappers

import com.kmpai.photoreader.feature.picker.data.rest.model.ChoiceBuilder
import com.kmpai.photoreader.feature.picker.data.rest.model.ImageResponse
import com.kmpai.photoreader.feature.picker.data.rest.model.ImageResponseBuilder
import com.kmpai.photoreader.feature.picker.data.rest.model.MessageBuilder
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PictureMapperTest {
    //No there isn´t null options
    @Test
    fun `should convert ImageResponse to Picture successfully`() {
        val imageResponse = ImageResponseBuilder().build()
        val picture = imageResponse.toPictureModel()

        assertEquals(
            expected = "ContentBuilder",
            actual = picture.contentDescription
        )
    }

    @Test
    fun `should throw exception when choices list is empty`() {
        val imageResponse = ImageResponseBuilder().choices(emptyList()).build()
        val picture = imageResponse.toPictureModel()
        assertNull(picture.contentDescription)
    }
}