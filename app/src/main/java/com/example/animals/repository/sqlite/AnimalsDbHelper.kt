package com.example.animals.repository.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.animals.repository.Animal
import com.example.animals.repository.sqlite.AnimalsContract.Animal.COLUMN_AGE
import com.example.animals.repository.sqlite.AnimalsContract.Animal.COLUMN_BREED
import com.example.animals.repository.sqlite.AnimalsContract.Animal.COLUMN_ID
import com.example.animals.repository.sqlite.AnimalsContract.Animal.COLUMN_NAME
import com.example.animals.repository.sqlite.AnimalsContract.Animal.TABLE_NAME

class AnimalsDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun addSql(name: String, age: Double, breed: String) {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_AGE, age)
            put(COLUMN_BREED, breed)
        }

        val newRowId = db?.insert(TABLE_NAME, null, values)
    }

    fun getAllSql(): List<Animal> {
        val animals = mutableListOf<Animal>()
        readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME", null)
            .use { cursor ->
                if (cursor.moveToFirst()) {
                    do {
                        val animalId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                        val animalName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                        val animalAge = cursor.getDouble(cursor.getColumnIndex(COLUMN_AGE))
                        val animalBreed = cursor.getString(cursor.getColumnIndex(COLUMN_BREED))
                        animals.add(Animal(animalId, animalName, animalAge, animalBreed))
                    } while (cursor.moveToNext())
                }
            }
        return animals
    }

    fun getAlphabetizedNamesSql(): List<Animal> {
        val animals = mutableListOf<Animal>()
        readableDatabase.rawQuery(
            "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_NAME COLLATE NOCASE ASC",
            null
        )
            .use { cursor ->
                if (cursor.moveToFirst()) {
                    do {
                        val animalId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                        val animalName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                        val animalAge = cursor.getDouble(cursor.getColumnIndex(COLUMN_AGE))
                        val animalBreed = cursor.getString(cursor.getColumnIndex(COLUMN_BREED))
                        animals.add(Animal(animalId, animalName, animalAge, animalBreed))
                    } while (cursor.moveToNext())
                }
            }
        return animals
    }

    fun getAlphabetizedAgesSql(): List<Animal> {
        val animals = mutableListOf<Animal>()
        readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_AGE", null)
            .use { cursor ->
                if (cursor.moveToFirst()) {
                    do {
                        val animalId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                        val animalName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                        val animalAge = cursor.getDouble(cursor.getColumnIndex(COLUMN_AGE))
                        val animalBreed = cursor.getString(cursor.getColumnIndex(COLUMN_BREED))
                        animals.add(Animal(animalId, animalName, animalAge, animalBreed))
                    } while (cursor.moveToNext())
                }
            }
        return animals
    }

    fun getAlphabetizedBreedsSql(): List<Animal> {
        val animals = mutableListOf<Animal>()
        readableDatabase.rawQuery(
            "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_BREED COLLATE NOCASE ASC",
            null
        )
            .use { cursor ->
                if (cursor.moveToFirst()) {
                    do {
                        val animalId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                        val animalName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                        val animalAge = cursor.getDouble(cursor.getColumnIndex(COLUMN_AGE))
                        val animalBreed = cursor.getString(cursor.getColumnIndex(COLUMN_BREED))
                        animals.add(Animal(animalId, animalName, animalAge, animalBreed))
                    } while (cursor.moveToNext())
                }
            }
        return animals
    }

    fun deleteSql(animal: Animal) {
        writableDatabase.use { it.delete(TABLE_NAME, "id=${animal.id}", null) }
    }

}

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "animals-database"
private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE IF NOT EXIST $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COLUMN_NAME TEXT," +
            "$COLUMN_AGE REAL)" +
            "$COLUMN_BREED TEXT)"

private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"


object AnimalsContract {
    object Animal : BaseColumns {
        const val TABLE_NAME = "animals"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_AGE = "age"
        const val COLUMN_BREED = "breed"
    }
}
