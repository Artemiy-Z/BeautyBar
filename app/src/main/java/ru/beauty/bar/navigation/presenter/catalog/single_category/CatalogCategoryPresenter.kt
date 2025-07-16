package ru.beauty.bar.navigation.presenter.catalog.single_category;

import ru.beauty.bar.App
import ru.beauty.bar.database.api.CatalogRepository
import ru.beauty.bar.database.model.Category
import ru.beauty.bar.database.model.Service
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter

class CatalogCategoryPresenter : BasePresenter() {
    override fun onBackPressed() {
        App.INSTANCE.sharedData.curCategory = null

        router.exit()
    }

    fun onServiceSelected(service: Service) {
        App.INSTANCE.sharedData.curService = service

        router.navigateTo(Screens.CatalogServiceScreen)
    }

    suspend fun loadServices(category: Category): List<Service> {
        val repository = CatalogRepository(App.INSTANCE.supabaseClient)

        val list = repository.selectServices(category)

        return list
    }
}