package com.jomy.catinfo.repository

import com.jomy.catinfo.model.CatInfo
import com.jomy.catinfo.model.FavouriteModel

interface ICatsInfoRepository {

    suspend fun getCatsInfo(limit: Int, pageNo: Int): List<CatInfo>

    suspend fun addCatFavourite(favouriteModel: FavouriteModel): String

    suspend fun deleteCatFavourite(favouriteId: String)

    suspend fun getFavouriteCats(limit: Int, pageNo: Int, userSubId: String): List<CatInfo>
}