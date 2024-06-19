package com.jomy.catinfo.network

import com.jomy.catinfo.model.CatInfo
import com.jomy.catinfo.model.Constants
import com.jomy.catinfo.model.FavouriteModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * retrofit service api interface to fetch cat information
 */
interface CatApiService {

    @GET("breeds")
    suspend fun getCatsInfo(
        @Header("x-api-key") key: String? = Constants.APIKEY,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): List<CatInfo>

}