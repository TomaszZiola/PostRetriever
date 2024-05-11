package com.ziola.postsreader.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.ziola.postsreader.clients.PlaceholderClient
import com.ziola.postsreader.dtos.Comment
import com.ziola.postsreader.dtos.Post
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

@Service
class PostService(
    val client: PlaceholderClient,
    val objectMapper: ObjectMapper,
) {
    private val logger = KotlinLogging.logger {}

    fun retrievePosts(numberOfPost: Int): Mono<ByteArrayResource> {
        return client.getPosts()
            .flatMap { getComments(it) }
            .map { groupComments(it) }
            .map { createStreams(it) }
    }

    private fun createStreams(groupedComments: Map<String, List<Comment>>): ByteArrayResource {
        ByteArrayOutputStream().use { byteArrayOutputStream ->
            ZipOutputStream(byteArrayOutputStream).use {
                    zipOutputStream ->
                groupedComments.map { writePostToZip(it.key, it.value, zipOutputStream) }
            }
            return ByteArrayResource(byteArrayOutputStream.toByteArray())
        }
    }

    private fun groupComments(comments: List<Comment>): Map<String, List<Comment>> {
        return comments.groupBy { it.email.substringAfter("@") }
    }

    private fun getComments(posts: List<Post>): Mono<List<Comment>> {
        val monoList = posts.take(5).map { client.getComments(it.id) }
        return Flux.fromIterable(monoList)
            .flatMap { it }
            .flatMapIterable { it }
            .collectList()
    }

    private fun writePostToZip(
        domain: String,
        comments: List<Comment>,
        zipOutputStream: ZipOutputStream,
    ) {
        logger.debug { "Writing Post to ZIP: $domain" }
        val postAsBytes = objectMapper.writeValueAsString(comments).encodeToByteArray()
        val fileName = "$domain.json"
        zipOutputStream.putNextEntry(ZipEntry(fileName))
        zipOutputStream.write(postAsBytes)
        zipOutputStream.closeEntry()
        logger.debug { "Wrote Post to ZIP" }
    }
}
