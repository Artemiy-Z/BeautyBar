package ru.beauty.bar.navigation.presenter.admin.bookings;

import ru.beauty.bar.App
import ru.beauty.bar.dataLayer.BookingOrder
import ru.beauty.bar.dataLayer.BookingOrderText
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

    suspend fun loadBookings(): List<BookingOrderText> {
        val repository = BookingRepository(App.INSTANCE.supabaseClient)

        val list = repository.selectBookingListAll()

        val array = ArrayList<BookingOrderText>()

        if (list != null) {
            for(item: Booking in list) {
                val master = MasterRepository(App.INSTANCE.supabaseClient)
                    .selectSingleMaster(item.masterId)!!

                val catalogRepository = CatalogRepository(App.INSTANCE.supabaseClient)
                val userRepository = UserRepository(App.INSTANCE.supabaseClient)

                val service = catalogRepository.selectSingleService(item.serviceId)!!
                val category = catalogRepository.selectSingleCategory(service.categoryId)!!
                val user = userRepository.selectSingleUserById(item.userId)

                val sdf = SimpleDateFormat("yyyy-dd-MM HH:mm:ss", Locale.getDefault())
                val sdfDate = SimpleDateFormat("yyyy-dd-MM", Locale.getDefault())
                val sdfTime = SimpleDateFormat("HH:mm", Locale.getDefault())

                val fullMills = sdf.parse(item.bookingDateTime)?.time

                val order = BookingOrderText(
                    masterName = master.name,
                    service = service.name,
                    category = category.name,
                    date = sdfDate.format(fullMills),
                    time = sdfTime.format(fullMills),
                    bookingId = item.id,
                    userName = user?.name?:"_"
                )

                array.add(
                    order
                )
            }
        }

        return array.toList()
    }
}