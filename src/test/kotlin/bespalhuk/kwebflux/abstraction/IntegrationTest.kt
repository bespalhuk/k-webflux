package bespalhuk.kwebflux.abstraction

import bespalhuk.kwebflux.config.TestcontainersConfiguration
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.github.tomakehurst.wiremock.client.WireMock
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.net.URLEncoder

@ActiveProfiles("test")
@AutoConfigureWebClient
@AutoConfigureWebTestClient(timeout = "30000")
@AutoConfigureWireMock(port = 0)
@SpringBootTest(classes = [TestcontainersConfiguration::class])
@ExtendWith(SpringExtension::class)
abstract class IntegrationTest {

    @BeforeEach
    fun beforeEach() {
        WireMock.resetAllRequests()
    }

    fun encodeParameter(parameter: String): String = URLEncoder.encode(parameter, "UTF-8")

    fun toJson(any: Any): String = ObjectMapper()
        .registerModule(kotlinModule())
        .registerModule(JavaTimeModule())
        .writeValueAsString(any)
}
