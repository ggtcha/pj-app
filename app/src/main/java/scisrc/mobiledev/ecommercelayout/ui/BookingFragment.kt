package scisrc.mobiledev.ecommercelayout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import scisrc.mobiledev.ecommercelayout.BookingManager
import scisrc.mobiledev.ecommercelayout.ParkModel
import scisrc.mobiledev.ecommercelayout.R

class BookingFragment : Fragment() {

    private lateinit var parkNameTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var locationTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var datesEditText: EditText
    private lateinit var numberOfPeopleEditText: EditText
    private lateinit var confirmButton: Button
    private lateinit var parkSpinner: Spinner
    private lateinit var accommodationTypeSpinner: Spinner
    private lateinit var parkImageView: ImageView
    private lateinit var openingHoursTextView: TextView
    private lateinit var bungalowPriceTextView: TextView
    private lateinit var tentPriceTextView: TextView

    private var selectedPark: ParkModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_booking, container, false)

        parkNameTextView = view.findViewById(R.id.parkNameTextView)
        priceTextView = view.findViewById(R.id.priceTextView)
        locationTextView = view.findViewById(R.id.locationTextView)
        descriptionTextView = view.findViewById(R.id.descriptionTextView)
        datesEditText = view.findViewById(R.id.datesEditText)
        numberOfPeopleEditText = view.findViewById(R.id.numberOfPeopleEditText)
        confirmButton = view.findViewById(R.id.confirmBookingButton)
        parkSpinner = view.findViewById(R.id.parkSpinner)
        accommodationTypeSpinner = view.findViewById(R.id.accommodationTypeSpinner)
        parkImageView = view.findViewById(R.id.parkImageView)

        // เพิ่มการเชื่อมโยง TextView สำหรับเปิดปิดเวลา, ราคาบ้านพัก, และราคาเต็นท์
        openingHoursTextView = view.findViewById(R.id.openingHoursTextView)
        bungalowPriceTextView = view.findViewById(R.id.bungalowPriceTextView)
        tentPriceTextView = view.findViewById(R.id.tentPriceTextView)

        val parks = listOf(
            "อุทยานแห่งชาติบึงฉวาก",
            "อุทยานแห่งชาติปางสีดา",
            "อุทยานแห่งชาติเขาใหญ่",
            "อุทยานแห่งชาติเอราวัณ",
            "อุทยานแห่งชาติเขาสามร้อยยอด",
            "อุทยานแห่งชาติน้ำตกคลองแก้ว",
            "อุทยานแห่งชาติเขาชะเมา-เขาวง",
            "อุทยานแห่งชาติน้ำตกสามหลั่น",
            "อุทยานแห่งชาติน้ำตกเจ็ดสาวน้อย",
            "อุทยานแห่งชาติน้ำพอง",
            "อุทยานแห่งชาติป่าหินงาม",
            "อุทยานแห่งชาติตะรุเตา - จังหวัดสตูล",
            "อุทยานแห่งชาติศรีพังงา - จังหวัดพังงา"
        )

        val parkAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, parks)
        parkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        parkSpinner.adapter = parkAdapter

        val accommodationTypes = listOf("เต็นท์", "บ้านพัก")
        val accommodationAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, accommodationTypes)
        accommodationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        accommodationTypeSpinner.adapter = accommodationAdapter

        parkSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedParkName = parks[position]
                selectedPark = when (selectedParkName) {
                    "อุทยานแห่งชาติบึงฉวาก" -> ParkModel(
                        name = "อุทยานแห่งชาติบึงฉวาก",
                        price = 1200.0,
                        availability = true,
                        location = "สุพรรณบุรี",
                        imageRes = R.drawable.bungchawak,
                        description = "ที่พักในอุทยานแห่งชาติบึงฉวาก ตั้งอยู่ท่ามกลางธรรมชาติ อากาศบริสุทธิ์ เหมาะสำหรับการพักผ่อนแบบครอบครัวและการเดินป่าศึกษาธรรมชาติ",
                        isFavorite = false,
                        openingHours = "08:00 - 18:00 น.",
                        bungalowPrice = "1,500 - 3,000 บาท/คืน",
                        tentPrice = "200 - 500 บาท/คืน"
                    )
                    "อุทยานแห่งชาติปางสีดา" -> ParkModel(
                        name = "อุทยานแห่งชาติปางสีดา",
                        price = 1000.0,
                        availability = true,
                        location = "สระแก้ว",
                        imageRes = R.drawable.pangsida,
                        description = "อุทยานแห่งชาติปางสีดา มีความหลากหลายของธรรมชาติ ทั้งภูเขาและป่าไม้ เส้นทางเดินป่ามีความท้าทาย พร้อมพื้นที่พักผ่อนในบรรยากาศสงบ",
                        isFavorite = false,
                        openingHours = "06:00 - 18:00 น.",
                        bungalowPrice = "1,200 - 2,500 บาท/คืน",
                        tentPrice = "150 - 400 บาท/คืน"
                    )
                    "อุทยานแห่งชาติเขาใหญ่" -> ParkModel(
                        name = "อุทยานแห่งชาติเขาใหญ่",
                        price = 1500.0,
                        availability = true,
                        location = "นครราชสีมา",
                        imageRes = R.drawable.khaoyai,
                        description = "ที่พักในอุทยานแห่งชาติเขาใหญ่ อยู่ท่ามกลางธรรมชาติที่อุดมสมบูรณ์ เหมาะสำหรับการเดินป่าและการส่องสัตว์",
                        isFavorite = false,
                        openingHours = "06:00 - 18:00 น.",
                        bungalowPrice = "2,000 - 4,000 บาท/คืน",
                        tentPrice = "300 - 600 บาท/คืน"
                    )
                    else -> null
                }
                selectedPark?.let {
                    parkImageView.setImageResource(it.imageRes)
                    parkNameTextView.text = it.name
                    priceTextView.text = "ราคา: ฿ ${it.price}"
                    locationTextView.text = "สถานที่: ${it.location}"
                    descriptionTextView.text = it.description
                    openingHoursTextView.text = "เวลาเปิดปิด: ${it.openingHours}"
                    bungalowPriceTextView.text = "ราคาบ้านพัก: ${it.bungalowPrice}"
                    tentPriceTextView.text = "ราคาเต็นท์: ${it.tentPrice}"
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

        accommodationTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, view: View?, position: Int, id: Long) {
                val accommodationType = accommodationTypes[position]
                selectedPark?.let {
                    when (accommodationType) {
                        "เต็นท์" -> priceTextView.text = "ราคาเต็นท์: ฿ ${it.tentPrice}"
                        "บ้านพัก" -> priceTextView.text = "ราคาบ้านพัก: ฿ ${it.bungalowPrice}"
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

        confirmButton.setOnClickListener {
            val numberOfPeople = numberOfPeopleEditText.text.toString()
            val checkInDate = datesEditText.text.toString()

            if (numberOfPeople.isNotEmpty() && checkInDate.isNotEmpty() && selectedPark != null) {
                selectedPark?.let {
                    BookingManager.addToBookings(it)
                    showToast("จองที่พักเรียบร้อยแล้ว!")
                }
            } else {
                showToast("กรุณากรอกข้อมูลทั้งหมด")
            }
        }

        return view
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
