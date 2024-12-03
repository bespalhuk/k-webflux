package bespalhuk.kwebflux.dataprovider

import bespalhuk.kwebflux.app.adapter.input.web.user.CreateUserRequest

class UserRequestDataProvider {

    fun create(): CreateUserRequest = CreateUserRequest(
        "username",
        25,
        151,
    )
}
