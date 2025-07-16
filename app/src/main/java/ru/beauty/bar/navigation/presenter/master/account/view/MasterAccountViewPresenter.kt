package ru.beauty.bar.navigation.presenter.master.account.view;

import ru.beauty.bar.App
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter

class MasterAccountViewPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }

    fun onEditPressed() {
        router.navigateTo(Screens.MasterAccountEditScreen)
    }
}