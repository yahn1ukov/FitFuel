package com.ua.fitfuel.presentation.recipes.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.ua.fitfuel.R
import com.ua.fitfuel.databinding.FragmentRecipeFavoriteBinding
import com.ua.fitfuel.presentation.recipes.adapters.FavoriteAdapter
import com.ua.fitfuel.presentation.recipes.viewmodels.RecipeFavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFavoriteFragment : Fragment() {
    private var _binding: FragmentRecipeFavoriteBinding? = null
    private val binding get() = _binding!!

    private val favoriteAdapter: FavoriteAdapter by lazy { FavoriteAdapter() }
    private val recipeFavoriteViewModel: RecipeFavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeFavoriteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        setupRecyclerView()

        loadFavorites()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_recipe_favorite_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.menu_delete_all) {
                    recipeFavoriteViewModel.deleteAll()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupRecyclerView() {
        binding.recyclerViewRecipesFavorites.adapter = favoriteAdapter
        binding.recyclerViewRecipesFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewRecipesFavorites.itemAnimator = DefaultItemAnimator()
    }

    private fun loadFavorites() {
        recipeFavoriteViewModel.localFavorites.observe(viewLifecycleOwner) {
            favoriteAdapter.setNewFavorites(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}