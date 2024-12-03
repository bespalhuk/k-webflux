package bespalhuk.kwebflux.core.domain

enum class LegendaryPokemonEnum(val number: Int) {
    ARTICUNO(144),
    ZAPDOS(145),
    MOLTRES(146),
    MEWTWO(150),
    MEW(151),
    ;

    companion object {
        fun map(number: Int?): LegendaryPokemonEnum {
            return entries.find { it.number == number } ?: MEW
        }
    }
}
