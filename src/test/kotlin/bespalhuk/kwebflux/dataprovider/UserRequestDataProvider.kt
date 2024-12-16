package bespalhuk.kwebflux.dataprovider

import bespalhuk.kwebflux.app.adapter.input.web.user.create.CreateUserRequest

class UserRequestDataProvider {

    fun create(): CreateUserRequest = CreateUserRequest(
        "username",
        "rocket",
        25,
        151,
    )
}
