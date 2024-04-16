package com.ziola.postsreader.models

import com.ziola.postsreader.constants.Constants.ATTACHMENT_FILENAME
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.MediaType.APPLICATION_OCTET_STREAM
import org.springframework.http.ResponseEntity

internal object ResponseEntityModel {
    fun basic(): ResponseEntity<ByteArrayResource> {
        return ResponseEntity.ok()
            .header(CONTENT_DISPOSITION, ATTACHMENT_FILENAME)
            .contentType(APPLICATION_OCTET_STREAM)
            .body(ByteArrayResource(PostDtoModel.bytes()))
    }
}
