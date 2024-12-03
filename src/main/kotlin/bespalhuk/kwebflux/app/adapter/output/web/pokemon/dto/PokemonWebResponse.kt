package bespalhuk.kwebflux.app.adapter.output.web.pokemon.dto

data class PokemonWebResponse(
    val moves: List<MoveItem>,
)

data class MoveItem(
    val move: Move
)

data class Move(
    val name: String,
)
