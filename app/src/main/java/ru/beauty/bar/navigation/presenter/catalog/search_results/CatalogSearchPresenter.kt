package ru.beauty.bar.navigation.presenter.catalog.search_results;

import ru.beauty.bar.App
import ru.beauty.bar.database.model.Service
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter

class CatalogSearchPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }

    fun onServiceSelected(service: Service) {
        App.INSTANCE.sharedData.curService = service

        router.navigateTo(Screens.CatalogServiceScreen)
    }
}