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
    private lateinit var openingHoursTextView: TextView // เพิ่ม TextView
    private lateinit var bungalowPriceTextView: TextView // เพิ่ม TextView
    private lateinit var tentPriceTextView: TextView // เพิ่ม TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_park_detail, container, false)

        parkImageView = view.findViewById(R.id.parkImageView)
        parkNameTextView = view.findViewById(R.id.parkNameTextView)
        priceTextView = view.findViewById(R.id.priceTextView)
        locationTextView = view.findViewById(R.id.locationTextView)
        descriptionTextView = view.findViewById(R.id.descriptionTextView)
        openingHoursTextView = view.findViewById(R.id.openingHoursTextView) // ค้นหา TextView จาก View
        bungalowPriceTextView = view.findViewById(R.id.bungalowPriceTextView) // ค้นหา TextView จาก View
        tentPriceTextView = view.findViewById(R.id.tentPriceTextView) // ค้นหา TextView จาก View

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
            openingHoursTextView.text = "เวลาเปิดปิด: ${it.openingHours}"
            bungalowPriceTextView.text = "ราคาบ้านพัก: ${it.bungalowPrice}"
            tentPriceTextView.text = "ราคาเต็นท์: ${it.tentPrice}"
        }

        return view
    }

    private fun findParkByName(parkName: String?): ParkModel? {
        val parkList = listOf(
            ParkModel(
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
            ),
            ParkModel(
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
            ),
            ParkModel(
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
        )

        return parkList.find { it.name == parkName }
    }
}