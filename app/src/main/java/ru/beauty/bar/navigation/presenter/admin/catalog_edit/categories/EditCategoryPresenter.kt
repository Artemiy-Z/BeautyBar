package ru.beauty.bar.navigation.presenter.admin.catalog_edit.categories;

import android.accounts.AuthenticatorDescription
import android.net.Uri
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import ru.beauty.bar.App
import ru.beauty.bar.dataLayer.grantAppAccess
import ru.beauty.bar.database.api.CatalogRepository
import ru.beauty.bar.database.api.StorageRepository
import ru.beauty.bar.database.model.Category
import ru.beauty.bar.database.model.CategoryInsert
import ru.beauty.bar.navigation.presenter.BasePresenter

class EditCategoryPresenter : BasePresenter() {
    override fun onBackPressed() {
        App.INSTANCE.mainInterface.showChoiceMessage(
            message = "Отменить?",
            confirmCallback = {
                App.INSTANCE.sharedData.editedCategory = null
                router.exit()
            }
        )
    }

    fun onPictureSelectPressed(
        onSelectedListener: ((url: Uri?)->Unit) = {},
    ) {
        App.INSTANCE.mainInterface.pickImage(
            callback = {uri ->
                onSelectedListener(uri)
            }
        )
    }

    override suspend fun uploadImage(imageUri: Uri): String? {
        val grantedFile = imageUri.grantAppAccess(App.INSTANCE.mainInterface.getContext())

        val storage = StorageRepository(App.INSTANCE.supabaseClient)

        val url = storage.uploadImage(imageFile = grantedFile)

        return url
    }

    suspend fun onSavePressed(
        name: String,
        description: String,
        previewImageLink: String,
        imageUri: Uri?
    ) {
        if(name.isEmpty()) {
            App.INSTANCE.mainInterface.showErrorMessage("Не введено название категории!")
            return
        }
        if(description.isEmpty()) {
            App.INSTANCE.mainInterface.showErrorMessage("Не введено описание категории!")
            return
        }

        var finalPreviewLink = previewImageLink

        if(imageUri != null && finalPreviewLink == "") {
            val url = uploadImage(imageUri = imageUri)

            if(url != null)
                finalPreviewLink = url
            else return
        }

        val repository = CatalogRepository(App.INSTANCE.supabaseClient)

        if(App.INSTANCE.sharedData.editedCategory != null) {
            val outputCategory = Category(
                id = App.INSTANCE.sharedData.editedCategory!!.id,
                name = name,
                description = description,
                previewImageLink = finalPreviewLink
            )

            val result = repository.updateCategory(outputCategory)

            if(result != null) {
                onSaveSucceeded()
            }
        }
        else {
            val outputCategory = CategoryInsert(
                name = name,
                description = description,
                previewImageLink = finalPreviewLink
            )

            val result = repository.insertCategory(outputCategory)

            if(result != null) {
                onSaveSucceeded()
            }
        }
    }

    fun onSaveSucceeded() {
        App.INSTANCE.sharedData.editedCategory = null
        router.exit()
    }

    suspend fun onDeletePressed() {
        val repository = CatalogRepository(App.INSTANCE.supabaseClient)

        val result = repository.deleteCategory(App.INSTANCE.sharedData.editedCategory!!)

        if(result) {
            App.INSTANCE.sharedData.editedCategory = null
            router.exit()
        }
    }
}