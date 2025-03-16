package scisrc.mobiledev.ecommercelayout.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import scisrc.mobiledev.ecommercelayout.ParkModel
import scisrc.mobiledev.ecommercelayout.R

class BookingFragment : Fragment() {

    private lateinit var searchEditText: EditText
    private lateinit var parkRecyclerView: RecyclerView
    private lateinit var parkAdapter: ParkAdapter

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
    private var filteredParks = parks.toMutableList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_booking, container, false)

        searchEditText = view.findViewById(R.id.search_edit_text)
        parkRecyclerView = view.findViewById(R.id.park_recycler_view)

        parkRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // ✅ ใช้ Adapter ที่รองรับการอัปเดตข้อมูล
        parkAdapter = ParkAdapter(filteredParks) { selectedPark ->
            goToDetailsFragment(selectedPark)
        }
        parkRecyclerView.adapter = parkAdapter

        // ✅ เพิ่มการฟังการเปลี่ยนแปลงของข้อความในช่องค้นหา
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterParks(s.toString().trim())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        return view
    }

    // ✅ ฟังก์ชันค้นหาอุทยาน
    private fun filterParks(query: String) {
        filteredParks = if (query.isEmpty()) {
            parks.toMutableList()
        } else {
            parks.filter { it.name.contains(query, ignoreCase = true) }.toMutableList()
        }

        // ✅ อัปเดตรายการที่แสดง
        parkAdapter.updateList(filteredParks)
    }

    private fun goToDetailsFragment(selectedPark: ParkModel) {
        val detailsFragment = DetailsFragment()
        val bundle = Bundle()
        bundle.putSerializable("selected_park", selectedPark)
        detailsFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailsFragment)
            .addToBackStack(null)
            .commit()
    }
}
