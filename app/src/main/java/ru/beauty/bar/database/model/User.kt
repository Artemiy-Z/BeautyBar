package ru.beauty.bar.database.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("user_id")
    val id: Int?,

    @SerialName("login")
    val login: String,

    @SerialName("passhash")
    val passhash: String,

    @SerialName("name")
    val name: String,

    @SerialName("picture")
    val pictueLink: String
) {
    override fun toString(): String {
        return "id = $id, login = $login, name = $name"
    }
}

@Serializable
data class UserInsert(
    @SerialName("login")
    val login: String,

    @SerialName("passhash")
    val passhash: String,

    @SerialName("name")
    val name: String,

    @SerialName("picture")
    val pictueLink: String
)