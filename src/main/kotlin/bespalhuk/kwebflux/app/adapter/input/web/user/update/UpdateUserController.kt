package bespalhuk.kwebflux.app.adapter.input.web.user.update

import bespalhuk.kwebflux.app.adapter.input.web.user.UserResponse
import bespalhuk.kwebflux.app.adapter.input.web.user.mapper.toResponse
import bespalhuk.kwebflux.app.adapter.input.web.user.update.mapper.toInput
import bespalhuk.kwebflux.core.port.input.UpdateUserPortIn
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
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
class UpdateUserController(
    private val updateUserPortIn: UpdateUserPortIn,
) {

    @Operation(summary = "User", description = "Update user")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun update(
        @PathVariable("id") id: String,
        @RequestBody request: UpdateUserRequest,
    ): Mono<UserResponse> =
        Mono.just(request)
            .map { it.toInput(id) }
            .flatMap { updateUserPortIn.update(it) }
            .map { it.getOrThrow().toResponse() }
            .log("UpdateUserController.update", Level.INFO, SignalType.ON_ERROR)
}
