package scisrc.mobiledev.ecommercelayout

object FavoritesManager {
    private val favoriteList = mutableListOf<ParkModel>() // เปลี่ยนเป็น ParkModel

    // เพิ่มบ้านพักเข้าไปในรายการสิ่งที่สนใจ
    fun addToFavorites(park: ParkModel) {
        if (!favoriteList.contains(park)) {
            favoriteList.add(park)
        }
    }

    // ลบบ้านพักออกจากรายการสิ่งที่สนใจ
    fun removeFromFavorites(park: ParkModel) {
        favoriteList.remove(park)
    }

    // ดึงรายการบ้านพักที่ถูกบันทึกไว้
    fun getFavorites(): List<ParkModel> {
        return favoriteList
    }

    // เช็คว่าบ้านพักนั้นอยู่ในรายการสิ่งที่สนใจหรือไม่
    fun isFavorite(park: ParkModel): Boolean {
        return favoriteList.contains(park)
    }
}
