package com.example.animals.repository.sqlite

import android.content.Context
import com.example.animals.repository.Animal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AnimalsDaoSql(context: Context) {
    private val dbSql = AnimalsDbHelper(context)

    fun getAllSql(): Flow<List<Animal>> = flow { emit(dbSql.getAllSql()) }

    fun getAlphabetizedNamesSql(): Flow<List<Animal>> = flow { emit(dbSql.getAlphabetizedNamesSql()) }

    fun getAlphabetizedAgesSql(): Flow<List<Animal>> = flow { emit(dbSql.getAlphabetizedAgesSql()) }

    fun getAlphabetizedBreedsSql(): Flow<List<Animal>> = flow { emit(dbSql.getAlphabetizedBreedsSql()) }

    suspend fun addSql(animal: Animal) = dbSql.addSql(animal.name, animal.age, animal.breed)

    suspend fun deleteSql(animal: Animal) = dbSql.deleteSql(animal)
}