package bespalhuk.kwebflux.app.adapter.output.persistence.mapper

import bespalhuk.kwebflux.app.adapter.output.persistence.UserDocument
import bespalhuk.kwebflux.core.domain.User

fun User.toDocument(
) = UserDocument(
    id = id,
    createdDate = createdDate,
    lastModified = lastModified,
    username = username,
    team = team,
)

fun UserDocument.toDomain(
) = User(
    id = id,
    createdDate = createdDate,
    lastModified = lastModified,
    username = username,
    team = team,
)
