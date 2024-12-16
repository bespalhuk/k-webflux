package bespalhuk.kwebflux.core.usecase

import bespalhuk.kwebflux.core.port.input.DeleteUserPortIn
import bespalhuk.kwebflux.core.port.output.DeleteUserPortOut
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.SignalType
import java.util.logging.Level

@Service
class DeleteUserUseCase(
    private val deleteUserPortOut: DeleteUserPortOut,
) : DeleteUserPortIn {

    override fun delete(id: String): Mono<Void> =
        Mono.just(id)
            .flatMap { deleteUserPortOut.delete(it) }
            .log("DeleteUserUseCase.delete", Level.INFO, SignalType.ON_NEXT)
}
