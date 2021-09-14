package com.example.animals.repository.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalsDao {

    @Query("SELECT * FROM animals")
    fun getAll(): Flow<List<Animal>>

    @Query("SELECT * FROM animals ORDER BY name COLLATE NOCASE ASC")
    fun getAlphabetizedNames(): Flow<List<Animal>>

    @Query("SELECT * FROM animals ORDER BY age ASC")
    fun getAlphabetizedAges(): Flow<List<Animal>>

    @Query("SELECT * FROM animals ORDER BY breed COLLATE NOCASE ASC")
    fun getAlphabetizedBreeds(): Flow<List<Animal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(animal: Animal)

    @Delete
    suspend fun delete(animal: Animal)
}