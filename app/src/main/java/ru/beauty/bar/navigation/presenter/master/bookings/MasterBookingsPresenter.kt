package ru.beauty.bar.navigation.presenter.master.bookings;

import ru.beauty.bar.App
import ru.beauty.bar.navigation.presenter.BasePresenter

class MasterBookingsPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }
}