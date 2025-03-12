package scisrc.mobiledev.ecommercelayout

object BookingManager {

    // รายการการจองทั้งหมด
    private val bookingItems = mutableListOf<ParkModel>()

    // ดึงข้อมูลการจองทั้งหมด
    fun getBookingItems(): List<ParkModel> {
        return bookingItems
    }

    // เพิ่มบ้านพักเข้ามาในรายการจอง
    fun addToBookings(park: ParkModel) {
        bookingItems.add(park)
    }

    // ลบบ้านพักออกจากรายการจอง
    fun removeFromBookings(park: ParkModel) {
        bookingItems.remove(park)
    }

    // ล้างข้อมูลการจองทั้งหมด
    fun clearBookings() {
        bookingItems.clear()
    }
}


