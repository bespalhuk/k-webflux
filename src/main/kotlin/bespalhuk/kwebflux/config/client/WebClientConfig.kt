package bespalhuk.kwebflux.config.client

import bespalhuk.kwebflux.config.properties.DefaultWebClientProperties
import io.netty.channel.ChannelOption
import io.netty.channel.epoll.EpollChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import mu.KotlinLogging
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeFilterFunction.ofRequestProcessor
import reactor.core.publisher.Mono
import reactor.netty.Connection
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.concurrent.TimeUnit

private val log = KotlinLogging.logger {}

@Configuration
class WebClientConfig(
    private val defaultWebClientProperties: DefaultWebClientProperties,
) {

    fun buildHttpClient(timeout: Int): ReactorClientHttpConnector =
        ReactorClientHttpConnector(
            HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(EpollChannelOption.TCP_KEEPIDLE, defaultWebClientProperties.keepIdle)
                .option(EpollChannelOption.TCP_KEEPINTVL, defaultWebClientProperties.keepInterval)
                .option(EpollChannelOption.TCP_KEEPCNT, defaultWebClientProperties.keepCnt)
                .responseTimeout(Duration.ofMillis(timeout.toLong()))
                .doOnConnected { connection: Connection ->
                    connection
                        .addHandlerFirst(ReadTimeoutHandler(timeout.toLong(), TimeUnit.MILLISECONDS))
                        .addHandlerFirst(WriteTimeoutHandler(timeout.toLong(), TimeUnit.MILLISECONDS))
                },
        )

    fun logRequestFilter(client: String): ExchangeFilterFunction =
        ofRequestProcessor {
            log.info("client={}, method={}, uri={}, headers={}", client, it.method(), it.url(), it.headers())
            Mono.just(it)
        }
}
