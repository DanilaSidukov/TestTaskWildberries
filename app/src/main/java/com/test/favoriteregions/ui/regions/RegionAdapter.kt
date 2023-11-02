package com.test.favoriteregions.ui.regions

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.favoriteregions.R
import com.test.favoriteregions.data.Settings
import com.test.favoriteregions.domain.entities.Brand

class RegionAdapter(
    private var list:List<Brand>,
    private val onRegionClickListener: OnRegionClickListener,
): RecyclerView.Adapter<RegionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.region_item, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(list[position].thumbUrls[0])
            .error(androidx.cardview.R.color.cardview_dark_background)
            .optionalCenterCrop()
            .into(holder.regionImage)

        holder.regionName.text = list[position].title

        holder.icRegionLike.setOnClickListener {
            if (list[holder.adapterPosition].like) {
                list[holder.adapterPosition].like = false
                holder.icRegionLike.setImageResource(R.drawable.ic_heart)
            }
            else {
                list[holder.adapterPosition].like = true
                holder.icRegionLike.setImageResource(R.drawable.ic_heart_fill)
            }
            onRegionClickListener.onLikeClicked(list[holder.adapterPosition])
        }

        holder.itemView.setOnClickListener {
            onRegionClickListener.onRegionClicked(list[position])
        }

        if (list[position].like) holder.icRegionLike.setImageResource(R.drawable.ic_heart_fill)
        else holder.icRegionLike.setImageResource(R.drawable.ic_heart)

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val regionImage: ImageView = itemView.findViewById(R.id.region_item_image)
        val regionName: TextView = itemView.findViewById(R.id.region_item_name)
        val icRegionLike: ImageView = itemView.findViewById(R.id.region_item_image_heart)
    }

    fun updateList(newList: List<Brand>){
        list = newList
        notifyDataSetChanged()
    }

}

interface OnRegionClickListener {
    fun onRegionClicked(region: Brand)
    fun onLikeClicked(region: Brand)
}
