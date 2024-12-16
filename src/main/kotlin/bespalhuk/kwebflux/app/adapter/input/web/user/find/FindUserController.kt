package bespalhuk.kwebflux.app.adapter.input.web.user.find

import bespalhuk.kwebflux.app.adapter.input.web.user.UserResponse
import bespalhuk.kwebflux.app.adapter.input.web.user.mapper.toResponse
import bespalhuk.kwebflux.core.port.input.FindUserPortIn
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.publisher.SignalType
import java.util.logging.Level

@Tag(name = "user")
@RestController
@RequestMapping("/api/users")
class FindUserController(
    private val findUserPortIn: FindUserPortIn,
) {

    @Operation(summary = "User", description = "Find user")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun find(
        @PathVariable("id") id: String,
    ): Mono<UserResponse> =
        Mono.just(id)
            .flatMap { findUserPortIn.find(it) }
            .map { it.getOrThrow().toResponse() }
            .log("FindUserController.find", Level.INFO, SignalType.ON_ERROR)
}
