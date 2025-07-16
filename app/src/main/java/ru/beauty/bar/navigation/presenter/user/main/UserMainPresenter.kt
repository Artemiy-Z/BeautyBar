package ru.beauty.bar.navigation.presenter.user.main;

import ru.beauty.bar.App
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter

class UserMainPresenter : BasePresenter() {
    override fun onBackPressed() {
        App.INSTANCE.mainInterface.showChoiceMessage(
            message = "Вы точно хотите выйти?",
            dismissCallback = {},
            confirmCallback = {
                router.newRootScreen(Screens.UserLoginScreen)
                App.INSTANCE.sharedData.userData = null
                App.INSTANCE.sharedData.userType = null
            }
        )
    }

    fun onAccountPressed() {
        router.navigateTo(Screens.UserAccountViewScreen)
    }

    fun onCatalogPressed() {
        router.navigateTo(Screens.CatalogCategoriesScreen)
    }

    fun onBookingsPressed() {
        router.navigateTo(Screens.UserBookingsScreen)
    }
}