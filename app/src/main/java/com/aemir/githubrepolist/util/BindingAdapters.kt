package com.aemir.githubrepolist.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    val requestOptions = RequestOptions().centerCrop()
    Glide.with(this)
        .load(url)
        .placeholder(ColorDrawable(Color.GRAY))
        .apply(requestOptions)
        .into(this)
}