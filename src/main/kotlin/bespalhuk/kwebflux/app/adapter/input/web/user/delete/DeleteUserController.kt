package bespalhuk.kwebflux.app.adapter.input.web.user.delete

import bespalhuk.kwebflux.core.port.input.DeleteUserPortIn
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
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
class DeleteUserController(
    private val deleteUserPortIn: DeleteUserPortIn,
) {

    @Operation(summary = "User", description = "Delete user")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @PathVariable("id") id: String,
    ): Mono<Void> = Mono.just(id)
        .flatMap { deleteUserPortIn.delete(it) }
        .log("DeleteUserController.delete", Level.INFO, SignalType.ON_ERROR)
}
