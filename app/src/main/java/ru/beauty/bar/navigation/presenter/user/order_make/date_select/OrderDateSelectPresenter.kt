package ru.beauty.bar.navigation.presenter.user.order_make.date_select;

import ru.beauty.bar.App
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter

class OrderDateSelectPresenter : BasePresenter() {
    override fun onBackPressed() {
        App.INSTANCE.sharedData.orderProgress?.dateMills = null

        router.exit()
    }

    fun onTimeSelectPressed(
        date: Long
    ) {
        App.INSTANCE.sharedData.orderProgress?.dateMills = date

        router.navigateTo(Screens.OrderTimeSelectScreen)
    }
}