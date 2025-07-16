package ru.beauty.bar.database.api

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import ru.beauty.bar.App
import ru.beauty.bar.database.model.Booking
import ru.beauty.bar.database.model.BookingInsert
import ru.beauty.bar.database.model.Master
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale

class BookingRepository(val client: SupabaseClient) {
    suspend fun insertBooking(bookingInsert: BookingInsert): Booking? {
        App.INSTANCE.mainInterface.toggleLoading(true)
        try {
            val result = client
                .from("BOOKING")
                .insert(bookingInsert) {
                    select()
                }
                .decodeSingle<Booking>()

            App.INSTANCE.mainInterface.toggleLoading(false)
            return result
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.toggleLoading(false)
            App.INSTANCE.mainInterface.showErrorMessage(e.localizedMessage)
            return null
        }
    }

    suspend fun selectBookingListUser(userId: Int): List<Booking>? {
        App.INSTANCE.mainInterface.toggleLoading(true)
        try {
            val result = client
                .from("BOOKING")
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<Booking>()

            App.INSTANCE.mainInterface.toggleLoading(false)
            return result
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.showErrorMessage(e.localizedMessage)

            App.INSTANCE.mainInterface.toggleLoading(false)
            return null
        }
    }

    suspend fun selectBookingListAll(): List<Booking>? {
        App.INSTANCE.mainInterface.toggleLoading(true)
        try {
            val result = client
                .from("BOOKING")
                .select()
                .decodeList<Booking>()

            App.INSTANCE.mainInterface.toggleLoading(false)
            return result
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.showErrorMessage(e.localizedMessage)

            App.INSTANCE.mainInterface.toggleLoading(false)
            return null
        }
    }

    suspend fun selectBookingListByDateAndMaster(dateMills: Long, master: Master): List<Booking> {
        App.INSTANCE.mainInterface.toggleLoading(true)
        try {
            val result = client
                .from("BOOKING")
                .select {
                    filter {
                        eq("master_id", master.id)
                    }
                }
                .decodeList<Booking>()

            val array = ArrayList<Booking>()

            for(item in result) {
                val sdf = SimpleDateFormat("yyyy-dd-MM'T'HH:mm:ss", Locale.getDefault())

                val fullMills = sdf.parse(item.bookingDateTime)?.time ?: continue

                val originalInstant = Instant.ofEpochMilli(dateMills)
                val fullInstant = Instant.ofEpochMilli(fullMills)
                val originalDate = LocalDateTime.ofInstant(originalInstant, ZoneId.systemDefault())
                val fullDate = LocalDateTime.ofInstant(fullInstant, ZoneId.systemDefault())

                if(originalDate.dayOfYear == fullDate.dayOfYear) {
                    array.add(item)
                }
            }

            App.INSTANCE.mainInterface.toggleLoading(false)
            return array.toList()
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.showErrorMessage(e.localizedMessage)

            App.INSTANCE.mainInterface.toggleLoading(false)
            return emptyList()
        }
    }

    suspend fun deleteBooking(bookingId: Int): Boolean {
        App.INSTANCE.mainInterface.toggleLoading(true)
        try {
            val result = client
                .from("BOOKING")
                .delete {
                    filter {
                        eq("booking_id", bookingId)
                    }
                }

            App.INSTANCE.mainInterface.toggleLoading(false)
            return true
        }
        catch (e: Exception) {
            App.INSTANCE.mainInterface.showErrorMessage(e.localizedMessage)

            App.INSTANCE.mainInterface.toggleLoading(false)
            return false
        }
    }
}