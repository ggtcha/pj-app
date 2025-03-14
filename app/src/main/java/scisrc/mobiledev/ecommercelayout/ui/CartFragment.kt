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
import scisrc.mobiledev.ecommercelayout.R

class CartFragment : Fragment() {

    private lateinit var bookingRecyclerView: RecyclerView
    private lateinit var emptyTextView: TextView
    private lateinit var totalPriceTextView: TextView
    private lateinit var checkoutButton: Button
    private lateinit var bookingAdapter: CartAdapter
    private lateinit var bookingStatusTextView: TextView
    private lateinit var confirmReturnButton: Button // ปุ่มยืนยันคืนบ้าน
    private lateinit var confirmCheckoutButton: Button // ปุ่มยืนยันคิดเงิน

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        bookingRecyclerView = view.findViewById(R.id.cartRecyclerView)
        emptyTextView = view.findViewById(R.id.emptyTextView)
        totalPriceTextView = view.findViewById(R.id.txt_total_price)
        checkoutButton = view.findViewById(R.id.btn_checkout)
        bookingStatusTextView = view.findViewById(R.id.bookingStatusTextView)
        confirmReturnButton = view.findViewById(R.id.confirmReturnButton) // ปุ่มยืนยันคืนบ้าน
        confirmCheckoutButton = view.findViewById(R.id.confirmCheckoutButton) // ปุ่มยืนยันคิดเงิน

        setupRecyclerView()
        updateTotalPrice()

        // เริ่มแสดงสถานะการจองเฉพาะเมื่อมีการจอง
        updateBookingStatus()

        checkoutButton.setOnClickListener {
            if (BookingManager.getBookingItems().isNotEmpty()) {
                confirmCheckoutButton.visibility = View.VISIBLE // แสดงปุ่มยืนยันคิดเงิน
                checkoutButton.visibility = View.GONE // ซ่อนปุ่มยืนยันการจอง
            } else {
                Toast.makeText(requireContext(), "การจองว่างเปล่า", Toast.LENGTH_SHORT).show()
            }
        }

        confirmCheckoutButton.setOnClickListener {
            BookingManager.setBookingStatus("ใช้งาน") // เปลี่ยนสถานะเป็นใช้งาน
            updateBookingStatus()  // อัปเดตสถานะการจอง
            confirmCheckoutButton.visibility = View.GONE // ซ่อนปุ่มยืนยันคิดเงิน
            confirmReturnButton.visibility = View.VISIBLE // แสดงปุ่มยืนยันคืนบ้าน
        }

        confirmReturnButton.setOnClickListener {
            // ลบบ้านพักที่จองออกจากรายการ
            BookingManager.clearBookings()

            // เปลี่ยนสถานะการจองเป็น "คืนบ้านแล้ว"
            BookingManager.setBookingStatus("คืนบ้านแล้ว")
            updateBookingStatus()

            // รีเฟรชการแสดงผลใน RecyclerView
            updateBooking()

            confirmReturnButton.visibility = View.GONE
            checkoutButton.visibility = View.VISIBLE
        }

        return view
    }

    private fun setupRecyclerView() {
        val bookingItems = BookingManager.getBookingItems()

        if (bookingItems.isEmpty()) {
            emptyTextView.visibility = View.VISIBLE
            bookingRecyclerView.visibility = View.GONE
        } else {
            emptyTextView.visibility = View.GONE
            bookingRecyclerView.visibility = View.VISIBLE
        }

        bookingAdapter = CartAdapter(bookingItems) { park ->
            BookingManager.removeFromBookings(park)
            updateBooking()
            Toast.makeText(requireContext(), "${park.name} ถูกลบออกจากการจอง", Toast.LENGTH_SHORT).show()
        }

        bookingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        bookingRecyclerView.adapter = bookingAdapter
    }

    private fun updateBooking() {
        bookingAdapter.notifyDataSetChanged()
        val bookingItems = BookingManager.getBookingItems()

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
        val total = BookingManager.getBookingItems().sumOf { it.price }
        totalPriceTextView.text = "ราคารวม: $ %.2f".format(total)
    }

    private fun updateBookingStatus() {
        // ตรวจสอบว่ามีการจองหรือไม่
        val bookingItems = BookingManager.getBookingItems()
        if (bookingItems.isEmpty()) {
            bookingStatusTextView.visibility = View.GONE // ซ่อนสถานะถ้าไม่มีการจอง
        } else {
            val status = BookingManager.getBookingStatus()
            bookingStatusTextView.text = "สถานะการจอง: $status"
            bookingStatusTextView.visibility = View.VISIBLE // แสดงสถานะ
        }
    }
}

