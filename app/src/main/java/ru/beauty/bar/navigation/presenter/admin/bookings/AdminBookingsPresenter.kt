package ru.beauty.bar.navigation.presenter.admin.bookings;

import ru.beauty.bar.App
import ru.beauty.bar.dataLayer.BookingOrder
import ru.beauty.bar.dataLayer.MasterScheduleCombined
import ru.beauty.bar.database.api.BookingRepository
import ru.beauty.bar.database.api.CatalogRepository
import ru.beauty.bar.database.api.MasterRepository
import ru.beauty.bar.database.api.UserRepository
import ru.beauty.bar.database.model.Booking
import ru.beauty.bar.navigation.presenter.BasePresenter
import java.text.SimpleDateFormat
import java.util.Locale

class AdminBookingsPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }

    suspend fun loadBookings(): List<BookingOrder> {
        val repository = BookingRepository(App.INSTANCE.supabaseClient)

        val list = repository.selectBookingListAll()

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
                order.user = UserRepository(App.INSTANCE.supabaseClient).selectSingleUserById(item.userId)

                array.add(
                    order
                )
            }
        }

        return array.toList()
    }
}