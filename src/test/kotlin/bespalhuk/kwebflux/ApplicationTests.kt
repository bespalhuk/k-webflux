package bespalhuk.kwebflux

import bespalhuk.kwebflux.config.TestcontainersConfiguration
import org.springframework.boot.fromApplication
import org.springframework.boot.with

fun main(args: Array<String>) {
    fromApplication<Application>()
        .with(TestcontainersConfiguration::class)
        .withAdditionalProfiles("test")
        .run(*args)
}
