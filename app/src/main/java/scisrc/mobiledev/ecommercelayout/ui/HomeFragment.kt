package scisrc.mobiledev.ecommercelayout.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3
import scisrc.mobiledev.ecommercelayout.ParkModel
import scisrc.mobiledev.ecommercelayout.R
import android.widget.Toast
import scisrc.mobiledev.ecommercelayout.FavoritesManager
import scisrc.mobiledev.ecommercelayout.BookingManager

class HomeFragment : Fragment() {

    private lateinit var recommendedRecyclerView: RecyclerView
    private lateinit var recommendedAdapter: ParkAdapter
    private val recommendedParks = mutableListOf<ParkModel>()

    private lateinit var promotionsRecyclerView: RecyclerView
    private lateinit var promotionsAdapter: ParkAdapter
    private val promotionsList = mutableListOf<ParkModel>()

    private lateinit var bannerViewPager: ViewPager2
    private lateinit var bannerIndicator: CircleIndicator3
    private val bannerHandler = Handler(Looper.getMainLooper())

    private val bannerRunnable = object : Runnable {
        override fun run() {
            val currentItem = bannerViewPager.currentItem
            val itemCount = bannerViewPager.adapter?.itemCount ?: 0
            if (itemCount > 0) {
                bannerViewPager.currentItem = (currentItem + 1) % itemCount
            }
            bannerHandler.postDelayed(this, 3000) // เลื่อนทุก 3 วินาที
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // เชื่อม RecyclerView บ้านพักแนะนำ
        recommendedRecyclerView = view.findViewById(R.id.recommendedProductsRecycler)
        recommendedRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // Mock data for recommended parks
        recommendedParks.apply {
            add(ParkModel("บ้านพักอุทยานแห่งชาติ", 1500.00, true, "เชียงใหม่", R.drawable.ic_pen, "บ้านพักที่ตั้งอยู่ในอุทยานแห่งชาติ"))
            add(ParkModel("อุทยานแห่งชาติบึงฉวาก", 1200.0, true, "สุพรรณบุรี", R.drawable.bungchawak, "ที่พักในอุทยานแห่งชาติบึงฉวาก ตั้งอยู่ท่ามกลางธรรมชาติ อากาศบริสุทธิ์ เหมาะสำหรับการพักผ่อนแบบครอบครัวและการเดินป่าศึกษาธรรมชาติ"))
        }

        // Set up the adapter for the recommended parks
        recommendedAdapter = ParkAdapter(recommendedParks, { updateFavorites(it) }, { bookPark(it) })
        recommendedRecyclerView.adapter = recommendedAdapter

        // Mock data for promotions
        promotionsRecyclerView = view.findViewById(R.id.promotionsRecycler)
        promotionsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        promotionsList.apply {
            add(ParkModel("อุทยานแห่งชาติไทรโยค", 1300.0, true, "กาญจนบุรี", R.drawable.saiyok, "อุทยานที่เต็มไปด้วยธรรมชาติและน้ำตกที่สวยงาม"))
            add(ParkModel("อุทยานแห่งชาติผาแต้ม", 1300.0, true, "อุบลราชธานี", R.drawable.phataem, "อุทยานที่มีวิวทิวทัศน์สวยงามและภาพเขียนสีโบราณ"))
            add(ParkModel("อุทยานแห่งชาติเขาใหญ่", 1300.0, true, "นครราชสีมา", R.drawable.khaoyai, "อุทยานที่มีป่าเขียวขจีและสัตว์ป่าหลากหลายชนิด"))
            add(ParkModel("อุทยานแห่งชาติเอราวัณ", 1300.0, true, "กาญจนบุรี", R.drawable.erawan, "อุทยานแห่งชาติเอราวัณเป็นแหล่งท่องเที่ยวธรรมชาติที่มีชื่อเสียงในเรื่องน้ำตกเอราวัณที่สวยงามและพื้นที่สำหรับเดินป่า"))
        }


        // Set up the adapter for promotions
        promotionsAdapter = ParkAdapter(promotionsList, { updateFavorites(it) }, { bookPark(it) })
        promotionsRecyclerView.adapter = promotionsAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bannerViewPager = view.findViewById(R.id.bannerViewPager)
        bannerIndicator = view.findViewById(R.id.bannerIndicator)

        val bannerImages = listOf(R.drawable.banner1, R.drawable.banner2, R.drawable.banner3)

        val adapter = BannerAdapter(bannerImages)
        bannerViewPager.adapter = adapter

        bannerIndicator.setViewPager(bannerViewPager)
        bannerHandler.postDelayed(bannerRunnable, 3000)

        bannerViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bannerHandler.removeCallbacks(bannerRunnable)
                bannerHandler.postDelayed(bannerRunnable, 3000)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bannerHandler.removeCallbacks(bannerRunnable)
    }

    // Function to handle favorite click events
    private fun updateFavorites(park: ParkModel) {
        park.isFavorite = !park.isFavorite
        if (park.isFavorite) {
            FavoritesManager.addToFavorites(park)
        } else {
            FavoritesManager.removeFromFavorites(park)
        }
        recommendedAdapter.notifyDataSetChanged()
        promotionsAdapter.notifyDataSetChanged()
    }

    // Function to handle booking click events
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
