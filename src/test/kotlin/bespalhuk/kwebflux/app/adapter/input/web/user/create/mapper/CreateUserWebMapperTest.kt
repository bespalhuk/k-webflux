package bespalhuk.kwebflux.app.adapter.input.web.user.create.mapper

import bespalhuk.kwebflux.abstraction.UnitTest
import bespalhuk.kwebflux.core.domain.LegendaryPokemonEnum
import bespalhuk.kwebflux.core.domain.StarterPokemonEnum
import bespalhuk.kwebflux.dataprovider.UserRequestDataProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CreateUserWebMapperTest : UnitTest() {

    @Test
    fun `map CreateUserRequest to CreateUserInput`() {
        val request = UserRequestDataProvider().create()

        val input = request.toInput()
        assertThat(input.username).isEqualTo(request.username)
        assertThat(input.team).isEqualTo(request.team)
        assertThat(input.starter).isEqualTo(StarterPokemonEnum.map(request.starterNumber))
        assertThat(input.legendary).isEqualTo(LegendaryPokemonEnum.map(request.legendaryNumber))
    }
}
