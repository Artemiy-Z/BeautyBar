package ru.beauty.bar.navigation.presenter

import android.net.Uri
import ru.beauty.bar.App
import ru.beauty.bar.dataLayer.grantAppAccess
import ru.beauty.bar.database.api.StorageRepository

abstract class ImageUploadPresenter : BasePresenter() {
    suspend fun uploadImage(imageUri: Uri): String? {
        val grantedFile = imageUri.grantAppAccess(App.INSTANCE.mainInterface.getContext())

        val storage = StorageRepository(App.INSTANCE.supabaseClient)

        val url = storage.uploadImage(imageFile = grantedFile)

        return url
    }
}