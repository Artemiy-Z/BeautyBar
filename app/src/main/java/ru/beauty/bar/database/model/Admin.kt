package ru.beauty.bar.database.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Admin(
    @SerialName("admin_id")
    val id: Int,

    @SerialName("login")
    val login: String,

    @SerialName("passhash")
    val passhash: String
)