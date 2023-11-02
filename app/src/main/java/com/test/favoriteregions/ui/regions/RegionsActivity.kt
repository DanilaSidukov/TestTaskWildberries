package com.test.favoriteregions.ui.regions

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.simform.refresh.SSPullToRefreshLayout
import com.test.favoriteregions.R
import com.test.favoriteregions.RegionsApplication
import com.test.favoriteregions.domain.entities.Brand
import com.test.favoriteregions.ui.RegionViewModel
import com.test.favoriteregions.ui.region.RegionActivity
import com.test.favoriteregions.ui.region.RegionActivity.Companion.REGION_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Serializable


class RegionsActivity : AppCompatActivity(), OnRegionClickListener {

    private lateinit var regionRecyclerView: RecyclerView
    private lateinit var regionAdapter: RegionAdapter

    private lateinit var swipeRefreshLayout: SSPullToRefreshLayout
    private lateinit var buttonRefresh: Button

    private val regionViewModel: RegionViewModel = RegionsApplication.injector.injectViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regions)

        lifecycle.addObserver(regionViewModel)

        regionRecyclerView = findViewById(R.id.region_rv)
        buttonRefresh = findViewById(R.id.button_refresh_list)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setRefreshView(RefreshCircleView(this))


        regionAdapter = RegionAdapter(emptyList(), this)
        regionRecyclerView.layoutManager = LinearLayoutManager(this)

        regionRecyclerView.adapter = regionAdapter

        lifecycleScope.launch {
            regionViewModel.uiState.collect { regionInfo ->
                if (regionInfo == null) return@collect
                regionAdapter.updateList(regionInfo.brands)
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            CoroutineScope(Dispatchers.Main).launch {
                delay(2000)
                swipeRefreshLayout.setRefreshing(false)
                regionViewModel.uiState.collect { regionInfo ->
                    if (regionInfo == null) return@collect
                    regionAdapter.updateList(regionInfo.brands)
                }
            }
        }

        buttonRefresh.setOnClickListener {
            swipeRefreshLayout.setRefreshing(true)
            swipeRefreshLayout.post {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(2000)
                    swipeRefreshLayout.setRefreshing(false)
                    regionViewModel.uiState.collect { regionInfo ->
                        if (regionInfo == null) return@collect
                        regionAdapter.updateList(regionInfo.brands)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            regionViewModel.uiState.collect { regionInfo ->
                if (regionInfo == null) return@collect
                regionAdapter.updateList(regionInfo.brands)
            }
        }
    }

    override fun onRegionClicked(region: Brand) {
        val intent = Intent(this, RegionActivity::class.java)
        intent.putExtra(
            REGION_KEY,
            RegionItem(
                region.brandId,
                region.thumbUrls,
                region.title,
                region.viewsCount,
                region.like
            )
        )
        startActivity(intent)
    }

    override fun onLikeClicked(region: Brand) {
        regionViewModel.changeLikeState(region.brandId, region.like)
    }

}

data class RegionItem(
    val id: String,
    val listOfImages: List<String>,
    val name: String,
    val countViews: Int,
    val like: Boolean
) : Serializable