package com.test.favoriteregions.ui.region

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.test.favoriteregions.R
import com.test.favoriteregions.RegionsApplication
import com.test.favoriteregions.ui.RegionViewModel
import com.test.favoriteregions.ui.regions.RegionItem

class RegionActivity : AppCompatActivity() {

    companion object {
        const val REGION_KEY = "region"
    }

    private lateinit var viewPagerImage: ViewPager
    private lateinit var viewPagerImageAdapter: ViewPagerImageAdapter

    private lateinit var regionName: TextView
    private lateinit var viewCount: TextView
    private lateinit var imageLike: ImageView
    private lateinit var buttonBack: Button

    private lateinit var regionItem: RegionItem

    private val regionViewModel: RegionViewModel = RegionsApplication.injector.injectViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_region)

        regionItem = intent.extras?.getSerializable(REGION_KEY) as RegionItem

        lifecycle.addObserver(regionViewModel)

        viewPagerImage = findViewById(R.id.view_pager_region_image)
        regionName = findViewById(R.id.region_name)
        viewCount = findViewById(R.id.text_count_views)
        imageLike = findViewById(R.id.region_image_heart)
        buttonBack = findViewById(R.id.button_back)

        viewPagerImageAdapter = ViewPagerImageAdapter(regionItem.listOfImages, this)
        viewPagerImage.adapter = viewPagerImageAdapter

        regionName.text = regionItem.name
        viewCount.text = getString(R.string.count_views) + regionItem.countViews

        if (regionItem.like) imageLike.setImageResource(R.drawable.ic_heart_fill)
        else imageLike.setImageResource(R.drawable.ic_heart)

        buttonBack.setOnClickListener {
            finish()
        }

        imageLike.setOnClickListener {
            imageLike.setImageResource(
                if (regionItem.like) R.drawable.ic_heart else R.drawable.ic_heart_fill
            )
            regionViewModel.changeLikeState(regionItem.id, !regionItem.like)
        }
    }

}