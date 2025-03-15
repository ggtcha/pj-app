package scisrc.mobiledev.ecommercelayout

import java.io.Serializable

data class ParkModel(
    val name: String,
    val price: Double,
    val availability: Boolean,
    val location: String,
    val imageRes: Int,
    val description: String,
    val isFavorite: Boolean,
    val openingHours: String, // เพิ่มฟิลด์เวลาเปิดปิด
    val bungalowPrice: String, // เพิ่มฟิลด์ราคาบ้านพัก
    val tentPrice: String // เพิ่มฟิลด์ราคาเต็นท์
) : Serializable
