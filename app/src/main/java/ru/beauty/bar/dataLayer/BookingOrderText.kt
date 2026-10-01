package ru.beauty.bar.dataLayer

import ru.beauty.bar.database.model.Category
import ru.beauty.bar.database.model.Service
import ru.beauty.bar.database.model.User

data class BookingOrderText(
    val bookingId: Int,
    var masterName: String,
    var service: String,
    var category: String,
    val date: String,
    val time: String,
    val userName: String = ""
)