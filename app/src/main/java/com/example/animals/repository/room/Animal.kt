package com.example.animals.repository.room

import androidx.room.*

@Entity(tableName = "animals")
data class Animal(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val name: String,
    val age: String,
    val breed: String
)

