package scisrc.mobiledev.ecommercelayout.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView // เพิ่มการ import
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import scisrc.mobiledev.ecommercelayout.ParkModel
import scisrc.mobiledev.ecommercelayout.R

class ParkAdapter(
    private val parkList: List<ParkModel>,
    private val onFavoriteClick: (ParkModel) -> Unit,
    private val onBookClick: (ParkModel) -> Unit
) : RecyclerView.Adapter<ParkAdapter.ParkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_park, parent, false)
        return ParkViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParkViewHolder, position: Int) {
        val park = parkList[position]
        holder.bind(park)
    }

    override fun getItemCount() = parkList.size

    inner class ParkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val parkImageView: ImageView = itemView.findViewById(R.id.parkImageView) // แก้ไขตรงนี้
        private val parkNameTextView: TextView = itemView.findViewById(R.id.parkNameTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)

        fun bind(park: ParkModel) {
            parkNameTextView.text = park.name
            priceTextView.text = "ราคา: ฿ ${park.price}"

            // ตั้งค่าภาพที่จะแสดง
            parkImageView.setImageResource(park.imageRes) // ตรวจสอบให้แน่ใจว่า park.imageRes เป็น resource ID ที่ถูกต้อง



        }
    }
}
