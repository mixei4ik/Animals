package com.example.animals.ui.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.animals.repository.room.Animal

class NoteDiffCallback: DiffUtil.ItemCallback<Animal>() {
    override fun areContentsTheSame(oldItem: Animal, newItem: Animal): Boolean = oldItem.id == newItem.id

    override fun areItemsTheSame(oldItem: Animal, newItem: Animal): Boolean = oldItem == newItem
}