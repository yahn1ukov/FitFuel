package com.ua.fitfuel.presentation.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.ua.fitfuel.R
import com.ua.fitfuel.databinding.ActivityDetailsBinding
import com.ua.fitfuel.presentation.recipes.screens.RecipeIngredientsFragment
import com.ua.fitfuel.presentation.recipes.screens.RecipeOverviewFragment
import com.ua.fitfuel.utils.Constants.Companion.ARGS_RECIPE_KEY
import com.ua.fitfuel.utils.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding

    private val args: DetailsActivityArgs by navArgs()

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

        val adapter = ViewPagerAdapter(resultBundle, fragments, titles, supportFragmentManager)

        binding.viewPagerDetails.adapter = adapter
        binding.tabLayoutDetails.setupWithViewPager(binding.viewPagerDetails)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}