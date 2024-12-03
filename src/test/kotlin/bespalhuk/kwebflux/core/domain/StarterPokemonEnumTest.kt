package bespalhuk.kwebflux.core.domain

import bespalhuk.kwebflux.abstraction.UnitTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class StarterPokemonEnumTest : UnitTest() {

    @ParameterizedTest
    @ValueSource(ints = [1, 4, 7, 25])
    fun `map number to enum value`(number: Int) {
        assertThat(StarterPokemonEnum.map(number)).isNotNull()
    }

    @Test
    fun `map to default value if number do not exists`() {
        assertThat(StarterPokemonEnum.map(0)).isEqualTo(StarterPokemonEnum.PIKACHU)
    }
}
