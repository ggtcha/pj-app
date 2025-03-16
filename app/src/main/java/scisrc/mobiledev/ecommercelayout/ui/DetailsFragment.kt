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
    private lateinit var dateTextView: TextView
    private lateinit var peopleEditText: EditText
    private lateinit var priceText: TextView
    private lateinit var nextButton: Button
    private lateinit var parkNameText: TextView

    private lateinit var selectedPark: ParkModel
    private var selectedDate: String? = null
    private var selectedAccommodation: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        // ✅ รับค่าจาก arguments (ป้องกัน null)
        selectedPark = requireArguments().getSerializable("selected_park") as? ParkModel
            ?: throw IllegalArgumentException("Selected park is required")

        // ✅ ผูก UI กับตัวแปร
        parkImage = view.findViewById(R.id.park_image)
        parkNameText = view.findViewById(R.id.park_name_text)
        accommodationSpinner = view.findViewById(R.id.accommodation_spinner)
        dateTextView = view.findViewById(R.id.date_text_view)
        peopleEditText = view.findViewById(R.id.people_count)
        priceText = view.findViewById(R.id.price_text)
        nextButton = view.findViewById(R.id.next_button)

        // ✅ แสดงชื่ออุทยาน
        parkNameText.text = "อุทยาน: ${selectedPark.name}"

        // ✅ โหลดรูปภาพของอุทยาน
        Glide.with(this)
            .load(selectedPark.imageRes)
            .into(parkImage)

        // ✅ ตั้งค่า Spinner สำหรับที่พัก
        val accommodationTypes = listOf("เต็นท์", "บ้านพัก")
        val accommodationAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            accommodationTypes
        )
        accommodationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        accommodationSpinner.adapter = accommodationAdapter

        // ✅ ตั้งค่าเริ่มต้น
        selectedAccommodation = accommodationTypes[0]
        priceText.text = "ราคา: ${selectedPark.tentPrice}"

        // ✅ อัปเดตราคาเมื่อเลือกที่พัก
        accommodationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedAccommodation = accommodationTypes[position]
                priceText.text = if (position == 0) {
                    "ราคา: ${selectedPark.tentPrice}"
                } else {
                    "ราคา: ${selectedPark.bungalowPrice}"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // ✅ เปิด DatePickerDialog เมื่อกดที่ `dateTextView`
        dateTextView.setOnClickListener {
            showDatePickerDialog()
        }

        nextButton.setOnClickListener {
            handleBooking()
        }

        return view
    }

    // ✅ ฟังก์ชันเปิด DatePickerDialog
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
            dateTextView.text = selectedDate
        }, year, month, day)

        datePickerDialog.show()
    }

    // ✅ ฟังก์ชันกดปุ่ม "ยืนยันการจอง"
    private fun handleBooking() {
        val people = peopleEditText.text.toString().trim()
        val date = selectedDate ?: ""

        if (people.isEmpty()) {
            Toast.makeText(context, "กรุณาระบุจำนวนคน", Toast.LENGTH_SHORT).show()
            return
        }

        if (date.isEmpty()) {
            Toast.makeText(context, "กรุณาเลือกวันที่", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedAccommodation == null) {
            Toast.makeText(context, "กรุณาเลือกที่พัก", Toast.LENGTH_SHORT).show()
            return
        }

        nextButton.isEnabled = false // ป้องกันการกดซ้ำ

        val paymentFragment = PaymentFragment()
        val bundle = Bundle()
        bundle.putSerializable("selected_park", selectedPark)
        bundle.putString("accommodation", selectedAccommodation)
        bundle.putString("date", date)
        bundle.putString("people", people)
        paymentFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, paymentFragment)
            .addToBackStack(null)
            .commit()
    }
}
