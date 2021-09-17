package com.example.animals.repository

import android.app.Application
import androidx.preference.PreferenceManager
import com.example.animals.repository.room.AnimalsDatabase
import com.example.animals.repository.sqlite.AnimalsDaoSql
import kotlinx.coroutines.flow.Flow

class Repository(
    private val db: AnimalsDatabase,
    private val daoSql: AnimalsDaoSql,
    private val application: Application
) {

    private val dao get() = db.animalsDao

    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(application) }
    val sorting = sharedPreferences.getString("sort_by_dialog", "")
    val roomOrSql = sharedPreferences.getString("settings_database_dialog", "")

    fun getAll(): Flow<List<Animal>> = when (roomOrSql) {
        "Room" ->
            when (sorting) {
                "name_sort_method" -> dao.getAlphabetizedNames()
                "age_sort_method" -> dao.getAlphabetizedAges()
                "breed_sort_method" -> dao.getAlphabetizedBreeds()
                else -> dao.getAll()
            }

        "SQLite" ->
            when (sorting) {
                "name_sort_method" -> daoSql.getAlphabetizedNamesSql()
                "age_sort_method" -> daoSql.getAlphabetizedAgesSql()
                "breed_sort_method" -> daoSql.getAlphabetizedBreedsSql()
                else -> daoSql.getAllSql()
            }
        else -> when (sorting) {
            "name_sort_method" -> dao.getAlphabetizedNames()
            "age_sort_method" -> dao.getAlphabetizedAges()
            "breed_sort_method" -> dao.getAlphabetizedBreeds()
            else -> dao.getAll()
        }
    }

    suspend fun save(animal: Animal) = when(roomOrSql) {
        "Room" -> dao.add(animal)
        "SQLite" -> daoSql.addSql(animal)
        else -> dao.add(animal)
    }

    suspend fun delete(animal: Animal) = when(roomOrSql) {
        "Room" -> dao.delete(animal)
        "SQLite" -> daoSql.addSql(animal)
        else -> dao.delete(animal)
    }
}