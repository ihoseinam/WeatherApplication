package ir.hoseinahmadi.weatherapplication.data.model

import kotlinx.serialization.Serializable

@Serializable
data class City(
    val id: Int,
    val title: String,
    val slug: String,
    val province_id: Int,
    val latitude: Double,
    val longitude: Double
)
