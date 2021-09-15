package com.example.animals.ui.main.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import com.example.animals.repository.Animal

class SwipeHelper(onSwiped: (Animal) -> Unit,): ItemTouchHelper(SwipeCallback(onSwiped))