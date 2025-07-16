package ru.beauty.bar.navigation.presenter.user.order_make.time_select;

import ru.beauty.bar.App
import ru.beauty.bar.database.api.BookingRepository
import ru.beauty.bar.database.model.Booking
import ru.beauty.bar.database.model.Master
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class OrderTimeSelectPresenter : BasePresenter() {
    override fun onBackPressed() {
        App.INSTANCE.sharedData.orderProgress?.timeMills = null

        router.exit()
    }

    fun onConfirmPressed(
        time: Long
    ) {
        App.INSTANCE.sharedData.orderProgress?.timeMills = time

        router.navigateTo(Screens.OrderConfirmScreen)
    }

    suspend fun loadBookingsByDate(dateMills: Long?, master: Master): List<Booking> {
        if(dateMills == null)
            return emptyList()

        val repository = BookingRepository(App.INSTANCE.supabaseClient)

        return repository.selectBookingListByDateAndMaster(dateMills, master)
    }
}