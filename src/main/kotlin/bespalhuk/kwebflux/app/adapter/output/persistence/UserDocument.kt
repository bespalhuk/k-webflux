package bespalhuk.kwebflux.app.adapter.output.persistence

import bespalhuk.kwebflux.core.domain.User
import org.bson.types.ObjectId
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Instant

@Document("users")
data class UserDocument(

    @Id
    val id: String? = ObjectId.get().toString(),

    @CreatedDate
    @Field("created_date")
    val createdDate: Instant? = null,

    @LastModifiedDate
    @Field("last_modified")
    var lastModified: Instant? = null,

    @Field("username")
    @Indexed(unique = true)
    @NotNull
    val username: String,

    @Field("team")
    @NotNull
    val team: User.Team,
)
