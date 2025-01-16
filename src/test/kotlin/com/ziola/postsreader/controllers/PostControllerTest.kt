package com.ziola.postsreader.controllers

import com.ziola.postsreader.utils.BaseUnitTest
import com.ziola.postsreader.utils.TestUtils.subscribe
import io.mockk.verifySequence
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PostControllerTest : BaseUnitTest() {
    @Test
    fun `PostController#getPosts should return ResponseEntity`() {
        // when
        val result = subscribe(controllerImpl.getPosts(numberOfPost))

        // then
        assertThat(result).isEqualTo(responseEntity)

        verifySequence {
            service.retrievePosts(numberOfPost)
        }
    }
}
