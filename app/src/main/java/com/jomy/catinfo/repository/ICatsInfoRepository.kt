package com.jomy.catinfo.repository

import com.jomy.catinfo.model.CatInfo
import com.jomy.catinfo.model.FavouriteModel

/**
 * A interface to be used by different repository to fetch cat information
 */
interface ICatsInfoRepository {
    suspend fun getCatsInfo(limit: Int, pageNo: Int): List<CatInfo>
}