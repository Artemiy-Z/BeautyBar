package ru.beauty.bar.database.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkSchedule(
    @SerialName("scheme_id")
    val id: Int,

    @SerialName("sunday")
    val sunday: Boolean,

    @SerialName("monday")
    val monday: Boolean,

    @SerialName("tuesday")
    val tuesday: Boolean,

    @SerialName("wednesday")
    val wednesday: Boolean,

    @SerialName("thursday")
    val thursday: Boolean,

    @SerialName("friday")
    val friday: Boolean,

    @SerialName("saturday")
    val saturday: Boolean
)

@Serializable
data class WorkScheduleInsert(
    @SerialName("sunday")
    val sunday: Boolean,

    @SerialName("monday")
    val monday: Boolean,

    @SerialName("tuesday")
    val tuesday: Boolean,

    @SerialName("wednesday")
    val wednesday: Boolean,

    @SerialName("thursday")
    val thursday: Boolean,

    @SerialName("friday")
    val friday: Boolean,

    @SerialName("saturday")
    val saturday: Boolean
)