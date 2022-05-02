package com.example.qrreadertest

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @BindingAdapter("app:showIfLoading")
    @JvmStatic fun showIfLoading(view: View, isLoading: Boolean) {
        view.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}