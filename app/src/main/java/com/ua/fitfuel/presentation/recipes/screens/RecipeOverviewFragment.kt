package com.ua.fitfuel.presentation.recipes.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ua.fitfuel.data.remote.models.entities.Recipe
import com.ua.fitfuel.databinding.FragmentRecipeOverviewBinding
import com.ua.fitfuel.utils.Constants.Companion.ARGS_RECIPE_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeOverviewFragment : Fragment() {
    private var _binding: FragmentRecipeOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeOverviewBinding.inflate(inflater, container, false)

        val recipe = arguments?.getParcelable<Recipe>(ARGS_RECIPE_KEY)
        binding.recipe = recipe

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}