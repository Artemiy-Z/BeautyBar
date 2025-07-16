package ru.beauty.bar.navigation.presenter.admin.catalog_edit.services;

import android.net.Uri
import ru.beauty.bar.App
import ru.beauty.bar.dataLayer.grantAppAccess
import ru.beauty.bar.database.api.CatalogRepository
import ru.beauty.bar.database.api.StorageRepository
import ru.beauty.bar.database.model.Category
import ru.beauty.bar.database.model.CategoryInsert
import ru.beauty.bar.database.model.Service
import ru.beauty.bar.database.model.ServiceInsert
import ru.beauty.bar.navigation.presenter.BasePresenter

class EditServicePresenter : BasePresenter() {
    override fun onBackPressed() {
        App.INSTANCE.mainInterface.showChoiceMessage(
            message = "Отменить?",
            confirmCallback = {
                App.INSTANCE.sharedData.editedService = null
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
        category: Category,
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

        if(App.INSTANCE.sharedData.editedService != null) {
            val outputService = Service(
                id = App.INSTANCE.sharedData.editedService!!.id,
                name = name,
                description = description,
                previewImageLink = finalPreviewLink,
                categoryId = category.id
            )

            val result = repository.updateService(outputService)

            if(result != null) {
                onSaveSucceeded()
            }
        }
        else {
            val outputService = ServiceInsert(
                name = name,
                description = description,
                previewImageLink = finalPreviewLink,
                categoryId = category.id
            )

            val result = repository.insertService(outputService)

            if(result != null) {
                onSaveSucceeded()
            }
        }
    }

    fun onSaveSucceeded() {
        App.INSTANCE.sharedData.editedService = null
        router.exit()
    }

    suspend fun onDeletePressed() {
        val repository = CatalogRepository(App.INSTANCE.supabaseClient)

        val result = repository.deleteService(App.INSTANCE.sharedData.editedService!!)

        if(result) {
            App.INSTANCE.sharedData.editedService = null
            App.INSTANCE.sharedData.editedCategory = null
            router.exit()
        }
    }
}