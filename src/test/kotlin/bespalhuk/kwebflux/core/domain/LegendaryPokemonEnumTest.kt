package bespalhuk.kwebflux.core.domain

import bespalhuk.kwebflux.abstraction.UnitTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class LegendaryPokemonEnumTest : UnitTest() {

    @ParameterizedTest
    @ValueSource(ints = [144, 145, 146, 150, 151])
    fun `map number to enum value`(number: Int) {
        assertThat(LegendaryPokemonEnum.map(number)).isNotNull()
    }

    @Test
    fun `map to default value if number do not exists`() {
        assertThat(LegendaryPokemonEnum.map(0)).isEqualTo(LegendaryPokemonEnum.MEW)
    }
}
