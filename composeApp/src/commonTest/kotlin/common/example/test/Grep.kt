package common.example.test

import com.kmpai.photoreader.core.grep
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
//TODO PABEL Remove this example file
class GrepTest {
    companion object {
        val sampleData = listOf(
            "123 abc",
            "abc 123",
            "123 ABC",
            "ABC 123"
        )
    }

    @Test
    fun shouldFindMatches() {
        val results = mutableListOf<String>()
        grep(sampleData, "[a-z]+") {
            results.add(it)
        }

        assertEquals(2, results.size)
        for (result in results) {
            assertContains(result, "abc")
        }
    }
}