package ru.beauty.bar.navigation.presenter.admin.catalog_edit.services;

import com.github.terrakok.cicerone.ResultListener
import ru.beauty.bar.App
import ru.beauty.bar.database.api.CatalogRepository
import ru.beauty.bar.database.model.Category
import ru.beauty.bar.database.model.Service
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter

class CatalogEditServicesPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }

    suspend fun loadServices(category: Category?): List<Service> {
        if(category == null) {
            return emptyList()
        }

        val repository = CatalogRepository(App.INSTANCE.supabaseClient)

        val list = repository.selectServices(category)

        return list
    }

    fun onEditPressed(
        category: Category?,
        service: Service,
        onDataUpdated: (() -> Unit) = {}
    ) {
        if(category == null) {
            App.INSTANCE.mainInterface.showErrorMessage(message = "Не выбрана категория!")
            return
        }

        router.setResultListener(
            key = "UPDATE_DATA",
            listener = ResultListener(
                function = {
                    App.INSTANCE.sharedData.editedCategory = null
                    onDataUpdated()
                }
            )
        )

        App.INSTANCE.sharedData.editedService = service
        App.INSTANCE.sharedData.editedCategory = category
        router.navigateTo(Screens.EditServiceScreen)
    }

    fun onAddPressed(
        category: Category?,
        onDataUpdated: (() -> Unit) = {}
    ) {
        if(category == null) {
            App.INSTANCE.mainInterface.showErrorMessage(message = "Не выбрана категория!")
            return
        }

        router.setResultListener(
            key = "UPDATE_DATA",
            listener = ResultListener(
                function = {
                    App.INSTANCE.sharedData.editedCategory = null
                    onDataUpdated()
                }
            )
        )

        App.INSTANCE.sharedData.editedService = null
        App.INSTANCE.sharedData.editedCategory = category
        router.navigateTo(Screens.EditServiceScreen)
    }

    suspend fun loadCategories(): List<Category> {
        val repository = CatalogRepository(App.INSTANCE.supabaseClient)
        val list = repository.selectCategories()
        return list
    }
}