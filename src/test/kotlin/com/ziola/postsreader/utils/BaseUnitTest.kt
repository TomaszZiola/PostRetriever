package com.ziola.postsreader.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.ziola.postsreader.clients.PlaceholderClient
import com.ziola.postsreader.controllers.PostController
import com.ziola.postsreader.dtos.Post
import com.ziola.postsreader.models.ByteArrayResourceModel
import com.ziola.postsreader.models.PostDtoModel
import com.ziola.postsreader.models.ResponseEntityModel
import com.ziola.postsreader.services.PostService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.ResponseEntity

abstract class BaseUnitTest {
    private val objectMapperImpl = jsonMapper {}

    var objectMapper = mockk<ObjectMapper>()
    var client = mockk<PlaceholderClient>()
    var service = mockk<PostService>()

    protected lateinit var byteArrayResource: ByteArrayResource
    protected lateinit var postDto: Post
    private lateinit var postDtoBytes: ByteArray
    private lateinit var postDtoString: String
    protected lateinit var responseEntity: ResponseEntity<ByteArrayResource>

    protected lateinit var controllerImpl: PostController
    protected lateinit var serviceImpl: PostService

    @BeforeEach
    fun mockResponses() {
        byteArrayResource = ByteArrayResourceModel.basic()
        postDto = PostDtoModel.basic()
        postDtoBytes = PostDtoModel.bytes()
        postDtoString = objectMapperImpl.writeValueAsString(postDto)
        responseEntity = ResponseEntityModel.basic()

        controllerImpl = PostController(service)
        serviceImpl = PostService(client, objectMapper)

        every { client.getPosts() } returns listOf(postDto)
        every { objectMapper.writeValueAsString(postDto) } returns postDtoString
//        every { service.retrievePosts(numberOfPost) } returns ByteArrayResource(postDtoBytes)
    }
}
