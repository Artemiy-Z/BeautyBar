package ru.beauty.bar.navigation.presenter.admin.catalog_edit;

import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter

class CatalogEditOverviewPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }

    fun onCategoriesPressed() {
        router.navigateTo(Screens.CatalogEditCategoriesScreen)
    }

    fun onServicesPressed() {
        router.navigateTo(Screens.CatalogEditServicesScreen)
    }
}