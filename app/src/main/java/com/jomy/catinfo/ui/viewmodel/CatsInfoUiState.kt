package com.jomy.catinfo.ui.viewmodel

import androidx.compose.runtime.State
import androidx.paging.PagingData
import com.jomy.catinfo.model.CatInfo
import kotlinx.coroutines.flow.Flow

sealed class CatsInfoUiState{
    //data class SuccessGetCatsInfo(val catsInfo: List<CatInfo>) : CatsInfoUiState()
    data class SuccessGetCatsInfo(val catsInfoPagingDataFlow: Flow<PagingData<CatInfo>>) : CatsInfoUiState()

    data object SuccessAddCatFavourite : CatsInfoUiState()
    data object SuccessDeleteCatFavourite: CatsInfoUiState()
    data class SuccessGetFavouriteCats(val catsInfo: List<CatInfo>):CatsInfoUiState()
    data class ErrorGetCatsInfo(val errorMessage: String?) : CatsInfoUiState()
    data class ErrorGetFavouriteCats(val errorMessage: String?):CatsInfoUiState()
    data class ErrorAddFavouriteCat(val errorMessage: String?):CatsInfoUiState()
    data class ErrorDeleteFavouriteCat(val errorMessage: String?):CatsInfoUiState()
     object LoadingGetCatsInfo : CatsInfoUiState()
    data object LoadingGetFavouriteCats:CatsInfoUiState()
}