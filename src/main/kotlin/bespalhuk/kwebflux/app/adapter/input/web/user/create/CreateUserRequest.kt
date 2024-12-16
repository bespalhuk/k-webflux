package bespalhuk.kwebflux.app.adapter.input.web.user.create

data class CreateUserRequest(
    val username: String,
    val team: String,
    val starterNumber: Int?,
    val legendaryNumber: Int?,
)
