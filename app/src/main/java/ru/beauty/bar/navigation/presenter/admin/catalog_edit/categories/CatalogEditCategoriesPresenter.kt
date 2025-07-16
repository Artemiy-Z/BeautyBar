package ru.beauty.bar.navigation.presenter.admin.catalog_edit.categories;

import com.github.terrakok.cicerone.ResultListener
import ru.beauty.bar.App
import ru.beauty.bar.database.api.CatalogRepository
import ru.beauty.bar.database.api.ClientFactory
import ru.beauty.bar.database.model.Category
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter

class CatalogEditCategoriesPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }

    fun onEditPressed(
        category: Category,
        onDataUpdated: (() -> Unit) = {}
    ) {
        router.setResultListener(
            key = "UPDATE_DATA",
            listener = ResultListener(
                function = {
                    App.INSTANCE.sharedData.editedCategory = null
                    onDataUpdated()
                }
            )
        )

        App.INSTANCE.sharedData.editedCategory = category
        router.navigateTo(Screens.EditCategoryScreen)
    }

    fun onAddPressed(
        onDataUpdated: (() -> Unit) = {}
    ) {
        router.setResultListener(
            key = "UPDATE_DATA",
            listener = ResultListener(
                function = {
                    App.INSTANCE.sharedData.editedCategory = null
                    onDataUpdated()
                }
            )
        )

        App.INSTANCE.sharedData.editedCategory = null
        router.navigateTo(Screens.EditCategoryScreen)
    }

    suspend fun loadCategories(): List<Category> {
        val repository = CatalogRepository(App.INSTANCE.supabaseClient)
        val list = repository.selectCategories()
        return list
    }
}