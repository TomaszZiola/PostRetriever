package com.ziola.postsreader.clients

import com.ziola.postsreader.dtos.Comment
import com.ziola.postsreader.dtos.Post
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class PlaceholderClient(private val client: WebClient) {
    fun getPosts(): Mono<List<Post>> {
        return client.get()
            .uri("/posts")
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<List<Post>>() {})
    }

    fun getComments(postId: Int): Mono<List<Comment>> {
        return client.get()
            .uri("/posts/$postId/comments")
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<List<Comment>>() {})
    }
}
