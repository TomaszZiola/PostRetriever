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

    fun list(): List<PostDto> {
        val first =
            PostDto(
                userId = 1,
                id = 1,
                title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                body =
                    "quia et suscipit suscipit recusandae consequuntur expedita et cum reprehenderit molestiae ut ut quas totam nostrum rerum est " +
                        "autem sunt rem eveniet architecto",
            )
        val second =
            PostDto(
                userId = 1,
                id = 2,
                title = "qui est esse",
                body =
                    "est rerum tempore vitae sequi sint nihil reprehenderit dolor beatae ea dolores neque fugiat blanditiis " +
                        "voluptate porro vel nihil molestiae ut reiciendis qui aperiam non debitis possimus qui neque nisi nulla",
            )
        val third =
            PostDto(
                userId = 1,
                id = 3,
                title = "ea molestias quasi exercitationem repellat qui ipsa sit aut",
                body =
                    "et iusto sed quo iure voluptatem occaecati omnis eligendi aut ad voluptatem doloribus vel accusantium quis pariatur molestiae " +
                        "porro eius odio et labore et velit aut",
            )
        val fourth =
            PostDto(
                userId = 1,
                id = 4,
                title = "eum et est occaecati",
                body =
                    "ullam et saepe reiciendis voluptatem adipisci sit amet autem assumenda provident rerum culpa quis hic commodi nesciunt rem " +
                        "tenetur doloremque ipsam iure quis sunt voluptatem rerum illo velit",
            )
        val fifth =
            PostDto(
                userId = 1,
                id = 5,
                title = "nesciunt quas odio",
                body =
                    "repudiandae veniam quaerat sunt sed alias aut fugiat sit autem sed est voluptatem omnis possimus esse voluptatibus quis est " +
                        "aut tenetur dolor neque",
            )
        return listOf(first, second, third, fourth, fifth)
    }
}
