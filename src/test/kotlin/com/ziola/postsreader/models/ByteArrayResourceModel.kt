package com.ziola.postsreader.models

import com.fasterxml.jackson.module.kotlin.jsonMapper
import org.springframework.core.io.ByteArrayResource
import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object ByteArrayResourceModel {
    private val objectMapper = jsonMapper {}

    fun basic(): ByteArrayResource {
        ByteArrayOutputStream().use { byteArrayOutputStream ->
            ZipOutputStream(byteArrayOutputStream).use { zipOutputStream ->
                val postAsBytes = objectMapper.writeValueAsString(PostDtoModel.basic()).toByteArray()
                val fileName = "${PostDtoModel.basic().id}.json"
                zipOutputStream.putNextEntry(ZipEntry(fileName))
                zipOutputStream.write(postAsBytes)
                zipOutputStream.closeEntry()
            }
            return ByteArrayResource(byteArrayOutputStream.toByteArray())
        }
    }
}
