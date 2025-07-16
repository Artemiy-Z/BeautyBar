package ru.beauty.bar.database.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category (
    @SerialName("category_id")
    val id: Int,

    @SerialName("name")
    val name: String,

    @SerialName("description")
    val description: String,

    @SerialName("preview")
    val previewImageLink: String
)

@Serializable
data class CategoryInsert (
    @SerialName("name")
    val name: String,

    @SerialName("description")
    val description: String,

    @SerialName("preview")
    val previewImageLink: String
)