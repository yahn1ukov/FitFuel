package com.ua.fitfuel.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.ua.fitfuel.R

object FitFuelAdapter {
    @BindingAdapter("android:loadImage")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String) {
        imageView.load(url) {
            crossfade(400)
            error(R.drawable.ic_no_image)
        }
    }

    @BindingAdapter("android:isRecipeVegan")
    @JvmStatic
    fun isRecipeVegan(view: View, isVegan: Boolean) {
        if (isVegan) {
            when (view) {
                is ImageView -> view.setColorFilter(
                    ContextCompat.getColor(
                        view.context,
                        R.color.green
                    )
                )

                is TextView -> view.setTextColor(
                    ContextCompat.getColor(
                        view.context,
                        R.color.green
                    )
                )
            }
        }
    }

    @BindingAdapter("android:convertFromIntToText")
    @JvmStatic
    fun convertFromIntToText(textView: TextView, number: Int) {
        textView.text = number.toString()
    }
}