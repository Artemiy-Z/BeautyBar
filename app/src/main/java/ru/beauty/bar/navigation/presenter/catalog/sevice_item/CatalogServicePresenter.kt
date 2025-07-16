package ru.beauty.bar.navigation.presenter.catalog.sevice_item;

import ru.beauty.bar.App
import ru.beauty.bar.dataLayer.BookingOrder
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter

class CatalogServicePresenter : BasePresenter() {
    override fun onBackPressed() {
        App.INSTANCE.sharedData.curService = null

        router.exit()
    }

    fun onBookingCreatePressed() {
        App.INSTANCE.sharedData.orderProgress = BookingOrder()

        App.INSTANCE.sharedData.orderProgress?.service = App.INSTANCE.sharedData.curService

        router.navigateTo(Screens.OrderMasterSelectScreen)
    }
}