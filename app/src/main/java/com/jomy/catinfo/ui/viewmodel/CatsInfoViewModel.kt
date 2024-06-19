package com.jomy.catinfo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.jomy.catinfo.StringList
import com.jomy.catinfo.model.CatInfo
import com.jomy.catinfo.repository.CatsInfoPaginationResource
import com.jomy.catinfo.repository.DataStorePreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject



/**
 * Viewmodel to fetch cat breed information and handle favourite cats in data store
 */
@HiltViewModel
class CatsInfoViewModel @Inject constructor(
    private val paginationResource: CatsInfoPaginationResource,
    private val dataStorePreferenceManager: DataStorePreferenceManager
) :
    ViewModel() {

    private var _catsInfoUiState =
        MutableStateFlow<CatsInfoUiState>(CatsInfoUiState.LoadingGetCatsInfo)
    val catsInfoUiState: StateFlow<CatsInfoUiState> = _catsInfoUiState.asStateFlow()
    private lateinit var favouriteList: StringList

    init {
        getCatsInfo()
    }

    private suspend fun getFavouriteDataStoreList():StringList{
        return dataStorePreferenceManager.readStringList()
    }

    private fun getCatsInfo() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _catsInfoUiState.value = CatsInfoUiState.LoadingGetCatsInfo
                _catsInfoUiState.value = try {
                    val catsPagingDataFlow: = Pager(
                        config = PagingConfig(pageSize = 20, enablePlaceholders = false),
                        pagingSourceFactory = { paginationResource }
                    ).flow.cachedIn(viewModelScope)

                    favouriteList = dataStorePreferenceManager.readStringList()
                    val modifiedPagingData: Flow<PagingData<CatInfo>> = catsPagingDataFlow.map {
                        it.map { data ->
                            data.copy(
                                isFavourite = favouriteList.itemsList.contains(data.id)
                            )
                        }
                    }
                    CatsInfoUiState.SuccessGetCatsInfo(modifiedPagingData)
                } catch (e: Exception) {
                    CatsInfoUiState.ErrorGetCatsInfo(e.message)
                }
            }
        }
    }

    suspend fun checkIfModelisFavourite(breedId: String): Boolean {
        favouriteList = getFavouriteDataStoreList()
        return ::favouriteList.isInitialized && favouriteList.itemsList.contains(breedId)
    }

    suspend fun deleteFavouriteFromDataStore(breedId: String) {
        if (::favouriteList.isInitialized) {
            favouriteList = getFavouriteDataStoreList()
            val mutableList = favouriteList.itemsList.toMutableList()
            mutableList.remove(breedId)
            dataStorePreferenceManager.saveStringList(mutableList)
        }
    }

    suspend fun saveFavouriteInDataStore(breedId: String) {
        if (::favouriteList.isInitialized) {
            favouriteList = dataStorePreferenceManager.readStringList()
            val mutableList = favouriteList.itemsList.toMutableList()
            mutableList.add(breedId)
            dataStorePreferenceManager.appendStringList(breedId)
        } else {
            val list = mutableListOf(breedId).toList()
            dataStorePreferenceManager.saveStringList(list)
        }
    }
}