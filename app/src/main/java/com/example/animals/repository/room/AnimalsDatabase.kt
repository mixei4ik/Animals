package com.example.animals.repository.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.animals.repository.Animal

@Database(entities = [Animal::class], version = 1)
abstract class AnimalsDatabase: RoomDatabase() {
    abstract val animalsDao: AnimalsDao

    companion object {
        fun create(context: Context) = Room
            .databaseBuilder(
                context,
                AnimalsDatabase::class.java,
                "animals-database"
            )
            .build()
    }
}