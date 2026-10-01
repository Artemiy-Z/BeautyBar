package ru.beauty.bar.navigation.presenter.user.bookings

import ru.beauty.bar.App
import ru.beauty.bar.dataLayer.BookingOrder
import ru.beauty.bar.dataLayer.BookingOrderText
import ru.beauty.bar.dataLayer.MasterScheduleCombined
import ru.beauty.bar.database.api.BookingRepository
import ru.beauty.bar.database.api.CatalogRepository
import ru.beauty.bar.database.api.MasterRepository
import ru.beauty.bar.database.model.Booking
import ru.beauty.bar.database.model.User
import ru.beauty.bar.navigation.Screens
import ru.beauty.bar.navigation.presenter.BasePresenter
import java.text.SimpleDateFormat
import java.util.Locale

class UserBookingsPresenter : BasePresenter() {
    override fun onBackPressed() {
        router.exit()
    }

    suspend fun loadBookings(): List<BookingOrderText> {
        val userId = (App.INSTANCE.sharedData.userData as User).id!!

        val repository = BookingRepository(App.INSTANCE.supabaseClient)

        val list = repository.selectBookingListUser(userId = userId)

        val array = ArrayList<BookingOrderText>()

        if (list != null) {
            for(item: Booking in list) {
                val master = MasterRepository(App.INSTANCE.supabaseClient)
                    .selectSingleMaster(item.masterId)!!

                val catalogRepository = CatalogRepository(App.INSTANCE.supabaseClient)

                val service = catalogRepository.selectSingleService(item.serviceId)!!
                val category = catalogRepository.selectSingleCategory(service.categoryId)!!

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
                    bookingId = item.id
                )

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