package ru.beauty.bar.database.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Master(
    @SerialName("master_id")
    val id: Int,

    @SerialName("login")
    val login: String,

    @SerialName("passhash")
    val passhash: String,

    @SerialName("name")
    val name: String,

    @SerialName("picture")
    val pictueLink: String,

    @SerialName("scheme_id")
    val workScheduleId: Int,

    @SerialName("experience")
    val experience: Int,

    @SerialName("portfolio")
    val portfloio: String
)

@Serializable
data class MasterInsert(
    @SerialName("login")
    val login: String,

    @SerialName("passhash")
    val passhash: String,

    @SerialName("name")
    val name: String,

    @SerialName("picture")
    val pictueLink: String,

    @SerialName("scheme_id")
    val workScheduleId: Int,

    @SerialName("experience")
    val experience: Int,

    @SerialName("portfolio")
    val portfloio: String
)