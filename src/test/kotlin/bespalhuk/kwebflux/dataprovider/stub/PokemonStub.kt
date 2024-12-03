package bespalhuk.kwebflux.dataprovider.stub

import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.http.HttpStatus

class PokemonStub {

    companion object {
        private const val POKEAPI: String = "/api"

        fun retrieve(number: Int, httpStatus: HttpStatus, body: String) {
            WireMock.stubFor(
                WireMock.get(
                    WireMock.urlPathEqualTo(
                        POKEAPI + "/pokemon/${number}"
                    ),
                ).willReturn(
                    WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(httpStatus.value())
                        .withBody(body)
                ),
            )
        }
    }
}
