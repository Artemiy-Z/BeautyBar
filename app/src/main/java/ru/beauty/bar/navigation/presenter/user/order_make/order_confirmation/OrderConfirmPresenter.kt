package ru.beauty.bar.navigation.presenter.user.order_make.order_confirmation

import ru.beauty.bar.App
import ru.beauty.bar.database.api.BookingRepository
import ru.beauty.bar.database.model.BookingInsert
import ru.beauty.bar.database.model.User
import ru.beauty.bar.navigation.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

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

        val sdf = SimpleDateFormat("yyyy-dd-MM HH:mm:ss", Locale.getDefault())

        val dateFormatted = sdf.format(fullMills)

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