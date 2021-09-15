package com.example.animals.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.example.animals.R
import com.example.animals.databinding.MainFragmentBinding
import com.example.animals.repository.Animal
import com.example.animals.ui.main.adapter.AnimalsAdapter
import com.example.animals.ui.main.adapter.SwipeHelper
import com.example.animals.ui.main.settings.SettingsActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private val adapter: AnimalsAdapter? get() = views { animalsList.adapter as? AnimalsAdapter }
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = MainFragmentBinding.inflate(inflater).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sorting = sharedPreferences.getString("sort_by_dialog", "")
        val roomOrSql = sharedPreferences.getString("settings_database_dialog", "")

/*        SwipeHelper(
            when (roomOrSql) {
                "Room" -> viewModel::delete
                "SQLite" -> viewModel::deleteSql
                else -> viewModel::delete
            }
        ).attachToRecyclerView(binding.animalsList)*/


        views {
            animalsList.adapter = AnimalsAdapter()
            addButton.setOnClickListener { openAddNewAnimalFragment() }
            sortButton.setOnClickListener { openSettingsActivity() }
        }

        when (roomOrSql) {
            "Room" ->
                when (sorting) {
                    "name_sort_method" -> viewModel.animalsSortName.onEach(::renderAnimals).launchIn(lifecycleScope)
                    "age_sort_method" -> viewModel.animalsSortAge.onEach(::renderAnimals).launchIn(lifecycleScope)
                    "breed_sort_method" -> viewModel.animalsSortBreed.onEach(::renderAnimals).launchIn(lifecycleScope)
                    else -> viewModel.animals.onEach(::renderAnimals).launchIn(lifecycleScope)
                }

            "SQLite" ->
                when (sorting) {
                "name_sort_method" -> viewModel.animalsSortNameSql.onEach(::renderAnimals).launchIn(lifecycleScope)
                "age_sort_method" -> viewModel.animalsSortAgeSql.onEach(::renderAnimals).launchIn(lifecycleScope)
                "breed_sort_method" -> viewModel.animalsSortBreedSql.onEach(::renderAnimals).launchIn(lifecycleScope)
                else -> viewModel.animalsSql.onEach(::renderAnimals).launchIn(lifecycleScope)
            }
        }
    }

    private fun openSettingsActivity() {
        val intent = Intent(activity, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun openAddNewAnimalFragment() {
        val fragment = AddNewAnimalFragment()
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.container, fragment)
            ?.commit()
    }

    private fun renderAnimals (animals: List<Animal>) {
        adapter?.submitList(animals)
    }

    private fun <T> views(block: MainFragmentBinding.() -> T): T? = binding.block()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

