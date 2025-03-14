package scisrc.mobiledev.ecommercelayout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import scisrc.mobiledev.ecommercelayout.ParkModel
import scisrc.mobiledev.ecommercelayout.R

class ParkDetailFragment : Fragment() {

    private lateinit var parkImageView: ImageView
    private lateinit var parkNameTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var locationTextView: TextView
    private lateinit var descriptionTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_park_detail, container, false)

        parkImageView = view.findViewById(R.id.parkImageView)
        parkNameTextView = view.findViewById(R.id.parkNameTextView)
        priceTextView = view.findViewById(R.id.priceTextView)
        locationTextView = view.findViewById(R.id.locationTextView)
        descriptionTextView = view.findViewById(R.id.descriptionTextView)

        val parkName = arguments?.getString("parkName")

        // ค้นหาข้อมูลอุทยานจาก parkName
        val park = findParkByName(parkName)

        // แสดงข้อมูลอุทยาน
        park?.let {
            parkImageView.setImageResource(it.imageRes)
            parkNameTextView.text = it.name
            priceTextView.text = "ราคา: ฿ ${it.price}"
            locationTextView.text = "สถานที่: ${it.location}"
            descriptionTextView.text = it.description
        }

        return view
    }

    private fun findParkByName(parkName: String?): ParkModel? {
        // ค้นหาข้อมูลอุทยานจาก parkName ในรายการอุทยาน
        val parkList = listOf(
            ParkModel(
                name = "อุทยานแห่งชาติบึงฉวาก",
                price = 1200.0,
                availability = true,
                location = "สุพรรณบุรี",
                imageRes = R.drawable.bungchawak,
                description = "ที่พักในอุทยานแห่งชาติบึงฉวาก ตั้งอยู่ท่ามกลางธรรมชาติ อากาศบริสุทธิ์ เหมาะสำหรับการพักผ่อนแบบครอบครัวและการเดินป่าศึกษาธรรมชาติ",
                isFavorite = false
            ),
            ParkModel(
                name = "อุทยานแห่งชาติปางสีดา",
                price = 1000.0,
                availability = true,
                location = "สระแก้ว",
                imageRes = R.drawable.pangsida,
                description = "อุทยานแห่งชาติปางสีดา มีความหลากหลายของธรรมชาติ ทั้งภูเขาและป่าไม้ เส้นทางเดินป่ามีความท้าทาย พร้อมพื้นที่พักผ่อนในบรรยากาศสงบ",
                isFavorite = false
            ),
            ParkModel(
                name = "อุทยานแห่งชาติเขาใหญ่",
                price = 1500.0,
                availability = true,
                location = "นครราชสีมา",
                imageRes = R.drawable.khaoyai,
                description = "ที่พักในอุทยานแห่งชาติเขาใหญ่ อยู่ท่ามกลางธรรมชาติที่อุดมสมบูรณ์ เหมาะสำหรับการเดินป่าและการส่องสัตว์",
                isFavorite = false
            ),
            // เพิ่มข้อมูลอุทยานอื่นๆ ที่นี่
        )

        return parkList.find { it.name == parkName }
    }
}