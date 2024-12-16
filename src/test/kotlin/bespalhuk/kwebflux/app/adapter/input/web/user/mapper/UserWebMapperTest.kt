package bespalhuk.kwebflux.app.adapter.input.web.user.mapper

import bespalhuk.kwebflux.abstraction.UnitTest
import bespalhuk.kwebflux.dataprovider.UserDataProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserWebMapperTest : UnitTest() {

    @Test
    fun `map User to UserResponse`() {
        val user = UserDataProvider().user()

        val response = user.toResponse()
        assertThat(response.id).isEqualTo(user.id)
        assertThat(response.username).isEqualTo(user.username)
        assertThat(response.team).isEqualTo(user.team.name)
        assertThat(response.starter).isEqualTo(user.team.starter)
        assertThat(response.starterMove).isEqualTo(user.team.starterMove)
        assertThat(response.legendary).isEqualTo(user.team.legendary)
        assertThat(response.legendaryMove).isEqualTo(user.team.legendaryMove)
    }
}
