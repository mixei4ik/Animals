package com.example.animals.repository

import com.example.animals.repository.room.Animal
import com.example.animals.repository.room.AnimalsDatabase
import kotlinx.coroutines.flow.Flow

class Repository(
    private val db: AnimalsDatabase,
) {

    private val dao get() = db.animalsDao

    fun getAll(): Flow<List<Animal>> = dao.getAll()

    suspend fun save(animal: Animal) = dao.add(animal)

    suspend fun delete(animal: Animal) = dao.delete(animal)

}