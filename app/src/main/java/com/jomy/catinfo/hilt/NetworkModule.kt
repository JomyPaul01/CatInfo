package com.jomy.catinfo.hilt

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jomy.catinfo.model.Constants
import com.jomy.catinfo.network.CatApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton
/**
 * Hilt module to provide dependency to retrofit to repository class
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit{
       return Retrofit.Builder()
            .addConverterFactory(Json{ignoreUnknownKeys = true}
                .asConverterFactory("application/json".toMediaType())
            )
            .baseUrl(Constants.SERVICEURL)
            .build()
    }

    @Provides
    @Singleton
    fun provideCatApiService(retrofit: Retrofit): CatApiService =
        retrofit.create(CatApiService::class.java)

}