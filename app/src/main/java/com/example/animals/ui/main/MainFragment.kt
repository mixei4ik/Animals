package com.example.animals.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.animals.databinding.MainFragmentBinding
import com.example.animals.repository.room.Animal
import com.example.animals.ui.main.adapter.AnimalsAdapter
import com.example.animals.ui.main.adapter.SwipeHelper
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private val adapter: AnimalsAdapter? get() = views { animalsList.adapter as? AnimalsAdapter }
    private var binding: MainFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = MainFragmentBinding.inflate(inflater).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        views {
            animalsList.adapter = AnimalsAdapter()
            SwipeHelper(viewModel::delete).attachToRecyclerView(animalsList)
            addButton.setOnClickListener { saveAnimal() }
        }

        viewModel.animals.onEach(::renderAnimals).launchIn(lifecycleScope)

    }

    private fun saveAnimal() {
        views {
            val nameText = addNameEditText.text.toString().takeIf { it.isNotBlank() }  ?: return@views
            val ageText = addAgeEditText.text.toString().takeIf { it.isNotBlank() }  ?: return@views
            val breedText = addBreedEditText.text.toString().takeIf { it.isNotBlank() }  ?: return@views

            viewModel.save(nameText, ageText, breedText)
        }
    }

    private fun renderAnimals (animals: List<Animal>) {
        adapter?.submitList(animals)
    }

    private fun <T> views(block: MainFragmentBinding.() -> T): T? = binding?.block()

}

