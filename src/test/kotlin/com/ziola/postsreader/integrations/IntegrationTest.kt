package com.ziola.postsreader.integrations

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.ziola.postsreader.constants.Constants.ATTACHMENT_FILENAME
import com.ziola.postsreader.dtos.PostDto
import com.ziola.postsreader.models.PostDtoModel
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE
import java.util.zip.ZipInputStream

@WireMockTest(httpPort = 8081)
@SpringBootTest(webEnvironment = RANDOM_PORT)
internal class IntegrationTest {
    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `GET#should return ResponseEntity with zip body containing five posts`() {
        val response =
            Given {
                port(port)
            } When {
                get("/post/export")
            } Then {
                statusCode(200)
                header(CONTENT_TYPE, APPLICATION_OCTET_STREAM_VALUE)
                header(CONTENT_DISPOSITION, ATTACHMENT_FILENAME)
            } Extract {
                response()
            }

        val posts = extractPostsFromZip(response.asByteArray())

        assertThat(posts).isEqualTo(PostDtoModel.list())
    }

    fun extractPostsFromZip(zipBytes: ByteArray): List<PostDto> {
        return ZipInputStream(zipBytes.inputStream()).use { zipInput ->
            generateSequence { zipInput.nextEntry }
                .map {
                    val entryContents = zipInput.readBytes().decodeToString()
                    objectMapper.readValue(entryContents, PostDto::class.java)
                }
                .toList()
        }
    }
}
