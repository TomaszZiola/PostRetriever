package com.ziola.postsreader.clients

import com.ziola.postsreader.dtos.PostDto
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class PlaceholderClient(private val client: RestClient) {
    fun getPosts(): List<PostDto> {
        return client.get()
            .uri("/posts")
            .retrieve()
            .body(Array<PostDto>::class.java)
            ?.toList() ?: emptyList()
    }
}
