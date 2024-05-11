package com.ziola.postsreader.controllers

import com.ziola.postsreader.constants.Constants.ATTACHMENT_FILENAME
import com.ziola.postsreader.services.PostService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.MediaType.APPLICATION_OCTET_STREAM
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/post")
class PostController(
    val service: PostService,
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/export/{numberOfPost}")
    fun getPosts(
        @PathVariable numberOfPost: Int,
    ): Mono<ResponseEntity<ByteArrayResource>> {
        return service.retrievePosts(numberOfPost)
            .map {
                ResponseEntity.ok()
                    .header(CONTENT_DISPOSITION, ATTACHMENT_FILENAME)
                    .contentType(APPLICATION_OCTET_STREAM)
                    .body(it)
                    .also { logger.debug { "GET Posts result" } }
            }
    }
}
