package scisrc.mobiledev.ecommercelayout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import scisrc.mobiledev.ecommercelayout.ParkModel
import scisrc.mobiledev.ecommercelayout.R
import java.util.Calendar


class PaymentFragment : Fragment() {

    private lateinit var paymentSpinner: Spinner
    private lateinit var summaryText: TextView
    private lateinit var confirmButton: Button
    private lateinit var bankAccountText: TextView
    private lateinit var qrImageView: ImageView
    private lateinit var totalPriceText: TextView // เพิ่ม TextView สำหรับแสดงราคาทั้งหมด

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_payment, container, false)

        // รับค่าจาก arguments
        val selectedPark = arguments?.getSerializable("selected_park") as ParkModel
        val accommodation = arguments?.getString("accommodation")
        val checkInDate = arguments?.getString("check_in_date")
        val checkOutDate = arguments?.getString("check_out_date")
        val people = arguments?.getString("people")

        paymentSpinner = view.findViewById(R.id.payment_spinner)
        summaryText = view.findViewById(R.id.summary_text)
        confirmButton = view.findViewById(R.id.confirm_button)
        bankAccountText = view.findViewById(R.id.bank_account_text)
        qrImageView = view.findViewById(R.id.qr_code_image)
        totalPriceText = view.findViewById(R.id.total_price_text) // ผูกกับ TextView ที่แสดงราคาทั้งหมด

        // เลือกราคา per night
        val pricePerNight = if (accommodation == "เต็นท์") {
            selectedPark.tentPrice.replace(" บาท/คืน", "").toDouble()
        } else {
            selectedPark.bungalowPrice.replace(" บาท/คืน", "").toDouble()
        }

        // แสดงข้อมูลสรุป
        summaryText.text = """
            อุทยาน: ${selectedPark.name}
            ที่พัก: $accommodation
            วันที่: $checkInDate - $checkOutDate
            จำนวนคน: $people
            ราคา: ฿ $pricePerNight บาท/คืน
        """.trimIndent()

        // คำนวณราคาโดยใช้วันที่เข้าพักและวันที่ออก
        val totalPrice = calculateTotalPrice(checkInDate, checkOutDate, pricePerNight)
        totalPriceText.text = "ราคาทั้งหมด: ฿ $totalPrice"

        val paymentMethods = listOf("พร้อมเพย์ (QR Code)", "โอนเงินธนาคาร")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            paymentMethods
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        paymentSpinner.adapter = adapter

        paymentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    bankAccountText.visibility = View.GONE
                    qrImageView.visibility = View.VISIBLE
                    qrImageView.setImageResource(R.drawable.qr_code_sample)
                } else {
                    qrImageView.visibility = View.GONE
                    bankAccountText.visibility = View.VISIBLE
                    bankAccountText.text = "โอนเงินมาที่: 123-456-789 ธนาคาร A"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // เมื่อกดปุ่มยืนยันการจอง
        // หลังจากกดยืนยันการจองแล้ว
        confirmButton.setOnClickListener {
            // เก็บข้อมูลการจองใน SharedPreferences
            val sharedPreferences = requireActivity().getSharedPreferences("BookingHistory", 0)
            val editor = sharedPreferences.edit()
            editor.putString("selected_park", selectedPark.name)
            editor.putString("accommodation", accommodation)
            editor.putString("check_in_date", checkInDate)
            editor.putString("check_out_date", checkOutDate)
            editor.putString("people", people)
            editor.putString("total_price", totalPriceText.text.toString())
            editor.apply()

            Toast.makeText(context, "ยืนยันการจองเรียบร้อย!", Toast.LENGTH_LONG).show()

            // เปลี่ยนไปที่หน้า BookingHistoryFragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BookingHistoryFragment())
                .commit()
        }


        return view
    }

    // ฟังก์ชันคำนวณราคาทั้งหมด
    private fun calculateTotalPrice(checkInDate: String?, checkOutDate: String?, pricePerNight: Double): Double {
        if (checkInDate != null && checkOutDate != null) {
            val checkInCalendar = Calendar.getInstance()
            val checkOutCalendar = Calendar.getInstance()

            val checkInDateArray = checkInDate.split("-")
            val checkOutDateArray = checkOutDate.split("-")

            checkInCalendar.set(checkInDateArray[0].toInt(), checkInDateArray[1].toInt() - 1, checkInDateArray[2].toInt())
            checkOutCalendar.set(checkOutDateArray[0].toInt(), checkOutDateArray[1].toInt() - 1, checkOutDateArray[2].toInt())

            val daysBetween = ((checkOutCalendar.timeInMillis - checkInCalendar.timeInMillis) / (1000 * 60 * 60 * 24)).toInt()

            if (daysBetween > 0) {
                return pricePerNight * daysBetween
            }
        }
        return 0.0
    }
}
