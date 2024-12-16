package bespalhuk.kwebflux.app.adapter.input.web.user.create

import bespalhuk.kwebflux.app.adapter.input.web.user.UserResponse
import bespalhuk.kwebflux.app.adapter.input.web.user.create.mapper.toInput
import bespalhuk.kwebflux.app.adapter.input.web.user.mapper.toResponse
import bespalhuk.kwebflux.core.port.input.CreateUserPortIn
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.core.publisher.SignalType
import java.util.logging.Level

@Tag(name = "user")
@RestController
@RequestMapping("/api/users")
class CreateUserController(
    private val createUserPortIn: CreateUserPortIn,
) {

    @Operation(summary = "User", description = "Create user")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody request: CreateUserRequest,
    ): Mono<UserResponse> =
        Mono.just(request)
            .map { it.toInput() }
            .flatMap { createUserPortIn.create(it) }
            .map { it.getOrThrow().toResponse() }
            .log("CreateUserController.create", Level.INFO, SignalType.ON_ERROR)
}
