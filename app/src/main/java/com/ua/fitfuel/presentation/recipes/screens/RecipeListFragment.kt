package com.ua.fitfuel.presentation.recipes.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.ua.fitfuel.data.remote.models.entities.Recipes
import com.ua.fitfuel.databinding.FragmentRecipeListBinding
import com.ua.fitfuel.presentation.recipes.adapters.RecipeAdapter
import com.ua.fitfuel.presentation.recipes.viewmodels.RecipeViewModel
import com.ua.fitfuel.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    private val recipeViewModel: RecipeViewModel by viewModels()

    private val recipeAdapter: RecipeAdapter by lazy { RecipeAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        setupRecyclerView()

        requestRecipes()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerViewRecipes.adapter = recipeAdapter
        binding.recyclerViewRecipes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewRecipes.itemAnimator = DefaultItemAnimator()
    }

    private fun requestRecipes() {
        recipeViewModel.recipe.observe(viewLifecycleOwner) { response ->
            handleUIState(response)
        }
    }

    private fun handleUIState(response: NetworkResult<Recipes>) {
        when (response) {
            is NetworkResult.Success -> {
                binding.componentRecipeListLoading.root.visibility = View.GONE
                binding.recyclerViewRecipes.visibility = View.VISIBLE
                response.data?.let { recipes ->
                    recipeAdapter.setNewRecipes(recipes)
                }
            }

            is NetworkResult.Error -> {
                binding.componentRecipeListLoading.root.visibility = View.GONE
                binding.componentRecipeListError.root.visibility = View.VISIBLE
                response.message?.let { message ->
                    binding.componentRecipeListError.message = message
                }
            }

            is NetworkResult.Loading -> {
                binding.componentRecipeListLoading.root.visibility = View.VISIBLE
                binding.recyclerViewRecipes.visibility = View.GONE
                binding.componentRecipeListError.root.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}