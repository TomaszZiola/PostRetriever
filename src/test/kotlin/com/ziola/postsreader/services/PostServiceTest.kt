package com.ziola.postsreader.services

import com.ziola.postsreader.utils.BaseUnitTest
import io.mockk.verifySequence
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PostServiceTest : BaseUnitTest() {
    @Test
    fun `PostService#retrievePosts should return ByteArrayResource`() {
        // when
        val result = serviceImpl.retrievePosts()

        // then
        assertThat(result).isEqualTo(byteArrayResource)

        verifySequence {
            client.getPosts()
            objectMapper.writeValueAsString(postDto)
        }
    }
}
