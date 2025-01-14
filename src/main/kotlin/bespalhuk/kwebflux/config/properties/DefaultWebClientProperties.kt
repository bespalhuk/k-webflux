package bespalhuk.kwebflux.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("integration.client.default")
data class DefaultWebClientProperties(
    val keepIdle: Int,
    val keepInterval: Int,
    val keepCnt: Int,
)
