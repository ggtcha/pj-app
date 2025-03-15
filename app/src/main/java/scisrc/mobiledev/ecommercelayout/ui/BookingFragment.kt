package scisrc.mobiledev.ecommercelayout.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import scisrc.mobiledev.ecommercelayout.ParkModel
import scisrc.mobiledev.ecommercelayout.R

class BookingFragment : Fragment() {

    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var parkListView: ListView
    private lateinit var parkImageView: ImageView
    private lateinit var parkNameTextView: TextView
    private lateinit var locationTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var openingHoursTextView: TextView

    private val parks = listOf(
        ParkModel("อุทยานแห่งชาติบึงฉวาก", 1200.0, true, "สุพรรณบุรี", R.drawable.bungchawak,
            "ที่พักในอุทยานแห่งชาติบึงฉวาก ตั้งอยู่ท่ามกลางธรรมชาติ", false, "08:00 - 18:00 น.", "1,500 - 3,000 บาท/คืน", "200 - 500 บาท/คืน"),
        ParkModel("อุทยานแห่งชาติปางสีดา", 1000.0, true, "สระแก้ว", R.drawable.pangsida,
            "มีความหลากหลายของธรรมชาติ ทั้งภูเขาและป่าไม้", false, "06:00 - 18:00 น.", "1,200 - 2,500 บาท/คืน", "150 - 400 บาท/คืน"),
        ParkModel("อุทยานแห่งชาติเขาใหญ่", 1500.0, true, "นครราชสีมา", R.drawable.khaoyai,
            "ที่พักในอุทยานแห่งชาติเขาใหญ่ อยู่ท่ามกลางธรรมชาติที่อุดมสมบูรณ์", false, "06:00 - 18:00 น.", "2,000 - 4,000 บาท/คืน", "300 - 600 บาท/คืน"),
        ParkModel("อุทยานแห่งชาติเอราวัณ", 1200.0, true, "กาญจนบุรี", R.drawable.erawan,
            "อุทยานที่มีน้ำตกสวยงาม 7 ชั้น", false, "08:00 - 17:00 น.", "1,500 - 3,000 บาท/คืน", "200 - 500 บาท/คืน"),
        ParkModel("อุทยานแห่งชาติเขาสามร้อยยอด", 1100.0, true, "ประจวบคีรีขันธ์", R.drawable.samroiyot,
            "มีทั้งภูเขา ถ้ำ และชายหาด", false, "06:00 - 18:00 น.", "1,200 - 2,800 บาท/คืน", "200 - 450 บาท/คืน"),
        ParkModel("อุทยานแห่งชาติน้ำตกคลองแก้ว", 1000.0, true, "ตราด", R.drawable.khlongkaeo,
            "น้ำตกที่เงียบสงบและธรรมชาติบริสุทธิ์", false, "07:00 - 17:00 น.", "1,000 - 2,500 บาท/คืน", "150 - 400 บาท/คืน"),
        ParkModel("อุทยานแห่งชาติเขาชะเมา-เขาวง", 1300.0, true, "ระยอง", R.drawable.khaochamao,
            "น้ำตกและป่าเขาที่เหมาะสำหรับการเดินป่า", false, "06:00 - 18:00 น.", "1,500 - 3,000 บาท/คืน", "250 - 550 บาท/คืน"),
        ParkModel("อุทยานแห่งชาติน้ำตกสามหลั่น", 900.0, true, "สระบุรี", R.drawable.samlan,
            "น้ำตกขนาดเล็กในบรรยากาศเงียบสงบ", false, "08:00 - 17:00 น.", "1,000 - 2,000 บาท/คืน", "150 - 350 บาท/คืน"),
        ParkModel("อุทยานแห่งชาติน้ำตกเจ็ดสาวน้อย", 1100.0, true, "สระบุรี", R.drawable.chetsaonoi,
            "น้ำตกที่มี 7 ชั้น เหมาะสำหรับการพักผ่อน", false, "08:00 - 17:00 น.", "1,200 - 2,500 บาท/คืน", "200 - 450 บาท/คืน"),
        ParkModel("อุทยานแห่งชาติน้ำพอง", 1000.0, true, "ขอนแก่น", R.drawable.namphong,
            "ป่าเขียวขจีและอ่างเก็บน้ำ", false, "06:00 - 18:00 น.", "1,000 - 2,200 บาท/คืน", "150 - 400 บาท/คืน"),
        ParkModel("อุทยานแห่งชาติป่าหินงาม", 1300.0, true, "ชัยภูมิ", R.drawable.pahinngam,
            "ทุ่งดอกกระเจียวและหินรูปทรงแปลกตา", false, "06:00 - 18:00 น.", "1,500 - 3,000 บาท/คืน", "250 - 550 บาท/คืน"),
        ParkModel("อุทยานแห่งชาติตะรุเตา - จังหวัดสตูล", 1400.0, true, "สตูล", R.drawable.tarutao,
            "หมู่เกาะในทะเลอันดaman", false, "08:00 - 17:00 น.", "1,800 - 3,500 บาท/คืน", "300 - 600 บาท/คืน"),
        ParkModel("อุทยานแห่งชาติศรีพังงา - จังหวัดพังงา", 1200.0, true, "พังงา", R.drawable.sriphangnga,
            "น้ำตกและป่าเขตร้อนชื้น", false, "07:00 - 17:00 น.", "1,500 - 3,000 บาท/คืน", "250 - 500 บาท/คืน")
    )

    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_booking, container, false)

        try {
            // Initialize views
            searchEditText = view.findViewById(R.id.search_edit_text)
            searchButton = view.findViewById(R.id.search_button)
            parkListView = view.findViewById(R.id.park_list_view)
            parkImageView = view.findViewById(R.id.park_image)
            parkNameTextView = view.findViewById(R.id.park_name)
            locationTextView = view.findViewById(R.id.location_text)
            descriptionTextView = view.findViewById(R.id.description_text)
            openingHoursTextView = view.findViewById(R.id.opening_hours_text)

            // Set up initial adapter with all parks
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                parks.map { it.name }
            )
            parkListView.adapter = adapter

            // Show first park as default
            if (parks.isNotEmpty()) {
                val defaultPark = parks[0]
                parkImageView.setImageResource(defaultPark.imageRes)
                parkNameTextView.text = defaultPark.name
                locationTextView.text = defaultPark.location
                descriptionTextView.text = defaultPark.description
                openingHoursTextView.text = "เวลาเปิด-ปิด: ${defaultPark.openingHours}"
            }

            // Search button click
            searchButton.setOnClickListener {
                val query = searchEditText.text.toString().trim()
                filterParks(query)
            }

            // ListView item click
            parkListView.setOnItemClickListener { _, _, position, _ ->
                val filteredParks = getFilteredParks(searchEditText.text.toString().trim())
                val selectedPark = filteredParks[position]
                parkImageView.setImageResource(selectedPark.imageRes)
                parkNameTextView.text = selectedPark.name
                locationTextView.text = selectedPark.location
                descriptionTextView.text = selectedPark.description
                openingHoursTextView.text = "เวลาเปิด-ปิด: ${selectedPark.openingHours}"

                val detailsFragment = DetailsFragment()
                val bundle = Bundle()
                bundle.putSerializable("selected_park", selectedPark)
                detailsFragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailsFragment)
                    .addToBackStack(null)
                    .commit()
            }
        } catch (e: Exception) {
            Log.e("BookingFragment", "Error in onCreateView: ${e.message}")
            Toast.makeText(context, "เกิดข้อผิดพลาด: ${e.message}", Toast.LENGTH_LONG).show()
        }

        return view
    }

    // Filter parks based on search query
    private fun filterParks(query: String) {
        val filteredParks = getFilteredParks(query)
        adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            filteredParks.map { it.name }
        )
        parkListView.adapter = adapter

        // Update UI with first filtered result if available
        if (filteredParks.isNotEmpty()) {
            val firstPark = filteredParks[0]
            parkImageView.setImageResource(firstPark.imageRes)
            parkNameTextView.text = firstPark.name
            locationTextView.text = firstPark.location
            descriptionTextView.text = firstPark.description
            openingHoursTextView.text = "เวลาเปิด-ปิด: ${firstPark.openingHours}"
        } else {
            parkNameTextView.text = "ไม่พบอุทยาน"
            locationTextView.text = ""
            descriptionTextView.text = ""
            openingHoursTextView.text = ""
            parkImageView.setImageResource(android.R.drawable.ic_menu_search) // Placeholder
        }
    }

    // Get filtered parks list
    private fun getFilteredParks(query: String): List<ParkModel> {
        return if (query.isEmpty()) {
            parks
        } else {
            parks.filter { it.name.contains(query, ignoreCase = true) }
        }
    }
}