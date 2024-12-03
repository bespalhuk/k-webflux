package bespalhuk.kwebflux.app.adapter.output.persistence

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDocumentRepository : ReactiveMongoRepository<UserDocument, String>
