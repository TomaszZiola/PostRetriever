package com.ziola.postsreader.models

import com.fasterxml.jackson.module.kotlin.jsonMapper
import org.springframework.core.io.ByteArrayResource
import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object ByteArrayResourceModel {
    private val objectMapper = jsonMapper {}

    fun basic(): ByteArrayResource {
        val emailDomain = CommentDtoModel.basic().email.substringAfter("@")
        ByteArrayOutputStream().use { byteArrayOutputStream ->
            ZipOutputStream(byteArrayOutputStream).use { zipOutputStream ->
                val commentsAsBytes = objectMapper.writeValueAsString(listOf(CommentDtoModel.basic())).toByteArray()
                val fileName = "$emailDomain.json"
                zipOutputStream.putNextEntry(ZipEntry(fileName))
                zipOutputStream.write(commentsAsBytes)
                zipOutputStream.closeEntry()
            }
            return ByteArrayResource(byteArrayOutputStream.toByteArray())
        }
    }
}
