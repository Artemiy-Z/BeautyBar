package ru.beauty.bar.database.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Service(
    @SerialName("service_id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("description")
    val description: String,

    @SerialName("preview")
    val previewImageLink: String,

    @SerialName("category_id")
    val categoryId: Int
)

@Serializable
data class ServiceInsert(
    @SerialName("name")
    val name: String,

    @SerialName("description")
    val description: String,

    @SerialName("preview")
    val previewImageLink: String,

    @SerialName("category_id")
    val categoryId: Int
)