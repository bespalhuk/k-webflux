package bespalhuk.kwebflux.config.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openapi(): OpenAPI = OpenAPI()
        .components(Components())
        .info(
            Info()
                .title("K API")
                .description("Webflux")
        )
}
