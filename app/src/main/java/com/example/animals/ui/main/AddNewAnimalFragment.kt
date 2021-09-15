package com.example.animals.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import com.example.animals.R
import com.example.animals.databinding.FragmentAddNewAnimalBinding

class AddNewAnimalFragment: Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private var _binding: FragmentAddNewAnimalBinding? = null
    private val binding get() = _binding!!
    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAddNewAnimalBinding.inflate(inflater).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        views {
            buttonAddAnimal.setOnClickListener { saveAnimal() }
        }
        binding.toolbar.setNavigationOnClickListener { openMainFragment() }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                openMainFragment()
            }
        })
    }

    private fun openMainFragment() {
        val fragment = MainFragment()
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.container, fragment)
            ?.commit()
    }

    private fun saveAnimal() {
        views {
            val nameText = animalNameHint.text.toString().takeIf { it.isNotBlank() }  ?: return@views
            val ageText = animalAgeHint.text.toString().takeIf { it.isNotBlank() }  ?: return@views
            val breedText = animalBreedHint.text.toString().takeIf { it.isNotBlank() }  ?: return@views

            val roomOrSql = sharedPreferences.getString("settings_database_dialog", "")

            when (roomOrSql) {
                "Room" -> viewModel.save(nameText, ageText.toDouble(), breedText)
                "SQLite" -> viewModel.saveSql(nameText, ageText.toDouble(), breedText)
            }
            openMainFragment()
        }
    }

    private fun <T> views(block: FragmentAddNewAnimalBinding.() -> T): T? = binding.block()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}