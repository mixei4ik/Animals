package com.example.animals.ui.main.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.animals.repository.room.Animal

class SwipeCallback(
    private val onSwiped: (Animal) -> Unit,
) : ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        (viewHolder as? AnimalViewHolder)?.item?.let { onSwiped(it) }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false
}