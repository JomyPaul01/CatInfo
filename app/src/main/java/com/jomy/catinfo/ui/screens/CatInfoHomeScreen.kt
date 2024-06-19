package com.jomy.catinfo.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.jomy.catinfo.ui.viewmodel.CatsInfoUiState
import com.jomy.catinfo.ui.viewmodel.CatsInfoViewModel

/**
 * Composable to load the UI as per the state of error,success. it gets the state from CatInfoApp composable
 */
@Composable
fun CatInfoHomeScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: CatsInfoViewModel,
    catInfoUiState: State<CatsInfoUiState>,

    ) {

    var loader by rememberSaveable { mutableStateOf(true) }

    when (catInfoUiState.value) {
        is CatsInfoUiState.LoadingGetCatsInfo -> {
            ProgressSpinner()
        }

        is CatsInfoUiState.ErrorGetCatsInfo -> {
            ErrorScreen(paddingValues)
        }

        is CatsInfoUiState.SuccessGetCatsInfo -> {
            if (loader) {
                ProgressSpinner()
            }
            val catInfoList =
                (catInfoUiState.value as CatsInfoUiState.SuccessGetCatsInfo).catsInfoPagingDataFlow.collectAsLazyPagingItems()
            catInfoList.let {
                if (catInfoList.itemCount > 0) {
                    loader = false
                    CatInfoListScreen(
                        paddingValues = paddingValues,
                        catInfoList,
                        navController = navController,
                        viewModel = viewModel
                    )
                } else if(catInfoList.loadState.hasError) {
                    loader = false
                    ErrorScreen(paddingValues)
                }
            }
        }

    }
}