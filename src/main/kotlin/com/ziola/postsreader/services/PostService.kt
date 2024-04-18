package com.ziola.postsreader.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.ziola.postsreader.clients.PlaceholderClient
import com.ziola.postsreader.dtos.PostDto
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

@Service
class PostService(
    val client: PlaceholderClient,
    val objectMapper: ObjectMapper,
) {
    private val logger = KotlinLogging.logger {}

    fun retrievePosts(): ByteArrayResource {
        ByteArrayOutputStream().use { byteArrayOutputStream ->
            ZipOutputStream(byteArrayOutputStream).use { zipOutputStream ->
                logger.debug { "Retrieving Posts" }
                client.getPosts()
                    .also { logger.debug { "Retrieved Posts: $it" } }
                    .forEach { post ->
                        writePostToZip(post, zipOutputStream)
                    }
            }
            return ByteArrayResource(byteArrayOutputStream.toByteArray())
        }
    }

    private fun writePostToZip(
        postDto: PostDto,
        zipOutputStream: ZipOutputStream,
    ) {
        logger.debug { "Writing Post to ZIP: $postDto" }
        val postAsBytes = objectMapper.writeValueAsString(postDto).encodeToByteArray()
        val fileName = "${postDto.id}.json"
        zipOutputStream.putNextEntry(ZipEntry(fileName))
        zipOutputStream.write(postAsBytes)
        zipOutputStream.closeEntry()
        logger.debug { "Wrote Post to ZIP" }
    }
}
