package ru.beauty.bar.dataLayer

import ru.beauty.bar.database.model.Master
import ru.beauty.bar.database.model.WorkSchedule

class MasterScheduleCombined(
    val master: Master? = null,
    val schedule: WorkSchedule? = null
) {
    fun scheduleString(): String {
        var string = ""
        if(schedule?.monday == true)
            string += "Пн,"
        if(schedule?.tuesday == true)
            string += "Вт,"
        if(schedule?.wednesday == true)
            string += "Ср,"
        if(schedule?.thursday == true)
            string += "Чт,"
        if(schedule?.friday == true)
            string += "Пт,"
        if(schedule?.saturday == true)
            string += "Сб,"
        if(schedule?.sunday == true)
            string += "Вс,"
        return string.trimEnd(',')
    }
}