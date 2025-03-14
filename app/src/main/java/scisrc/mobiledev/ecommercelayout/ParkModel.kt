package scisrc.mobiledev.ecommercelayout

import java.io.Serializable

data class ParkModel(
    val name: String,
    val price: Double,
    val availability: Boolean,
    val location: String? = null,
    val imageRes: Int,
    val description: String,
    var isFavorite: Boolean = false,
) : Serializable
