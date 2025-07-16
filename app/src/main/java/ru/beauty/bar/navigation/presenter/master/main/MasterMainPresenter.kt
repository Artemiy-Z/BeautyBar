package ru.beauty.bar.navigation.presenter.master.main;

import ru.beauty.bar.App
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter

class MasterMainPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.newRootScreen(Screens.UserLoginScreen)
        App.INSTANCE.sharedData.userData = null
        App.INSTANCE.sharedData.userType = null
    }

    fun onAccountPressed() {
        router.navigateTo(Screens.MasterAccountViewScreen)
    }

    fun onBookingsPressed() {
        router.navigateTo(Screens.MasterBookingsScreen)
    }

    fun onCatalogPressed() {
        router.navigateTo(Screens.CatalogCategoriesScreen)
    }
}