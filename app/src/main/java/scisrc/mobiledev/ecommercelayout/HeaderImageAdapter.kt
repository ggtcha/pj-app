package scisrc.mobiledev.ecommercelayout.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import scisrc.mobiledev.ecommercelayout.R

class HeaderImageAdapter(private val imageList: List<Int>) :
    RecyclerView.Adapter<HeaderImageAdapter.HeaderImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_header_image, parent, false)
        return HeaderImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeaderImageViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount() = imageList.size

    inner class HeaderImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val headerImageView: ImageView = itemView.findViewById(R.id.headerImageView)

        fun bind(imageRes: Int) {
            headerImageView.setImageResource(imageRes)
        }
    }
}