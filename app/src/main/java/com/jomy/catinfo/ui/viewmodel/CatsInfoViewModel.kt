package com.jomy.catinfo.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import coil.network.HttpException
import com.jomy.catinfo.model.CatInfo
import com.jomy.catinfo.model.FavouriteModel
import com.jomy.catinfo.repository.CatsInfoPaginationResource
import com.jomy.catinfo.repository.NetworkCatsInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import javax.inject.Inject


@HiltViewModel
class CatsInfoViewModel @Inject constructor(private val paginationResource: CatsInfoPaginationResource) :
    ViewModel() {

     private var _catsInfoUiState = MutableStateFlow<CatsInfoUiState>(CatsInfoUiState.LoadingGetCatsInfo)
     val catsInfoUiState:StateFlow<CatsInfoUiState> = _catsInfoUiState.asStateFlow()

    init {
        getCatsInfo()
    }

     fun getCatsInfo() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _catsInfoUiState.value = CatsInfoUiState.LoadingGetCatsInfo
                _catsInfoUiState.value = try {
                    //todo set page no from a counter
                   // val catInfoList = catsInfoRepository.getCatsInfo(20, 0)
                    val catsPagingDataFlow: Flow<PagingData<CatInfo>> = Pager(
                        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
                        pagingSourceFactory = { paginationResource }
                    )
                        .flow
                        .cachedIn(viewModelScope)

                    CatsInfoUiState.SuccessGetCatsInfo(catsPagingDataFlow)
                }  catch (e: Exception) {
                    CatsInfoUiState.ErrorGetCatsInfo(e.message)
                }
            }
        }
    }

//     fun getFavouriteCats() {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                _catsInfoUiState.value = CatsInfoUiState.LoadingGetFavouriteCats
//                _catsInfoUiState.value = try {
//                    //todo set page no from a counter and get valid sub id from data store
//                    //val catInfoList = catsInfoRepository.getFavouriteCats(20, 0, "xyz")
//                   // CatsInfoUiState.SuccessGetFavouriteCats(catInfoList)
//                } catch (e: IOException) {
//                    CatsInfoUiState.ErrorGetFavouriteCats(e.message)
//                } catch (e: HttpException) {
//                    CatsInfoUiState.ErrorGetFavouriteCats(e.message)
//                } catch (e: Exception) {
//                    CatsInfoUiState.ErrorGetFavouriteCats(e.message)
//                }
//            }
//        }
//    }
//
//     fun addFavouriteCat(imageId:String,subId:String) {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                _catsInfoUiState.value = try {
//                    //todo save the favourite iD.
//                    val favouriteModel = FavouriteModel(imageId, subId)
//                    val favouriteId = catsInfoRepository.addCatFavourite(favouriteModel)
//                    CatsInfoUiState.SuccessAddCatFavourite
//                } catch (e: Exception) {
//                    CatsInfoUiState.ErrorAddFavouriteCat(e.message)
//                }
//            }
//        }
//    }
//
//
//    //todo fetch favouite id from another api which can call this
//     fun deleteFavouriteCat(favouriteId:String) {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                _catsInfoUiState.value = try {
//                    catsInfoRepository.deleteCatFavourite(favouriteId)
//                    CatsInfoUiState.SuccessDeleteCatFavourite
//                } catch (e: Exception) {
//                    CatsInfoUiState.ErrorDeleteFavouriteCat(e.message)
//                }
//            }
//        }
//    }


}