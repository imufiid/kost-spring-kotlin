package com.mufid.kost.kost.repository

import com.mongodb.client.MongoCollection
import com.mufid.kost.database.DatabaseComponent
import com.mufid.kost.kost.entity.Kost
import org.litote.kmongo.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.lang.IllegalStateException

@Repository
class KostRepositoryImpl: KostRepository {

    @Autowired
    private lateinit var databaseComponent: DatabaseComponent

    private fun kostCollection(): MongoCollection<Kost> =
        databaseComponent.database.getDatabase("kost").getCollection()

    override fun getAll(): List<Kost> {
        return kostCollection().find().toList()
    }

    override fun getById(id: Int?): Kost? {
        return kostCollection().findOne(Kost::id eq id)
    }

    override fun search(name: String): List<Kost> {
        return kostCollection().find(Kost::name regex "*.$name.*").toList()
    }

    override fun add(kost: Kost): List<Kost> {
        val insert = kostCollection().insertOne(kost)

        return if (insert.wasAcknowledged()) {
            getAll()
        } else {
            throw IllegalStateException("Failed Insert")
        }
    }

    override fun update(kost: Kost): Kost? {
        val update = kostCollection().updateOneById(kost.id ?: 0, kost)

        return if (update.wasAcknowledged()) {
            getById(kost.id)
        } else {
            throw IllegalStateException("Failed Update")
        }
    }

    override fun delete(id: Int): Kost? {
        val delete = kostCollection().deleteOne(Kost::id eq id)

        return if (delete.wasAcknowledged()) {
            return null
        } else {
            throw IllegalStateException("Failed Delete")
        }
    }
}