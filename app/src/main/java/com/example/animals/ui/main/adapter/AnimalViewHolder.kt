package com.example.animals.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animals.databinding.AnimalListItemBinding
import com.example.animals.repository.Animal

class AnimalViewHolder(
    private val binding: AnimalListItemBinding,
): RecyclerView.ViewHolder(binding.root) {

    var item: Animal? = null
        private set

    fun onBind(item: Animal) {
        this.item = item
        views {
            animalName.text = "Name :  " + item.name
            animalAge.text = "Age    :  " + item.age.toString()
            animalBreed.text = "Breed :  " + item.breed
        }
    }

    private fun <T> views(block: AnimalListItemBinding.() -> T): T? = binding.block()

    companion object {
        fun create(parent: ViewGroup) = AnimalListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let(::AnimalViewHolder)
    }
}