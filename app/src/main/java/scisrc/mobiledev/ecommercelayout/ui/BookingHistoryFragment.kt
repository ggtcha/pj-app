package scisrc.mobiledev.ecommercelayout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import scisrc.mobiledev.ecommercelayout.R

class BookingHistoryFragment : Fragment() {

    private lateinit var parkName: TextView
    private lateinit var accommodation: TextView
    private lateinit var dates: TextView
    private lateinit var peopleCount: TextView
    private lateinit var totalPrice: TextView
    private lateinit var paymentStatus: TextView  // เพิ่มตัวแปรใหม่

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_booking_history, container, false)

        parkName = view.findViewById(R.id.park_name)
        accommodation = view.findViewById(R.id.accommodation)
        dates = view.findViewById(R.id.dates)
        peopleCount = view.findViewById(R.id.people_count)
        totalPrice = view.findViewById(R.id.total_price)
        paymentStatus = view.findViewById(R.id.payment_status)  // หา TextView สำหรับแสดงสถานะการชำระเงิน

        // ดึงข้อมูลจาก SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("BookingHistory", 0)
        val selectedPark = sharedPreferences.getString("selected_park", "Unknown Park")
        val selectedAccommodation = sharedPreferences.getString("accommodation", "Unknown")
        val checkInDate = sharedPreferences.getString("check_in_date", "Unknown")
        val checkOutDate = sharedPreferences.getString("check_out_date", "Unknown")
        val people = sharedPreferences.getString("people", "Unknown")
        val price = sharedPreferences.getString("total_price", "0.0")
        val payment = sharedPreferences.getString("payment_status", "ชำระเงินเรียบร้อยแล้ว")  // ดึงสถานะการชำระเงิน

        // แสดงข้อมูลการจอง
        parkName.text = "อุทยาน: $selectedPark"
        accommodation.text = "ที่พัก: $selectedAccommodation"
        dates.text = "วันที่เข้า-ออก: $checkInDate - $checkOutDate"
        peopleCount.text = "จำนวนผู้เข้าพัก: $people"
        totalPrice.text = "ราคารวมทั้งหมด: $price"
        paymentStatus.text = "สถานะการชำระเงิน: $payment"  // แสดงสถานะการชำระเงิน

        return view
    }
}
