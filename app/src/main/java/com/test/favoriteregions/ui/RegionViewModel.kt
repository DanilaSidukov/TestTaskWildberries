package com.test.favoriteregions.ui

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.favoriteregions.data.Settings
import com.test.favoriteregions.domain.entities.RegionInfo
import com.test.favoriteregions.data.RegionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegionViewModel(
    private val regionRepository: RegionRepository,
    private val settings: Settings
) : ViewModel(), DefaultLifecycleObserver {

    private val _uiState: MutableStateFlow<RegionInfo?> = MutableStateFlow(null)
    val uiState = _uiState.asStateFlow()

    private var localRegions = mutableMapOf<String, Boolean>()

    fun changeLikeState(regionId: String, regionLike: Boolean) {

        localRegions[regionId] = regionLike
        updateData()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        fetchData()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        updateData()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        updateData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            val regionInfo = regionRepository.getRegionInfo()
            localRegions = settings.regions.toMutableMap()
            if (localRegions.isEmpty()) {
                settings.regions = regionInfo.brands.associate {
                    it.brandId to false
                }
                localRegions = settings.regions.toMutableMap()
            }
            regionInfo.brands.forEachIndexed { index, brand ->
                brand.like = localRegions[brand.brandId] ?: false
            }
            _uiState.emit(regionInfo)
        }
    }

    private fun updateData() {
        settings.regions = localRegions
    }
}