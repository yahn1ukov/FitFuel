package com.ua.fitfuel.presentation.recipes.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.ua.fitfuel.databinding.FragmentRecipeBottomSheetBinding
import com.ua.fitfuel.presentation.recipes.viewmodels.SharedViewModel
import com.ua.fitfuel.utils.Constants.Companion.DEFAULT_DIET
import com.ua.fitfuel.utils.Constants.Companion.DEFAULT_DIET_ID
import com.ua.fitfuel.utils.Constants.Companion.DEFAULT_TYPE
import com.ua.fitfuel.utils.Constants.Companion.DEFAULT_TYPE_ID
import java.util.Locale

class RecipeBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentRecipeBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var mealType = DEFAULT_TYPE
    private var mealTypeId = DEFAULT_TYPE_ID
    private var dietType = DEFAULT_DIET
    private var dietTypeId = DEFAULT_DIET_ID

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBottomSheetBinding.inflate(inflater, container, false)

        sharedViewModel.getMealDietType.observe(viewLifecycleOwner) {
            if (it.mealTypeId != 0 && it.dietTypeId != 0) {
                updateChipGroup(it.mealTypeId, binding.chipGroupMealType)
                updateChipGroup(it.dietTypeId, binding.chipGroupDietType)
            }
        }

        binding.chipGroupMealType.setOnCheckedStateChangeListener { group, chipIds ->
            val selectedChip = group.findViewById<Chip>(chipIds.first())
            val selectedMealType = selectedChip.text.toString().lowercase(Locale.ROOT)
            mealType = selectedMealType
            mealTypeId = chipIds.first()
        }

        binding.chipGroupDietType.setOnCheckedStateChangeListener { group, chipIds ->
            val selectedChip = group.findViewById<Chip>(chipIds.first())
            val selectedDietType = selectedChip.text.toString().lowercase(Locale.ROOT)
            dietType = selectedDietType
            dietTypeId = chipIds.first()
        }

        binding.btnApplyRecipeBottomSheet.setOnClickListener {
            sharedViewModel.setMealDietType(
                mealType,
                mealTypeId,
                dietType,
                dietTypeId
            )
            val action =
                RecipeBottomSheetDirections.actionRecipeBottomSheetToRecipeListFragment(true)
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun updateChipGroup(chipId: Int, chipGroup: ChipGroup) {
        val chip = chipGroup.findViewById<Chip>(chipId)
        chip.isChecked = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}