package scisrc.mobiledev.ecommercelayout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import scisrc.mobiledev.ecommercelayout.ParkModel
import scisrc.mobiledev.ecommercelayout.R

class DetailsFragment : Fragment() {

    private lateinit var accommodationSpinner: Spinner
    private lateinit var dateSpinner: Spinner
    private lateinit var peopleEditText: EditText
    private lateinit var priceText: TextView
    private lateinit var nextButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        // แก้ไขการรับข้อมูลจาก arguments โดยใช้ getSerializableExtra
        val selectedPark = arguments?.getSerializable("selected_park") as? ParkModel
            ?: throw IllegalArgumentException("Selected park is required")

        accommodationSpinner = view.findViewById(R.id.accommodation_spinner)
        dateSpinner = view.findViewById(R.id.date_spinner)
        peopleEditText = view.findViewById(R.id.people_count)
        priceText = view.findViewById(R.id.price_text)
        nextButton = view.findViewById(R.id.next_button)

        val accommodationTypes = listOf("เต็นท์", "บ้านพัก")
        val accommodationAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            accommodationTypes
        )
        accommodationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        accommodationSpinner.adapter = accommodationAdapter

        // Sample available dates
        val availableDates = listOf("2025-03-20", "2025-03-21", "2025-03-22")
        val dateAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            availableDates
        )
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dateSpinner.adapter = dateAdapter

        accommodationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                priceText.text = if (position == 0) {
                    "ราคา: ${selectedPark.tentPrice}"
                } else {
                    "ราคา: ${selectedPark.bungalowPrice}"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        nextButton.setOnClickListener {
            val people = peopleEditText.text.toString()
            if (people.isNotEmpty()) {
                val paymentFragment = PaymentFragment()
                val bundle = Bundle()
                bundle.putSerializable("selected_park", selectedPark)
                bundle.putString("accommodation", accommodationSpinner.selectedItem.toString())
                bundle.putString("date", dateSpinner.selectedItem.toString())
                bundle.putString("people", people)
                paymentFragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, paymentFragment)
                    .commit()
            } else {
                Toast.makeText(context, "กรุณาระบุจำนวนคน", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}