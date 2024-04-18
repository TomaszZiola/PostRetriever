package com.ziola.postsreader.integrations

import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.lectra.koson.obj
import com.ziola.postsreader.constants.Constants.ATTACHMENT_FILENAME
import com.ziola.postsreader.constants.Constants.BODY
import com.ziola.postsreader.constants.Constants.ID
import com.ziola.postsreader.constants.Constants.TITLE
import com.ziola.postsreader.constants.Constants.USER_ID
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
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

    @Test
    fun `GET#should return ResponseEntity with zip body containing five posts`() {
        Given {
            port(port)
        } When {
            get("/post/export")
        } Then {
            statusCode(200)
            header(CONTENT_TYPE, APPLICATION_OCTET_STREAM_VALUE)
            header(CONTENT_DISPOSITION, ATTACHMENT_FILENAME)
        } Extract {
            val expected =
                obj {
                    USER_ID to 1
                    ID to 1
                    TITLE to "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"
                    BODY to "quia et suscipit suscipit recusandae consequuntur expedita et cum reprehenderit molestiae ut ut quas " +
                        "totam nostrum rerum est autem sunt rem eveniet architecto"
                }.toString() +
                    obj {
                        USER_ID to 1
                        ID to 2
                        TITLE to "qui est esse"
                        BODY to "est rerum tempore vitae sequi sint nihil reprehenderit dolor beatae ea dolores neque fugiat blanditiis " +
                            "voluptate porro vel nihil molestiae ut reiciendis qui aperiam non debitis possimus qui neque nisi nulla"
                    } +
                    obj {
                        USER_ID to 1
                        ID to 3
                        TITLE to "ea molestias quasi exercitationem repellat qui ipsa sit aut"
                        BODY to "et iusto sed quo iure voluptatem occaecati omnis eligendi aut ad voluptatem doloribus vel accusantium " +
                            "quis pariatur molestiae porro eius odio et labore et velit aut"
                    } +
                    obj {
                        USER_ID to 1
                        ID to 4
                        TITLE to "eum et est occaecati"
                        BODY to "ullam et saepe reiciendis voluptatem adipisci sit amet autem assumenda provident rerum culpa quis hic " +
                            "commodi nesciunt rem tenetur doloremque ipsam iure quis sunt voluptatem rerum illo velit"
                    } +
                    obj {
                        USER_ID to 1
                        ID to 5
                        TITLE to "nesciunt quas odio"
                        BODY to "repudiandae veniam quaerat sunt sed alias aut fugiat sit autem sed est voluptatem omnis possimus esse " +
                            "voluptatibus quis est aut tenetur dolor neque"
                    }
            val result = extractPostsFromZip(body().asByteArray())
            assertThat(result).isEqualTo(expected)
        }
    }

    fun extractPostsFromZip(zipBytes: ByteArray): String {
        return ZipInputStream(zipBytes.inputStream()).use { zipInput ->
            val stringBuilder = StringBuilder()

            generateSequence { zipInput.nextEntry }
                .forEach { _ ->
                    zipInput.readBytes().decodeToString().let { content ->
                        stringBuilder.append(content)
                    }
                }
            stringBuilder.toString()
        }
    }
}
