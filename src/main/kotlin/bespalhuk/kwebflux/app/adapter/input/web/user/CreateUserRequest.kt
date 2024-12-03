package bespalhuk.kwebflux.app.adapter.input.web.user

data class CreateUserRequest(
    val username: String,
    val starterNumber: Int?,
    val legendaryNumber: Int?,
)
