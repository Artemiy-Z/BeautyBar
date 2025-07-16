package ru.beauty.bar.navigation.presenter.admin.main;

import ru.beauty.bar.App
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter

class AdminMainPresenter : BasePresenter() {
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

    fun onBookingsPressed() {
        router.navigateTo(Screens.AdminBookingsScreen)
    }

    fun onMastersPressed() {
        router.navigateTo(Screens.AdminMastersScreen)
    }

    fun onUsersPressed() {
        router.navigateTo(Screens.AdminUsersScreen)
    }

    fun onCatalogPressed() {
        router.navigateTo(Screens.CatalogEditOverviewScreen)
    }
}