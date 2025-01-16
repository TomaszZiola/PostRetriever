package com.ziola.postsreader.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.ziola.postsreader.clients.PlaceholderClient
import com.ziola.postsreader.controllers.PostController
import com.ziola.postsreader.dtos.Comment
import com.ziola.postsreader.dtos.Post
import com.ziola.postsreader.models.ByteArrayResourceModel
import com.ziola.postsreader.models.CommentDtoModel
import com.ziola.postsreader.models.PostDtoModel
import com.ziola.postsreader.models.ResponseEntityModel
import com.ziola.postsreader.services.PostService
import com.ziola.postsreader.utils.TestUtils.mono
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.ResponseEntity

abstract class BaseUnitTest {
    private val objectMapperImpl = jsonMapper {}

    val numberOfPost = 1

    var objectMapper = mockk<ObjectMapper>()
    var client = mockk<PlaceholderClient>()
    var service = mockk<PostService>()

    protected lateinit var byteArrayResource: ByteArrayResource
    protected lateinit var commentDto: Comment
    protected lateinit var commentList: List<Comment>
    private lateinit var commentDtoString: String
    protected lateinit var postDto: Post
    private lateinit var postDtoBytes: ByteArray
    private lateinit var postDtoString: String
    protected lateinit var responseEntity: ResponseEntity<ByteArrayResource>

    protected lateinit var controllerImpl: PostController
    protected lateinit var serviceImpl: PostService

    @BeforeEach
    fun mockResponses() {
        byteArrayResource = ByteArrayResourceModel.basic()
        commentDto = CommentDtoModel.basic()
        commentList = listOf(commentDto)
        commentDtoString = objectMapperImpl.writeValueAsString(commentList)
        postDto = PostDtoModel.basic()
        postDtoBytes = PostDtoModel.bytes()
        postDtoString = objectMapperImpl.writeValueAsString(postDto)
        responseEntity = ResponseEntityModel.basic()

        controllerImpl = PostController(service)
        serviceImpl = PostService(client, objectMapper)

        every { client.getComments(postDto.id) } returns mono(commentList)
        every { client.getPosts() } returns mono(listOf(postDto))
        every { objectMapper.writeValueAsString(commentList) } returns commentDtoString
        every { objectMapper.writeValueAsString(postDto) } returns postDtoString
        every { service.retrievePosts(numberOfPost) } returns mono(ByteArrayResource(postDtoBytes))
    }
}
