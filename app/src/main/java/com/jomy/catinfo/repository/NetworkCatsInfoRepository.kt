package com.jomy.catinfo.repository

import com.jomy.catinfo.model.CatInfo
import com.jomy.catinfo.model.Constants
import com.jomy.catinfo.model.FavouriteModel
import com.jomy.catinfo.network.CatApiService
import javax.inject.Inject


class NetworkCatsInfoRepository @Inject constructor(private val catApiService: CatApiService) :
    ICatsInfoRepository {

        //todo handle page no as counter
    override suspend fun getCatsInfo(limit: Int, pageNo: Int): List<CatInfo> = catApiService.getCatsInfo(
            Constants.Companion.APIKEY,limit,pageNo)

    override suspend fun addCatFavourite(favouriteModel: FavouriteModel): String {
        return catApiService.addFavouriteCat(favouriteModel)
    }

    override suspend fun deleteCatFavourite(favouriteId: String) {
        catApiService.deleteFavourite(favouriteId)
    }

    override suspend fun getFavouriteCats(
        limit: Int,
        pageNo: Int,
        userSubId: String
    ): List<CatInfo> {
        return catApiService.getFavouriteCats(limit,pageNo,userSubId)
    }


}