package com.ua.fitfuel.presentation.recipes.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.ua.fitfuel.data.remote.models.entities.Recipe
import com.ua.fitfuel.databinding.FragmentRecipeIngredientsBinding
import com.ua.fitfuel.presentation.recipes.adapters.IngredientAdapter
import com.ua.fitfuel.utils.Constants.Companion.ARGS_RECIPE_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeIngredientsFragment : Fragment() {
    private var _binding: FragmentRecipeIngredientsBinding? = null
    private val binding get() = _binding!!

    private val ingredientAdapter: IngredientAdapter by lazy { IngredientAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeIngredientsBinding.inflate(inflater, container, false)

        val recipe = arguments?.getParcelable<Recipe>(ARGS_RECIPE_KEY)

        setupRecyclerView()

        recipe?.ingredients?.let {
            ingredientAdapter.setNewIngredients(it)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerViewIngredients.adapter = ingredientAdapter
        binding.recyclerViewIngredients.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewIngredients.itemAnimator = DefaultItemAnimator()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}