package com.example.animals.ui.main

import android.text.format.DateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animals.locateLazy
import com.example.animals.repository.Repository
import com.example.animals.repository.room.Animal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel : ViewModel() {

    private val repository: Repository by locateLazy()

    val animals = repository.getAll().asLiveDataFlow()

    fun save(name: String, age: String, breed: String) {
        viewModelScope.launch { repository.save(createAnimal(name, age, breed)) }
    }

    private fun createAnimal(name: String, age: String, breed: String) = Animal (
        caption = createCaption(),
        name = name,
        age = age,
        breed = breed
    )

    private fun createCaption(): String =
        DateFormat.format("hh:mm:ss MMM dd, yyyy", Date()).toString()

    fun delete(animal: Animal) {
        viewModelScope.launch { repository.delete(animal) }
    }

    private fun <T> Flow<T>.asLiveDataFlow() = shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)
}