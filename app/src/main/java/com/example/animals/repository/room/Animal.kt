package com.example.animals.repository.room

import androidx.room.*

@Entity(tableName = "animals")
data class Animal(
    @PrimaryKey val id:Int = 0,
    val name: String,
    val age: Double,
    val breed: String
)

