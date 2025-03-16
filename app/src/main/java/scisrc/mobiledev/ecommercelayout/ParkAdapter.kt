package scisrc.mobiledev.ecommercelayout.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide // ใช้ Glide โหลดรูปภาพ
import scisrc.mobiledev.ecommercelayout.ParkModel
import scisrc.mobiledev.ecommercelayout.R

class ParkAdapter(
    private var parkList: List<ParkModel>,
    private val onParkClick: (ParkModel) -> Unit
) : RecyclerView.Adapter<ParkAdapter.ParkViewHolder>() {

    inner class ParkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parkImage: ImageView = itemView.findViewById(R.id.park_image)
        val parkName: TextView = itemView.findViewById(R.id.park_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_booking_park, parent, false)
        return ParkViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParkViewHolder, position: Int) {
        val park = parkList[position]
        holder.parkName.text = park.name

        // ✅ ใช้ Glide โหลดรูปภาพ
        Glide.with(holder.itemView.context)
            .load(park.imageRes)
            .into(holder.parkImage)

        // ✅ กดที่ CardView หรือปุ่ม "จอง" ไปหน้า DetailsFragment
        holder.itemView.setOnClickListener { onParkClick(park) }
    }

    override fun getItemCount() = parkList.size

    // ✅ ฟังก์ชันอัปเดตรายการ (กรณีที่มีการค้นหา)
    fun updateList(newList: List<ParkModel>) {
        parkList = newList
        notifyDataSetChanged()
    }
}
