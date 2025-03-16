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
    private lateinit var bankAccountText: TextView
    private lateinit var qrImageView: ImageView

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
        bankAccountText = view.findViewById(R.id.bank_account_text)
        qrImageView = view.findViewById(R.id.qr_code_image)

        val price = if (accommodation == "เต็นท์") selectedPark.tentPrice else selectedPark.bungalowPrice

        summaryText.text = """
            📍 อุทยาน: ${selectedPark.name}
            🏕 ที่พัก: $accommodation
            📅 วันที่: $date
            👤 จำนวนคน: $people
            💰 ราคา: $price บาท/คืน
        """.trimIndent()

        val paymentMethods = listOf("พร้อมเพย์ (QR Code)", "โอนเงินธนาคาร")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            paymentMethods
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        paymentSpinner.adapter = adapter

        paymentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    bankAccountText.visibility = View.GONE
                    qrImageView.visibility = View.VISIBLE
                    qrImageView.setImageResource(R.drawable.qr_code_sample)
                } else {
                    qrImageView.visibility = View.GONE
                    bankAccountText.visibility = View.VISIBLE
                    bankAccountText.text = "🏦 โอนเงินมาที่: 123-456-789 ธนาคาร A"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        confirmButton.setOnClickListener {
            Toast.makeText(context, "✅ ยืนยันการจองเรียบร้อย!", Toast.LENGTH_LONG).show()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BookingFragment())
                .commit()
        }

        return view
    }
}
