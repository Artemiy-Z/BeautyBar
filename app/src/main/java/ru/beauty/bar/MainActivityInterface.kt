package ru.beauty.bar

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri

interface MainActivityInterface {
    fun getSharedPrefs(): SharedPreferences
    fun showErrorMessage(
        message: String,
        dismissCallback: (() -> Unit) = {}
    )
    fun showChoiceMessage(
        message: String,
        dismissCallback: (() -> Unit)? = null,
        confirmCallback: (() -> Unit)? = null
    )
    fun toggleLoading(showLoading: Boolean = false)
    fun pickImage(callback: ((Uri?)->Unit) = {})
    fun getContext(): Context
    fun pickMultipleImage(callback: (List<Uri>) -> Unit = {})
}
