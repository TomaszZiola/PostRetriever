package com.ziola.postsreader.utils

import reactor.core.publisher.Mono

object TestUtils {
    fun <T : Any> mono(source: T): Mono<T> {
        return Mono.just<T>(source)
    }

    fun <T> subscribe(source: Mono<T>): T {
        return source.block() ?: throw NoSuchElementException("No value present")
    }
}
