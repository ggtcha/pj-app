package scisrc.mobiledev.ecommercelayout.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import scisrc.mobiledev.ecommercelayout.ParkModel
import scisrc.mobiledev.ecommercelayout.R

class CartAdapter(
    private val cartList: List<ParkModel>, // เปลี่ยนจาก ProductModel เป็น scisrc.mobiledev.ecommercelayout.ParkModel
    private val onRemoveClick: (ParkModel) -> Unit // เปลี่ยนจาก ProductModel เป็น scisrc.mobiledev.ecommercelayout.ParkModel
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.cartProductImage) // ใช้รูปภาพบ้านพัก
        val name: TextView = itemView.findViewById(R.id.cartProductName)
        val price: TextView = itemView.findViewById(R.id.cartProductPrice)
        val btnRemove: Button = itemView.findViewById(R.id.btnRemoveFromCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart_product, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val park = cartList[position]

        holder.image.setImageResource(park.imageRes) // ใช้ `imageResId` จาก scisrc.mobiledev.ecommercelayout.ParkModel
        holder.name.text = park.name
        holder.price.text = "$ %.2f".format(park.price)

        holder.btnRemove.setOnClickListener {
            onRemoveClick(park) // เมื่อกดปุ่ม Remove จะใช้ scisrc.mobiledev.ecommercelayout.ParkModel
        }
    }

    override fun getItemCount() = cartList.size
}
