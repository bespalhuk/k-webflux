package bespalhuk.kwebflux.config.client

import bespalhuk.kwebflux.config.client.exception.NotFoundClientException
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.reactive.function.client.ClientResponse
import reactor.core.publisher.Mono
import java.util.function.Function

sealed class HttpClientErrorCompanions {

    companion object {
        private const val EMPTY = ""

        fun notFound(message: String): Function<ClientResponse, Mono<out Throwable>> {
            return Function { clientResponse: ClientResponse ->
                clientResponse.bodyToMono(String::class.java)
                    .switchIfEmpty(Mono.just(EMPTY))
                    .flatMap {
                        Mono.error(
                            NotFoundClientException(message)
                        )
                    }
            }
        }

        fun toClientError(methodName: String): Function<ClientResponse, Mono<out Throwable>> {
            return Function { clientResponse: ClientResponse ->
                clientResponse.bodyToMono(String::class.java)
                    .switchIfEmpty(Mono.just(EMPTY))
                    .flatMap {
                        Mono.error(
                            HttpClientErrorException(
                                clientResponse.statusCode(),
                                "$methodName: it",
                            ),
                        )
                    }
            }
        }

        fun toServerError(methodName: String): Function<ClientResponse, Mono<out Throwable>> {
            return Function { clientResponse: ClientResponse ->
                clientResponse.bodyToMono(String::class.java)
                    .switchIfEmpty(Mono.just(EMPTY))
                    .flatMap {
                        Mono.error(
                            HttpServerErrorException(
                                clientResponse.statusCode(),
                                "$methodName: it",
                            ),
                        )
                    }
            }
        }
    }
}
