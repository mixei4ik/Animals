package com.example.animals.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animals.locateLazy
import com.example.animals.repository.Animal
import com.example.animals.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository: Repository by locateLazy()

    val animals = repository.getAll().asLiveDataFlow()
    val animalsSortName = repository.getAlphabetizedNames().asLiveDataFlow()
    val animalsSortAge = repository.getAlphabetizedAges().asLiveDataFlow()
    val animalsSortBreed = repository.getAlphabetizedBreeds().asLiveDataFlow()

    val animalsSql = repository.getAllSql().asLiveDataFlow()
    val animalsSortNameSql = repository.getAlphabetizedNamesSql().asLiveDataFlow()
    val animalsSortAgeSql = repository.getAlphabetizedAgesSql().asLiveDataFlow()
    val animalsSortBreedSql = repository.getAlphabetizedBreedsSql().asLiveDataFlow()

    fun save(name: String, age: Double, breed: String) {
        viewModelScope.launch { repository.save(createAnimal(name, age, breed)) }
    }

    fun saveSql(name: String, age: Double, breed: String) {
        viewModelScope.launch { repository.saveSql(createAnimal(name, age, breed)) }
    }

    private fun createAnimal(name: String, age: Double, breed: String) = Animal(
        name = name,
        age = age,
        breed = breed
    )

    fun delete(animal: Animal) {
        viewModelScope.launch { repository.delete(animal) }
    }

    fun deleteSql(animal: Animal) {
        viewModelScope.launch { repository.deleteSql(animal) }
    }

    private fun <T> Flow<T>.asLiveDataFlow() =
        shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)
}