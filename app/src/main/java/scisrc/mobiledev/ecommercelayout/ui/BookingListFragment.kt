package scisrc.mobiledev.ecommercelayout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import scisrc.mobiledev.ecommercelayout.BookingManager
import scisrc.mobiledev.ecommercelayout.FavoritesManager
import scisrc.mobiledev.ecommercelayout.ParkModel
import scisrc.mobiledev.ecommercelayout.R

class BookingListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ParkAdapter
    private lateinit var searchEditText: TextInputEditText
    private val parkList = mutableListOf<ParkModel>()
    private val filteredParkList = mutableListOf<ParkModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_booking_list, container, false)

        // Initialize views
        recyclerView = view.findViewById(R.id.recycler_booking_list)
        val searchLayout = view.findViewById<TextInputLayout>(R.id.search_layout)
        searchEditText = searchLayout.editText as TextInputEditText // Get the EditText from TextInputLayout

        recyclerView.layoutManager = LinearLayoutManager(context)

        // Mock Data
        parkList.apply {
            add(ParkModel("อุทยานแห่งชาติบึงฉวาก", 1200.0, true, "สุพรรณบุรี", R.drawable.bungchawak, "ที่พักในอุทยานแห่งชาติบึงฉวาก ตั้งอยู่ท่ามกลางธรรมชาติ อากาศบริสุทธิ์ เหมาะสำหรับการพักผ่อนแบบครอบครัวและการเดินป่าศึกษาธรรมชาติ"))
            add(ParkModel("อุทยานแห่งชาติปางสีดา", 1000.0, true, "สระแก้ว", R.drawable.pangsida, "อุทยานแห่งชาติปางสีดา มีความหลากหลายของธรรมชาติ ทั้งภูเขาและป่าไม้ เส้นทางเดินป่ามีความท้าทาย พร้อมพื้นที่พักผ่อนในบรรยากาศสงบ"))
            add(ParkModel("อุทยานแห่งชาติเขาใหญ่", 1500.0, true, "นครราชสีมา", R.drawable.khaoyai, "ที่พักในอุทยานแห่งชาติเขาใหญ่ อยู่ท่ามกลางธรรมชาติที่อุดมสมบูรณ์ เหมาะสำหรับการเดินป่าและการส่องสัตว์"))
            add(ParkModel("อุทยานแห่งชาติเอราวัณ", 1300.0, true, "กาญจนบุรี", R.drawable.erawan, "อุทยานแห่งชาติเอราวัณเป็นแหล่งท่องเที่ยวธรรมชาติที่มีชื่อเสียงในเรื่องน้ำตกเอราวัณที่สวยงามและพื้นที่สำหรับเดินป่า"))
            add(ParkModel("อุทยานแห่งชาติเขาสามร้อยยอด", 1100.0, true, "ประจวบคีรีขันธ์", R.drawable.samroiyot, "อุทยานแห่งชาติเขาสามร้อยยอดมีทิวทัศน์ของทะเลและภูเขาที่สวยงาม พร้อมกับเส้นทางเดินป่าและการชมสัตว์ป่า"))
        }

        // Initialize filtered list with all parks
        filteredParkList.addAll(parkList)

        // Set up adapter
        adapter = ParkAdapter(filteredParkList, { park -> toggleFavorite(park) }, { park -> bookPark(park) })
        recyclerView.adapter = adapter

        // Add search functionality
        searchEditText.addTextChangedListener { text ->
            filterParks(text.toString())
        }

        return view
    }

    // Filter parks based on search query
    private fun filterParks(query: String?) {
        filteredParkList.clear()
        if (query.isNullOrEmpty()) {
            filteredParkList.addAll(parkList)
        } else {
            val searchQuery = query.lowercase()
            filteredParkList.addAll(parkList.filter { park ->
                park.name?.lowercase()?.contains(searchQuery) ?: false ||
                        park.location?.lowercase()?.contains(searchQuery) ?: false ||
                        park.description?.lowercase()?.contains(searchQuery) ?: false
            })
        }
        adapter.notifyDataSetChanged()
    }

    private fun toggleFavorite(park: ParkModel) {
        park.isFavorite = !park.isFavorite
        if (park.isFavorite) {
            FavoritesManager.addToFavorites(park)
        } else {
            FavoritesManager.removeFromFavorites(park)
        }
        adapter.notifyDataSetChanged()
    }

    private fun bookPark(park: ParkModel) {
        if (park.availability) {
            BookingManager.addToBookings(park)
            showToast("จองที่พัก ${park.name} เรียบร้อยแล้ว!")
        } else {
            showToast("ขออภัย, ${park.name} ไม่มีที่ว่างแล้ว")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}