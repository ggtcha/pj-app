package scisrc.mobiledev.ecommercelayout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import scisrc.mobiledev.ecommercelayout.ParkModel
import scisrc.mobiledev.ecommercelayout.R
import androidx.fragment.app.FragmentTransaction

class HomeFragment : Fragment() {

    private lateinit var recommendedProductsRecycler: RecyclerView
    private lateinit var parkAdapter: ParkAdapter
    private val parkList = listOf(
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recommendedProductsRecycler = view.findViewById(R.id.recommendedProductsRecycler)
        recommendedProductsRecycler.layoutManager = LinearLayoutManager(context) // แก้ไขตรงนี้: เปลี่ยนเป็น LinearLayoutManager(context) เพื่อแสดงรายการแนวตั้ง

        parkAdapter = ParkAdapter(parkList) { park ->
            // เมื่อคลิกที่รายการอุทยาน
            val parkDetailFragment = ParkDetailFragment()
            val bundle = Bundle()
            bundle.putString("parkName", park.name)
            parkDetailFragment.arguments = bundle

            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction() // หรือ requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, parkDetailFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        recommendedProductsRecycler.adapter = parkAdapter

        return view
    }

    private inner class ParkAdapter(
        private val parkList: List<ParkModel>,
        private val onItemClick: (ParkModel) -> Unit
    ) : RecyclerView.Adapter<ParkAdapter.ParkViewHolder>() {

        inner class ParkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val parkImageView: ImageView = itemView.findViewById(R.id.parkImageView)
            val parkNameTextView: TextView = itemView.findViewById(R.id.parkNameTextView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_park, parent, false)
            return ParkViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ParkViewHolder, position: Int) {
            val park = parkList[position]
            holder.parkImageView.setImageResource(park.imageRes)
            holder.parkNameTextView.text = park.name
            holder.itemView.setOnClickListener { onItemClick(park) }
        }

        override fun getItemCount() = parkList.size
    }
}