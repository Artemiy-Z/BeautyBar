package ru.beauty.bar.navigation.presenter

import android.net.Uri
import com.github.terrakok.cicerone.Router
import ru.beauty.bar.App
import ru.beauty.bar.dataLayer.grantAppAccess
import ru.beauty.bar.database.api.StorageRepository

abstract class BasePresenter {
    open fun onBackPressed() {router.exit()}
    val router: Router
        get() = App.INSTANCE.router

    open suspend fun uploadImage(imageUri: Uri): String? {
        val grantedFile = imageUri.grantAppAccess(App.INSTANCE.mainInterface.getContext())

        val storage = StorageRepository(App.INSTANCE.supabaseClient)

        val url = storage.uploadImage(imageFile = grantedFile)

        return url
    }
}