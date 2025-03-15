// PaymentFragment.kt
package scisrc.mobiledev.ecommercelayout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import scisrc.mobiledev.ecommercelayout.ParkModel
import scisrc.mobiledev.ecommercelayout.R

class PaymentFragment : Fragment() {

    private lateinit var paymentSpinner: Spinner
    private lateinit var summaryText: TextView
    private lateinit var confirmButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_payment, container, false)

        val selectedPark = arguments?.getSerializable("selected_park") as ParkModel
        val accommodation = arguments?.getString("accommodation")
        val date = arguments?.getString("date")
        val people = arguments?.getString("people")

        paymentSpinner = view.findViewById(R.id.payment_spinner)
        summaryText = view.findViewById(R.id.summary_text)
        confirmButton = view.findViewById(R.id.confirm_button)

        val paymentMethods = listOf("บัตรเครดิต", "โอนเงินธนาคาร", "ชำระผ่านมือถือ")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            paymentMethods
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        paymentSpinner.adapter = adapter

        summaryText.text = """
            อุทยาน: ${selectedPark.name}
            ที่พัก: $accommodation
            วันที่: $date
            จำนวนคน: $people
            ราคา: ${if (accommodation == "เต็นท์") selectedPark.tentPrice else selectedPark.bungalowPrice}
        """.trimIndent()

        confirmButton.setOnClickListener {
            Toast.makeText(context, "ยืนยันการจองเรียบร้อย!", Toast.LENGTH_LONG).show()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BookingFragment())
                .commit()
        }

        return view
    }
}