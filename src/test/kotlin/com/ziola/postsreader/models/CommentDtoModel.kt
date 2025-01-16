package com.ziola.postsreader.models

import com.ziola.postsreader.dtos.Comment

internal object CommentDtoModel {
    fun basic(): Comment {
        return Comment(
            postId = 1,
            id = 1,
            name = "John Smith",
            email = "john.smith@gmail.com",
            body = "Hello John Smith",
        )
    }
}
