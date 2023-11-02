package com.test.favoriteregions.ui.region

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.test.favoriteregions.R

class ViewPagerImageAdapter(
    private var imageList: List<String>,
    private val context: Context
): PagerAdapter() {

    override fun getCount(): Int = imageList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.images_items, container, false)

        Glide.with(context)
            .load(imageList[position])
            .fitCenter()
            .into(view.findViewById<ImageView>(R.id.image_item))

        view.tag = position
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as CardView)
    }

}