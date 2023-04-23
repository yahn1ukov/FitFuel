package com.ua.fitfuel.presentation.recipes.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.ua.fitfuel.R
import com.ua.fitfuel.databinding.FragmentRecipeListBinding
import com.ua.fitfuel.presentation.recipes.adapters.RecipeAdapter
import com.ua.fitfuel.presentation.recipes.viewmodels.RecipeListViewModel
import com.ua.fitfuel.presentation.recipes.viewmodels.SharedViewModel
import com.ua.fitfuel.utils.NetworkResult
import com.ua.fitfuel.utils.observeOnce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentRecipeListBinding? = null
    private val binding get() = _binding!!

    private val recipeListViewModel: RecipeListViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val args: RecipeListFragmentArgs by navArgs()

    private val recipeAdapter: RecipeAdapter by lazy { RecipeAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        setupRecyclerView()

        readDatabase()

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_recipeListFragment_to_recipeBottomSheet)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_recipe_list_menu, menu)

                val search = menu.findItem(R.id.menu_search)
                val searchView = search.actionView as SearchView
                searchView.apply {
                    isSubmitButtonEnabled = true
                    setOnQueryTextListener(this@RecipeListFragment)
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { searchRecipes(it) }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun setupRecyclerView() {
        binding.recyclerViewRecipes.adapter = recipeAdapter
        binding.recyclerViewRecipes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewRecipes.itemAnimator = DefaultItemAnimator()

        binding.componentRecipeListLoading.root.visibility = View.VISIBLE
    }

    private fun readDatabase() {
        recipeListViewModel.localRecipes.observeOnce(viewLifecycleOwner) { tableRecipes ->
            if (tableRecipes.isNotEmpty() && !args.backFromBottomSheet) {
                binding.componentRecipeListLoading.root.visibility = View.GONE
                binding.recyclerViewRecipes.visibility = View.VISIBLE
                recipeAdapter.setNewRecipes(tableRecipes.first().recipes)
            } else {
                requestRecipes()
            }
        }
    }

    private fun requestRecipes() {
        recipeListViewModel.getAllRecipes(sharedViewModel.applyQueries())
        recipeListViewModel.remoteRecipes.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.componentRecipeListLoading.root.visibility = View.GONE
                    binding.recyclerViewRecipes.visibility = View.VISIBLE
                    response.data?.let { recipes ->
                        recipeAdapter.setNewRecipes(recipes)
                    }
                    sharedViewModel.setMealDietType()
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
    }

    private fun searchRecipes(query: String) {
        binding.componentRecipeListLoading.root.visibility = View.VISIBLE
        recipeListViewModel.searchRecipes(sharedViewModel.applyQueries(query))
        recipeListViewModel.searchedRemoteRecipes.observe(viewLifecycleOwner) { response ->
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}