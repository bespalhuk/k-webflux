package bespalhuk.kwebflux.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("integration.client.pokemon-api")
data class PokemonApiProperties(
    val url: String,
    val timeout: Int,
)
