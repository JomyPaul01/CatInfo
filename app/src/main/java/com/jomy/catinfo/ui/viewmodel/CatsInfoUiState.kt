package com.jomy.catinfo.ui.viewmodel

import androidx.paging.PagingData
import com.jomy.catinfo.model.CatInfo
import kotlinx.coroutines.flow.Flow

sealed class CatsInfoUiState {
    data class SuccessGetCatsInfo(val catsInfoPagingDataFlow: Flow<PagingData<CatInfo>>) :
        CatsInfoUiState()

    data class ErrorGetCatsInfo(val errorMessage: String?) : CatsInfoUiState()
    data object LoadingGetCatsInfo : CatsInfoUiState()
}