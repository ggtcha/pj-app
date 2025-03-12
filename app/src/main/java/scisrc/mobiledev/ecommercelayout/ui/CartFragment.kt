package scisrc.mobiledev.ecommercelayout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import scisrc.mobiledev.ecommercelayout.BookingManager
import scisrc.mobiledev.ecommercelayout.ParkModel
import scisrc.mobiledev.ecommercelayout.R

class CartFragment : Fragment() {

    private lateinit var bookingRecyclerView: RecyclerView
    private lateinit var emptyTextView: TextView
    private lateinit var totalPriceTextView: TextView
    private lateinit var checkoutButton: Button
    private lateinit var bookingAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        bookingRecyclerView = view.findViewById(R.id.cartRecyclerView)
        emptyTextView = view.findViewById(R.id.emptyTextView)
        totalPriceTextView = view.findViewById(R.id.txt_total_price)
        checkoutButton = view.findViewById(R.id.btn_checkout)

        setupRecyclerView()
        updateTotalPrice()

        checkoutButton.setOnClickListener {
            if (BookingManager.getBookingItems().isNotEmpty()) {
                Toast.makeText(requireContext(), "จองบ้านพักเรียบร้อย!", Toast.LENGTH_SHORT).show()
                BookingManager.clearBookings()
                updateBooking()
            } else {
                Toast.makeText(requireContext(), "การจองว่างเปล่า", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun setupRecyclerView() {
        // ดึงข้อมูลการจองจาก BookingManager
        val bookingItems = BookingManager.getBookingItems()

        // ตรวจสอบว่าไม่มีกิจกรรมในตะกร้า
        if (bookingItems.isEmpty()) {
            emptyTextView.visibility = View.VISIBLE
            bookingRecyclerView.visibility = View.GONE
        } else {
            emptyTextView.visibility = View.GONE
            bookingRecyclerView.visibility = View.VISIBLE
        }

        // สร้าง adapter สำหรับการแสดงบ้านพัก
        bookingAdapter = CartAdapter(bookingItems) { park ->
            BookingManager.removeFromBookings(park)
            updateBooking()
            Toast.makeText(requireContext(), "${park.name} ถูกลบออกจากการจอง", Toast.LENGTH_SHORT).show()
        }

        bookingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        bookingRecyclerView.adapter = bookingAdapter
    }

    private fun updateBooking() {
        // อัพเดทข้อมูลหลังจากมีการเปลี่ยนแปลง
        bookingAdapter.notifyDataSetChanged()
        val bookingItems = BookingManager.getBookingItems()

        // ตรวจสอบการจอง
        if (bookingItems.isEmpty()) {
            emptyTextView.visibility = View.VISIBLE
            bookingRecyclerView.visibility = View.GONE
        } else {
            emptyTextView.visibility = View.GONE
            bookingRecyclerView.visibility = View.VISIBLE
        }

        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        // คำนวณราคารวมจากบ้านพักที่จอง
        val total = BookingManager.getBookingItems().sumOf { it.price }
        totalPriceTextView.text = "ราคารวม: $ %.2f".format(total)
    }
}
