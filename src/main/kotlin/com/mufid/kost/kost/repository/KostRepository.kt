package com.mufid.kost.kost.repository

import com.mufid.kost.kost.entity.Kost

interface KostRepository {
    fun getAll(): List<Kost>
    fun getById(id: String?): Kost?
    fun search(name: String): List<Kost>
    fun add(kost: Kost): List<Kost>
    fun update(idString: String?, kost: Kost): Kost?
    fun delete(id: String?): Kost?
}