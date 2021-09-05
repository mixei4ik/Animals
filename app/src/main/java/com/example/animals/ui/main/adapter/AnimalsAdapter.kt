package com.example.animals.ui.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.animals.repository.room.Animal

class AnimalsAdapter : ListAdapter<Animal, AnimalViewHolder>(NoteDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder =
        AnimalViewHolder.create(parent)


    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) =
        holder.onBind(getItem(position))


}