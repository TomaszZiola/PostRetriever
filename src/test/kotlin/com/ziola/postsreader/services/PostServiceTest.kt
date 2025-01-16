package com.ziola.postsreader.services

import com.github.tomakehurst.wiremock.client.WireMock
import com.ziola.postsreader.utils.BaseUnitTest
import com.ziola.postsreader.utils.TestUtils.subscribe
import io.mockk.verifySequence
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PostServiceTest : BaseUnitTest() {
    @Test
    fun `PostService#retrievePosts should return ByteArrayResource`() {
        // when
        val result = subscribe(serviceImpl.retrievePosts(numberOfPost))

        // then
        assertThat(result).isEqualTo(byteArrayResource)

        verifySequence {
            client.getPosts()
            client.getComments(numberOfPost)
            objectMapper.writeValueAsString(commentList)
        }
    }

    @Test
    fun contextLoads() {
        println(WireMock.listAllStubMappings())
    }
}
