package com.example.openweatherapp.utils.extension

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.openweatherapp.R

fun Context.showToast(toastMessage: String) {
    Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(@StringRes toastMessage: Int) {
    Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
}

fun FragmentActivity.hideKeyboard() {
    currentFocus?.let { view ->
        val imm = ContextCompat.getSystemService(this, InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Fragment.showAlertDialog(
    mainTitle: String = getString(R.string.title_alert),
    subTitle: String,
    actionButtonText: String = getString(R.string.action_okay),
    isCancellable: Boolean = true,
    onBtnClick: ((DialogInterface) -> Unit)? = null
) {
    requireContext().showAlertDialog(
        mainTitle, subTitle, actionButtonText, isCancellable, onBtnClick
    )
}

fun Context.showAlertDialog(
    mainTitle: String, subTitle: String,
    actionButtonText: String = getString(R.string.action_okay),
    isCancellable: Boolean = true,
    onClick: ((DialogInterface) -> Unit)? = null
): AlertDialog.Builder {
    return AlertDialog.Builder(this).apply {
        setTitle(mainTitle)
        setMessage(subTitle)
        setCancelable(isCancellable)
        setPositiveButton(actionButtonText) { dialogInterface, _ ->
            onClick?.invoke(dialogInterface)
        }
        show()
    }
}