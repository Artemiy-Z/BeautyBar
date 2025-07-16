package ru.beauty.bar.navigation.presenter.user.bookings;

import ru.beauty.bar.App
import ru.beauty.bar.dataLayer.BookingOrder
import ru.beauty.bar.dataLayer.MasterScheduleCombined
import ru.beauty.bar.database.api.BookingRepository
import ru.beauty.bar.database.api.CatalogRepository
import ru.beauty.bar.database.api.MasterRepository
import ru.beauty.bar.database.model.Booking
import ru.beauty.bar.database.model.Master
import ru.beauty.bar.navigation.general.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter
import java.text.SimpleDateFormat
import java.util.Locale

class UserBookingsPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }

    suspend fun loadBookings(
        userId: Int
    ): List<BookingOrder> {
        val repository = BookingRepository(App.INSTANCE.supabaseClient)

        val list = repository.selectBookingListUser(userId = userId)

        val array = ArrayList<BookingOrder>()

        if (list != null) {
            for(item: Booking in list) {
                val master = MasterRepository(App.INSTANCE.supabaseClient)
                    .selectSingleMaster(item.masterId)!!

                val catalogRepository = CatalogRepository(App.INSTANCE.supabaseClient)

                val service = catalogRepository.selectSingleService(item.serviceId)!!
                val category = catalogRepository.selectSingleCategory(service.categoryId)!!

                val sdf = SimpleDateFormat("yyyy-dd-MM'T'HH:mm:ss", Locale.getDefault())

                val fullMills = sdf.parse(item.bookingDateTime)?.time

                val order = BookingOrder()

                order.masterCombined = MasterScheduleCombined(master = master)
                order.service = service
                order.category = category
                order.fullMills = fullMills
                order.bookingId = item.id

                array.add(
                    order
                )
            }
        }

        return array.toList()
    }

    suspend fun onCancelBookingPressed(bookingId: Int) {
        val repository = BookingRepository(App.INSTANCE.supabaseClient)

        val result = repository.deleteBooking(bookingId)

        if(result) {
            router.exit()
            router.navigateTo(Screens.UserBookingsScreen)
        }
    }
}