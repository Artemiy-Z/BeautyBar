package ru.beauty.bar.navigation.presenter.catalog.search_results

import ru.beauty.bar.navigation.presenter.BasePresenter

class CatalogSearchPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }
}