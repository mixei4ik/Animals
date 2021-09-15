package com.example.animals.repository

import com.example.animals.repository.sqlite.AnimalsDaoSql
import com.example.animals.repository.room.AnimalsDatabase
import kotlinx.coroutines.flow.Flow

class Repository(
    private val db: AnimalsDatabase,
    private val daoSql: AnimalsDaoSql
) {



    private val dao get() = db.animalsDao

    fun getAll(): Flow<List<Animal>> = dao.getAll()

    fun getAlphabetizedNames(): Flow<List<Animal>> = dao.getAlphabetizedNames()

    fun getAlphabetizedAges(): Flow<List<Animal>> = dao.getAlphabetizedAges()

    fun getAlphabetizedBreeds(): Flow<List<Animal>> = dao.getAlphabetizedBreeds()

    suspend fun save(animal: Animal) = dao.add(animal)

    suspend fun delete(animal: Animal) = dao.delete(animal)

    fun getAllSql(): Flow<List<Animal>> = daoSql.getAllSql()

    fun getAlphabetizedNamesSql(): Flow<List<Animal>> = daoSql.getAlphabetizedNamesSql()

    fun getAlphabetizedAgesSql(): Flow<List<Animal>> = daoSql.getAlphabetizedAgesSql()

    fun getAlphabetizedBreedsSql(): Flow<List<Animal>> = daoSql.getAlphabetizedBreedsSql()

    suspend fun saveSql(animal: Animal) = daoSql.addSql(animal)

    suspend fun deleteSql(animal: Animal) = daoSql.deleteSql(animal)

}