package bespalhuk.kwebflux.core.domain.mapper

import bespalhuk.kwebflux.abstraction.UnitTest
import bespalhuk.kwebflux.dataprovider.UserInputDataProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserMapperTest : UnitTest() {

    @Test
    fun `map UserInput to User`() {
        val input = UserInputDataProvider().input()

        val user = input.toDomain()
        assertThat(user.username).isEqualTo(input.username)
        assertThat(user.team.starter.number).isEqualTo(input.starterNumber)
        assertThat(user.team.legendary.number).isEqualTo(input.legendaryNumber)
    }
}
