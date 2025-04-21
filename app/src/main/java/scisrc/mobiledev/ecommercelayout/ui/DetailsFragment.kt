package scisrc.mobiledev.ecommercelayout.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import scisrc.mobiledev.ecommercelayout.ParkModel
import scisrc.mobiledev.ecommercelayout.R
import java.util.*

class DetailsFragment : Fragment() {

    private lateinit var parkImage: ImageView
    private lateinit var accommodationSpinner: Spinner
    private lateinit var peopleEditText: EditText
    private lateinit var priceText: TextView
    private lateinit var nextButton: Button
    private lateinit var parkNameText: TextView
    private lateinit var checkInDateTextView: TextView
    private lateinit var checkOutDateTextView: TextView

    private lateinit var selectedPark: ParkModel
    private var selectedCheckInDate: String? = null
    private var selectedCheckOutDate: String? = null
    private var selectedAccommodation: String? = null
    private var selectedPricePerNight: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        selectedPark = requireArguments().getSerializable("selected_park") as ParkModel

        // ผูก UI
        parkImage = view.findViewById(R.id.park_image)
        parkNameText = view.findViewById(R.id.park_name_text)
        accommodationSpinner = view.findViewById(R.id.accommodation_spinner)
        checkInDateTextView = view.findViewById(R.id.check_in_date_text_view)
        checkOutDateTextView = view.findViewById(R.id.check_out_date_text_view)
        peopleEditText = view.findViewById(R.id.people_count)
        priceText = view.findViewById(R.id.price_text)
        nextButton = view.findViewById(R.id.next_button)

        parkNameText.text = "อุทยาน: ${selectedPark.name}"

        // โหลดรูปภาพของอุทยาน
        Glide.with(this)
            .load(selectedPark.imageRes)
            .into(parkImage)

        // ตั้งค่า Spinner สำหรับที่พัก
        val accommodationTypes = listOf("เต็นท์", "บ้านพัก")
        val accommodationAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            accommodationTypes
        )
        accommodationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        accommodationSpinner.adapter = accommodationAdapter

        selectedAccommodation = accommodationTypes[0]
        selectedPricePerNight = selectedPark.tentPrice.replace(" บาท/คืน", "").toDouble()
        priceText.text = "ราคา: ฿ ${selectedPricePerNight}"

        // คำนวณราคาหลังจากเลือกที่พัก
        accommodationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedAccommodation = accommodationTypes[position]
                if (position == 0) {
                    selectedPricePerNight = selectedPark.tentPrice.replace(" บาท/คืน", "").toDouble()
                    priceText.text = "ราคา: ฿ ${selectedPricePerNight}"
                } else {
                    selectedPricePerNight = selectedPark.bungalowPrice.replace(" บาท/คืน", "").toDouble()
                    priceText.text = "ราคา: ฿ ${selectedPricePerNight}"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // เลือกวันที่
        checkInDateTextView.setOnClickListener {
            showDatePickerDialog(true) // true คือเลือกวันเข้าพัก
        }

        nextButton.setOnClickListener {
            handleBooking()
        }

        return view
    }

    // ฟังก์ชันเปิด DatePickerDialog
    private fun showDatePickerDialog(isCheckIn: Boolean) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
            if (isCheckIn) {
                selectedCheckInDate = selectedDate
                checkInDateTextView.text = "วันที่เข้าพัก: $selectedDate"
                showDatePickerDialog(false) // ให้เลือกวันที่ออก
            } else {
                selectedCheckOutDate = selectedDate
                checkOutDateTextView.text = "วันที่ออก: $selectedDate"
                calculatePrice()
            }
        }, year, month, day)

        datePickerDialog.show()
    }

    // ฟังก์ชันคำนวณราคา
    private fun calculatePrice() {
        if (selectedCheckInDate != null && selectedCheckOutDate != null) {
            val checkInCalendar = Calendar.getInstance()
            val checkOutCalendar = Calendar.getInstance()

            val checkInDateArray = selectedCheckInDate!!.split("-")
            val checkOutDateArray = selectedCheckOutDate!!.split("-")

            checkInCalendar.set(checkInDateArray[0].toInt(), checkInDateArray[1].toInt() - 1, checkInDateArray[2].toInt())
            checkOutCalendar.set(checkOutDateArray[0].toInt(), checkOutDateArray[1].toInt() - 1, checkOutDateArray[2].toInt())

            val daysBetween = ((checkOutCalendar.timeInMillis - checkInCalendar.timeInMillis) / (1000 * 60 * 60 * 24)).toInt()

            if (daysBetween > 0) {
                val totalPrice = selectedPricePerNight * daysBetween
                priceText.text = "ราคา: ฿ $totalPrice"
            }
        }
    }

    // ฟังก์ชันกดปุ่ม "ยืนยันการจอง"
    private fun handleBooking() {
        val people = peopleEditText.text.toString().trim()
        if (people.isEmpty()) {
            Toast.makeText(context, "กรุณาระบุจำนวนคน", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedCheckInDate == null || selectedCheckOutDate == null) {
            Toast.makeText(context, "กรุณาเลือกวันที่เข้าพักและออก", Toast.LENGTH_SHORT).show()
            return
        }

        val paymentFragment = PaymentFragment()
        val bundle = Bundle()
        bundle.putSerializable("selected_park", selectedPark)
        bundle.putString("accommodation", selectedAccommodation)
        bundle.putString("check_in_date", selectedCheckInDate)
        bundle.putString("check_out_date", selectedCheckOutDate)
        bundle.putString("people", people)
        paymentFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, paymentFragment)
            .addToBackStack(null)
            .commit()
    }
}
