package com.example.shoplistapp.presentation

import androidx.databinding.BindingAdapter
import com.example.shoplistapp.R
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("errorTilName")
fun bindErrorTilName(textInputLayout: TextInputLayout, isError:Boolean){
    val messErr = if (isError) {
        textInputLayout.context.getString(R.string.error_input_name)
    } else {
        null
    }
    textInputLayout.error = messErr
}
@BindingAdapter("errorTilCount")
fun bindErrorTilCount(textInputLayout: TextInputLayout, isError:Boolean){
    val messErr = if (isError) {
        textInputLayout.context.getString(R.string.error_input_count)
    } else {
        null
    }
    textInputLayout.error = messErr
}
