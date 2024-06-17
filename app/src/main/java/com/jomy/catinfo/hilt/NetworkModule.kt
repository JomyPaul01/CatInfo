package com.jomy.catinfo.hilt

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jomy.catinfo.model.Constants
import com.jomy.catinfo.network.CatApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

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