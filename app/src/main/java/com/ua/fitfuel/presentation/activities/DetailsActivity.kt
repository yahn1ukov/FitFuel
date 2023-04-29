package com.ua.fitfuel.presentation.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.ua.fitfuel.R
import com.ua.fitfuel.data.local.models.entities.FavoritesEntity
import com.ua.fitfuel.databinding.ActivityDetailsBinding
import com.ua.fitfuel.presentation.recipes.screens.RecipeIngredientsFragment
import com.ua.fitfuel.presentation.recipes.screens.RecipeOverviewFragment
import com.ua.fitfuel.presentation.recipes.viewmodels.RecipeFavoriteViewModel
import com.ua.fitfuel.utils.Constants.Companion.ARGS_RECIPE_KEY
import com.ua.fitfuel.utils.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    private val args: DetailsActivityArgs by navArgs()

    private val recipeFavoriteViewModel: RecipeFavoriteViewModel by viewModels()

    private var recipeSaved = false
    private var recipeSavedId = 0

    private lateinit var menuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        setSupportActionBar(binding.toolbarDetails)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val resultBundle = Bundle()
        resultBundle.putParcelable(ARGS_RECIPE_KEY, args.recipe)

        val fragments = ArrayList<Fragment>()
        fragments.add(RecipeOverviewFragment())
        fragments.add(RecipeIngredientsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")

        val pagerAdapter = ViewPagerAdapter(
            resultBundle,
            fragments,
            this
        )

        binding.viewPagerDetails2.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayoutDetails, binding.viewPagerDetails2) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_details_menu, menu)
        menuItem = menu!!.findItem(R.id.menu_favorite)
        checkSavedRecipe(menuItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.menu_favorite && recipeSaved) {
            removeFromFavorites(item)
        } else if (item.itemId == R.id.menu_favorite && !recipeSaved) {
            saveToFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkSavedRecipe(item: MenuItem) {
        recipeFavoriteViewModel.localFavorites.observe(this) {
            for (favorite in it) {
                if (favorite.recipe.id == args.recipe.id) {
                    changeMenuItemColor(item, R.color.yellow)
                    recipeSavedId = favorite.id
                    recipeSaved = true
                }
            }
        }
    }

    private fun saveToFavorites(item: MenuItem) {
        recipeFavoriteViewModel.insert(FavoritesEntity(0, args.recipe))
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Recipe saved")
        recipeSaved = true
    }

    private fun removeFromFavorites(item: MenuItem) {
        recipeFavoriteViewModel.delete(FavoritesEntity(recipeSavedId, args.recipe))
        changeMenuItemColor(item, R.color.mediumGray)
        showSnackBar("Recipe removed")
        recipeSaved = false
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this, color))
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.constraintLayoutDetails, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        changeMenuItemColor(menuItem, R.color.mediumGray)
    }
}