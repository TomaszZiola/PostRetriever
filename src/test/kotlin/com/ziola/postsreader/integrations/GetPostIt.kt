package com.ziola.postsreader.integrations

import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.lectra.koson.arr
import com.lectra.koson.obj
import com.ziola.postsreader.constants.Constants.ATTACHMENT_FILENAME
import com.ziola.postsreader.constants.Constants.BODY
import com.ziola.postsreader.constants.Constants.EMAIL
import com.ziola.postsreader.constants.Constants.ID
import com.ziola.postsreader.constants.Constants.NAME
import com.ziola.postsreader.constants.Constants.POST_ID
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
internal class GetPostIt {
    @LocalServerPort
    var port: Int = 0

    @Test
    fun `GET#should return ResponseEntity with zip body containing five posts`() {
        Given {
            port(port)
        } When {
            get("/post/export/1")
        } Then {
            statusCode(200)
            header(CONTENT_TYPE, APPLICATION_OCTET_STREAM_VALUE)
            header(CONTENT_DISPOSITION, ATTACHMENT_FILENAME)
        } Extract {
            val expected =
                arr[
                    obj {
                        POST_ID to 1
                        ID to 1
                        NAME to "id labore ex et quam laborum"
                        EMAIL to "Eliseo@gardner.biz"
                        BODY to "laudantium enim quasi est quidem magnam voluptate ipsam eostempora quo necessitatibusdolor quam autem " +
                            "quasireiciendis et nam sapiente accusantium"
                    },
                ].toString() +
                    arr[
                        obj {
                            POST_ID to 1
                            ID to 2
                            NAME to "quo vero reiciendis velit similique earum"
                            EMAIL to "Jayne_Kuhic@sydney.com"
                            BODY to "est natus enim nihil est dolore omnis voluptatem numquamet omnis occaecati quod ullam atvoluptatem error " +
                                "expedita pariaturnihil sint nostrum voluptatem reiciendis et"
                        },
                    ] +
                    arr[
                        obj {
                            POST_ID to 1
                            ID to 3
                            NAME to "odio adipisci rerum aut animi"
                            EMAIL to "Nikita@garfield.biz"
                            BODY to "quia molestiae reprehenderit quasi aspernaturaut expedita occaecati aliquam eveniet laudantiumomnis quibusdam " +
                                "delectus saepe quia accusamus maiores nam estcum et ducimus et vero voluptates excepturi deleniti ratione"
                        },
                    ]
            val result = extractPostsFromZip(body().asByteArray())
            println(result)
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
