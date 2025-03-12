package scisrc.mobiledev.ecommercelayout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView  // เพิ่ม ImageView
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
    private lateinit var numberOfPeopleEditText: EditText  // จำนวนคน
    private lateinit var confirmButton: Button
    private lateinit var parkSpinner: Spinner  // สปินเนอร์เลือกอุทยาน
    private lateinit var accommodationTypeSpinner: Spinner  // สปินเนอร์เลือกประเภทที่พัก
    private lateinit var parkImageView: ImageView  // เพิ่ม ImageView สำหรับแสดงรูปภาพที่เลือก

    private var selectedPark: ParkModel? = null  // ใช้ ParkModel แทน Booking

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_booking, container, false)

        // เชื่อมต่อกับ UI
        parkNameTextView = view.findViewById(R.id.parkNameTextView)
        priceTextView = view.findViewById(R.id.priceTextView)
        locationTextView = view.findViewById(R.id.locationTextView)
        descriptionTextView = view.findViewById(R.id.descriptionTextView)
        datesEditText = view.findViewById(R.id.datesEditText)
        numberOfPeopleEditText = view.findViewById(R.id.numberOfPeopleEditText)
        confirmButton = view.findViewById(R.id.confirmBookingButton)
        parkSpinner = view.findViewById(R.id.parkSpinner)
        accommodationTypeSpinner = view.findViewById(R.id.accommodationTypeSpinner)
        parkImageView = view.findViewById(R.id.parkImageView)  // เชื่อมต่อ ImageView

        // ตั้งค่า Spinner สำหรับอุทยาน
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

        // ตั้งค่า Spinner สำหรับประเภทที่พัก
        val accommodationTypes = listOf("เต็นท์", "บ้านพัก")
        val accommodationAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, accommodationTypes)
        accommodationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        accommodationTypeSpinner.adapter = accommodationAdapter

        // ฟังก์ชันเมื่อเลือกอุทยานจาก Spinner
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
                        isFavorite = false
                    )
                    "อุทยานแห่งชาติปางสีดา" -> ParkModel(
                        name = "อุทยานแห่งชาติปางสีดา",
                        price = 1000.0,
                        availability = true,
                        location = "สระแก้ว",
                        imageRes = R.drawable.pangsida,
                        description = "อุทยานแห่งชาติปางสีดา มีความหลากหลายของธรรมชาติ ทั้งภูเขาและป่าไม้ เส้นทางเดินป่ามีความท้าทาย พร้อมพื้นที่พักผ่อนในบรรยากาศสงบ",
                        isFavorite = false
                    )
                    "อุทยานแห่งชาติเขาใหญ่" -> ParkModel(
                        name = "อุทยานแห่งชาติเขาใหญ่",
                        price = 1500.0,
                        availability = true,
                        location = "นครราชสีมา",
                        imageRes = R.drawable.khaoyai,
                        description = "ที่พักในอุทยานแห่งชาติเขาใหญ่ อยู่ท่ามกลางธรรมชาติที่อุดมสมบูรณ์ เหมาะสำหรับการเดินป่าและการส่องสัตว์",
                        isFavorite = false
                    )
                    "อุทยานแห่งชาติเอราวัณ" -> ParkModel(
                        name = "อุทยานแห่งชาติเอราวัณ",
                        price = 1300.0,
                        availability = true,
                        location = "กาญจนบุรี",
                        imageRes = R.drawable.erawan,
                        description = "อุทยานแห่งชาติเอราวัณเป็นแหล่งท่องเที่ยวธรรมชาติที่มีชื่อเสียงในเรื่องน้ำตกเอราวัณที่สวยงามและพื้นที่สำหรับเดินป่า",
                        isFavorite = false
                    )
                    "อุทยานแห่งชาติเขาสามร้อยยอด" -> ParkModel(
                        name = "อุทยานแห่งชาติเขาสามร้อยยอด",
                        price = 1100.0,
                        availability = true,
                        location = "ประจวบคีรีขันธ์",
                        imageRes = R.drawable.samroiyot,
                        description = "อุทยานแห่งชาติเขาสามร้อยยอดมีทิวทัศน์ของทะเลและภูเขาที่สวยงาม พร้อมกับเส้นทางเดินป่าและการชมสัตว์ป่า",
                        isFavorite = false
                    )
                    "อุทยานแห่งชาติน้ำตกคลองแก้ว" -> ParkModel(
                        name = "อุทยานแห่งชาติน้ำตกคลองแก้ว",
                        price = 950.0,
                        availability = true,
                        location = "จันทบุรี",
                        imageRes = R.drawable.khlongkaeo,
                        description = "อุทยานแห่งชาติน้ำตกคลองแก้วมีน้ำตกที่สวยงามและพื้นที่ธรรมชาติที่เงียบสงบ เหมาะแก่การท่องเที่ยวเพื่อพักผ่อน",
                        isFavorite = false
                    )
                    "อุทยานแห่งชาติเขาชะเมา-เขาวง" -> ParkModel(
                        name = "อุทยานแห่งชาติเขาชะเมา-เขาวง",
                        price = 980.0,
                        availability = true,
                        location = "ระยอง",
                        imageRes = R.drawable.khaochamao,
                        description = "อุทยานแห่งชาติเขาชะเมา-เขาวงมีเส้นทางเดินป่าที่น่าสนใจ และเป็นแหล่งอนุรักษ์สัตว์ป่าหายาก",
                        isFavorite = false
                    )
                    "อุทยานแห่งชาติน้ำตกสามหลั่น" -> ParkModel(
                        name = "อุทยานแห่งชาติน้ำตกสามหลั่น",
                        price = 1200.0,
                        availability = true,
                        location = "สระบุรี",
                        imageRes = R.drawable.samlan,
                        description = "อุทยานแห่งชาติน้ำตกสามหลั่นมีน้ำตกที่สวยงาม และเหมาะกับการพักผ่อนในบรรยากาศธรรมชาติ",
                        isFavorite = false
                    )
                    "อุทยานแห่งชาติน้ำตกเจ็ดสาวน้อย" -> ParkModel(
                        name = "อุทยานแห่งชาติน้ำตกเจ็ดสาวน้อย",
                        price = 1100.0,
                        availability = true,
                        location = "สระบุรี",
                        imageRes = R.drawable.chetsaonoi,
                        description = "น้ำตกเจ็ดสาวน้อยมีความสวยงามของธรรมชาติและสามารถเดินป่าไปยังน้ำตกต่างๆ",
                        isFavorite = false
                    )
                    "อุทยานแห่งชาติน้ำพอง" -> ParkModel(
                        name = "อุทยานแห่งชาติน้ำพอง",
                        price = 1050.0,
                        availability = true,
                        location = "ขอนแก่น",
                        imageRes = R.drawable.namphong,
                        description = "อุทยานแห่งชาติน้ำพองเหมาะแก่การเดินป่าและศึกษาธรรมชาติในพื้นที่เขียวขจี",
                        isFavorite = false
                    )
                    "อุทยานแห่งชาติป่าหินงาม" -> ParkModel(
                        name = "อุทยานแห่งชาติป่าหินงาม",
                        price = 950.0,
                        availability = true,
                        location = "ชัยภูมิ",
                        imageRes = R.drawable.pahinngam,
                        description = "ป่าหินงามเป็นแหล่งท่องเที่ยวที่มีลักษณะทางธรรมชาติเป็นหินรูปทรงแปลกตา",
                        isFavorite = false
                    )
                    "อุทยานแห่งชาติตะรุเตา - จังหวัดสตูล" -> ParkModel(
                        name = "อุทยานแห่งชาติตะรุเตา - จังหวัดสตูล",
                        price = 1500.0,
                        availability = true,
                        location = "สตูล",
                        imageRes = R.drawable.tarutao,
                        description = "อุทยานแห่งชาติตะรุเตามีชายหาดที่สวยงามและเป็นแหล่งท่องเที่ยวทางทะเลที่ยอดเยี่ยม",
                        isFavorite = false
                    )
                    "อุทยานแห่งชาติศรีพังงา - จังหวัดพังงา" -> ParkModel(
                        name = "อุทยานแห่งชาติศรีพังงา - จังหวัดพังงา",
                        price = 1400.0,
                        availability = true,
                        location = "พังงา",
                        imageRes = R.drawable.sriphangnga,
                        description = "อุทยานแห่งชาติศรีพังงามีทิวทัศน์สวยงามของภูเขาและทะเล เหมาะกับการทำกิจกรรมทางทะเลและการท่องเที่ยว",
                        isFavorite = false
                    )
                    else -> null
                }


                // แสดงข้อมูลที่พักหลังเลือกอุทยาน
                selectedPark?.let {
                    parkNameTextView.text = it.name
                    priceTextView.text = "ราคา: ฿ ${it.price}"
                    locationTextView.text = "สถานที่: ${it.location}"
                    descriptionTextView.text = it.description
                    parkImageView.setImageResource(it.imageRes)  // เปลี่ยนรูปภาพใน ImageView
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}
        }

        confirmButton.setOnClickListener {
            // เมื่อกดยืนยันการจอง
            val numberOfPeople = numberOfPeopleEditText.text.toString()
            val checkInDate = datesEditText.text.toString()

            if (numberOfPeople.isNotEmpty() && checkInDate.isNotEmpty() && selectedPark != null) {
                // เพิ่มการจองที่พัก
                selectedPark?.let {
                    BookingManager.addToBookings(it)
                    showToast("จองที่พักเรียบร้อยแล้ว!")
                }
            } else {
                // ถ้าไม่กรอกข้อมูล
                showToast("กรุณากรอกข้อมูลทั้งหมด")
            }
        }

        return view
    }

    private fun showToast(message: String) {
        // ฟังก์ชันสำหรับแสดง Toast
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}