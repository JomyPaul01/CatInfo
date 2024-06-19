package com.jomy.catinfo.repository

import com.jomy.catinfo.model.CatInfo
import com.jomy.catinfo.model.Constants
import com.jomy.catinfo.model.FavouriteModel
import com.jomy.catinfo.network.CatApiService
import javax.inject.Inject


/**
 * Class to fetch data from network for cat information
 */
class NetworkCatsInfoRepository @Inject constructor(private val catApiService: CatApiService) :
    ICatsInfoRepository {
    override suspend fun getCatsInfo(limit: Int, pageNo: Int): List<CatInfo> = catApiService.getCatsInfo(
            Constants.APIKEY,limit,pageNo)
}