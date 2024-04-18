package com.ziola.postsreader.models

import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.ziola.postsreader.dtos.PostDto

internal object PostDtoModel {
    private val objectMapper = jsonMapper {}

    fun basic(): PostDto {
        return PostDto(
            userId = 1,
            id = 1,
            title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
            body =
                "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum " +
                    "est autem sunt rem eveniet architecto",
        )
    }

    fun bytes(): ByteArray {
        return objectMapper.writeValueAsBytes(basic())
    }
}
