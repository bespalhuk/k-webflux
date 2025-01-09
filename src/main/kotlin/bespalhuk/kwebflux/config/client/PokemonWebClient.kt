package bespalhuk.kwebflux.config.client

import bespalhuk.kwebflux.config.client.ClientConstants.Constants.APP_NAME
import bespalhuk.kwebflux.config.client.ClientConstants.Constants.QUALIFIER_WEBCLIENT_POKEMON
import bespalhuk.kwebflux.config.client.ClientConstants.Constants.REQUEST_ORIGIN
import bespalhuk.kwebflux.config.properties.PokemonApiProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.CodecConfigurer
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class PokemonWebClient(
    private val webClientConfig: WebClientConfig,
    private val pokemonApiProperties: PokemonApiProperties,
) {

    @Bean(QUALIFIER_WEBCLIENT_POKEMON)
    fun webClient(): WebClient = WebClient.builder()
        .baseUrl(pokemonApiProperties.url)
        .clientConnector(webClientConfig.buildHttpClient(pokemonApiProperties.timeout))
        .defaultHeader(REQUEST_ORIGIN, APP_NAME)
        .exchangeStrategies(
            ExchangeStrategies.builder()
                .codecs { config: CodecConfigurer ->
                    config.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)
                }
                .build()
        )
        .filter(webClientConfig.logRequestFilter(QUALIFIER_WEBCLIENT_POKEMON))
        .build()
}
