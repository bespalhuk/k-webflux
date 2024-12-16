package bespalhuk.kwebflux.config.client

import bespalhuk.kwebflux.config.client.WebClientConstants.Constants.APP_NAME
import bespalhuk.kwebflux.config.client.WebClientConstants.Constants.BEAN_WEBCLIENT_POKEMON
import bespalhuk.kwebflux.config.client.WebClientConstants.Constants.REQUEST_ORIGIN
import bespalhuk.kwebflux.config.properties.PokeApiProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.CodecConfigurer
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class PokemonClient(
    private val webClientConfiguration: WebClientConfig,
    private val pokeApiProperties: PokeApiProperties,
) {

    @Bean(BEAN_WEBCLIENT_POKEMON)
    fun webClient(): WebClient = WebClient.builder()
        .baseUrl(pokeApiProperties.url)
        .clientConnector(webClientConfiguration.buildHttpClient(pokeApiProperties.timeout))
        .defaultHeader(REQUEST_ORIGIN, APP_NAME)
        .exchangeStrategies(
            ExchangeStrategies.builder()
                .codecs { config: CodecConfigurer ->
                    config.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)
                }
                .build()
        )
        .filter(webClientConfiguration.logRequestFilter(BEAN_WEBCLIENT_POKEMON))
        .build()
}
