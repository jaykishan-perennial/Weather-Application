package com.example.openweatherapp.utils.databinding

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorMsg")
fun errorMsg(view: TextInputLayout, msg: String?) {
    view.error = if (msg.isNullOrEmpty()) null else msg
    view.isErrorEnabled = msg.isNullOrEmpty().not()
}

@BindingAdapter("showIf")
fun showIf(view: View, boolean: Boolean) {
    view.isVisible = boolean
}

@BindingAdapter("weatherIconId")
fun weatherIconId(view: ImageView, id: String?) {
    if (id == null) return
    Glide.with(view).load("https://openweathermap.org/img/wn/$id@4x.png").into(view)
}