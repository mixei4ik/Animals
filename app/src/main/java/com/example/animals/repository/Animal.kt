package com.example.animals.repository

import androidx.room.*

@Entity(tableName = "animals")
data class Animal(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val name: String,
    val age: Double,
    val breed: String
)

