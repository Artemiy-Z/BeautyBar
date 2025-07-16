package ru.beauty.bar.dataLayer

import ru.beauty.bar.database.model.Category
import ru.beauty.bar.database.model.Master
import ru.beauty.bar.database.model.Service
import ru.beauty.bar.database.model.User

class BookingOrder {
    var bookingId: Int? = null
    var user: User? = null
    var masterCombined: MasterScheduleCombined? = null
    var service: Service? = null
    var category: Category? = null
    var dateMills: Long? = null
    var timeMills: Long? = null
    var fullMills: Long? = null
}