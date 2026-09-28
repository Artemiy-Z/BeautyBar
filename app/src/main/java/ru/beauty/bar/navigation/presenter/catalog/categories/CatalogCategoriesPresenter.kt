package ru.beauty.bar.navigation.presenter.catalog.categories

import ru.beauty.bar.App
import ru.beauty.bar.database.api.CatalogRepository
import ru.beauty.bar.database.model.Category
import ru.beauty.bar.navigation.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter

class CatalogCategoriesPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }

    fun onCategorySelected(category: Category) {
        App.INSTANCE.sharedData.curCategory = category

        router.navigateTo(Screens.CatalogCategoryScreen)
    }

    suspend fun loadCategories(): List<Category> {
        val repository = CatalogRepository(App.INSTANCE.supabaseClient)
        val list = repository.selectCategories()
        return list
    }
}