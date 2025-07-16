package ru.beauty.bar.database.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class Booking(
    @SerialName("booking_id")
    val id: Int = -1,

    @SerialName("master_id")
    val masterId: Int = -1,

    @SerialName("user_id")
    val userId: Int = -1,

    @SerialName("service_id")
    val serviceId: Int = -1,

    @SerialName("booking_date")
    val bookingDateTime: String = ""
)

@Serializable
data class BookingInsert(
    @SerialName("master_id")
    val masterId: Int = -1,

    @SerialName("user_id")
    val userId: Int = -1,

    @SerialName("service_id")
    val serviceId: Int = -1,

    @SerialName("booking_date")
    val bookingDateTime: String = ""
)