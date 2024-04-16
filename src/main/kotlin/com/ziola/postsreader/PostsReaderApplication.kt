package com.ziola.postsreader

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PostsReaderApplication

fun main(args: Array<String>) {
    runApplication<PostsReaderApplication>(*args)
}
