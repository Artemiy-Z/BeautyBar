package ru.beauty.bar.ui.common

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
class SelectableDatesList(val dates: Iterable<Int>) : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        if (Calendar.getInstance().timeInMillis > utcTimeMillis)
            return false

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = utcTimeMillis

        return dates.contains(calendar.get(Calendar.DAY_OF_WEEK))
    }
}