package bespalhuk.kwebflux.app.adapter.input.web.user.mapper

import bespalhuk.kwebflux.abstraction.UnitTest
import bespalhuk.kwebflux.dataprovider.UserDataProvider
import bespalhuk.kwebflux.dataprovider.UserRequestDataProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserWebMapperTest : UnitTest() {

    @Test
    fun `map CreateUserRequest to UserInput`() {
        val request = UserRequestDataProvider().create()

        val input = request.toInput()
        assertThat(input.username).isEqualTo(request.username)
        assertThat(input.starterNumber).isEqualTo(request.starterNumber)
        assertThat(input.legendaryNumber).isEqualTo(request.legendaryNumber)
    }

    @Test
    fun `map User to CreateUserResponse`() {
        val user = UserDataProvider().user()

        val response = user.toResponse()
        assertThat(response.id).isEqualTo(user.id)
        assertThat(response.username).isEqualTo(user.username)
        assertThat(response.starter).isEqualTo(user.team.starter)
        assertThat(response.starterMove).isEqualTo(user.team.starterMove)
        assertThat(response.legendary).isEqualTo(user.team.legendary)
        assertThat(response.legendaryMove).isEqualTo(user.team.legendaryMove)
    }
}
