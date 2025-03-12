package scisrc.mobiledev.ecommercelayout

import android.net.Uri

object BookingManager {

    // รายการการจองทั้งหมด
    private val bookingItems = mutableListOf<ParkModel>()
    private var bookingStatus: String = "ชำระเงิน"  // เริ่มต้นที่สถานะการจ่ายเงิน
    private var paymentProofUri: Uri? = null // เพิ่มตัวแปรสำหรับเก็บ URI ของภาพหลักฐานการชำระเงิน

    // ฟังก์ชันใหม่
    fun getBookingItems(): List<ParkModel> = bookingItems

    fun addToBookings(park: ParkModel) {
        bookingItems.add(park)
    }

    fun removeFromBookings(park: ParkModel) {
        bookingItems.remove(park)
    }

    fun clearBookings() {
        bookingItems.clear()
        setBookingStatus("คืนบ้านพัก")  // เมื่อการจองเสร็จสมบูรณ์
    }

    // ฟังก์ชันใหม่สำหรับการจัดการสถานะการจอง
    fun getBookingStatus(): String = bookingStatus

    fun setBookingStatus(status: String) {
        bookingStatus = status
    }

    // ฟังก์ชันใหม่สำหรับการเก็บหลักฐานการชำระเงิน
    fun getPaymentProof(): Uri? = paymentProofUri  // ดึงข้อมูลหลักฐาน

    fun setPaymentProof(uri: Uri) {
        paymentProofUri = uri  // เก็บ URI ของหลักฐาน
    }
}
