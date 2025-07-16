package ru.beauty.bar.navigation.presenter.user.order_make.order_confirmation;

import ru.beauty.bar.App
import ru.beauty.bar.database.api.BookingRepository
import ru.beauty.bar.database.model.BookingInsert
import ru.beauty.bar.database.model.User
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class OrderConfirmPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }

    fun onCancelPressed() {
        App.INSTANCE.sharedData.orderProgress = null
        App.INSTANCE.sharedData.curCategory = null
        App.INSTANCE.sharedData.curService = null

        router.backTo(Screens.UserMainScreen)
    }

    suspend fun onConfirmed() {
        val order = App.INSTANCE.sharedData.orderProgress!!

        val fullMills = order.dateMills!!+order.timeMills!!

        val dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

        val instant = Instant.ofEpochMilli(fullMills)

        val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

        val dateFormatted = dtf.format(date)

        val booking = BookingInsert(
            masterId = order.masterCombined!!.master!!.id,
            userId = (App.INSTANCE.sharedData.userData!! as User).id!!,
            serviceId = order.service!!.id,
            bookingDateTime = dateFormatted
        )

        val repository = BookingRepository(App.INSTANCE.supabaseClient)

        val result = repository.insertBooking(booking)

        if(result != null) {
            App.INSTANCE.sharedData.orderProgress = null
            App.INSTANCE.sharedData.curCategory = null
            App.INSTANCE.sharedData.curService = null

            router.backTo(Screens.UserMainScreen)
        }
    }
}