package com.jomy.catinfo.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.jomy.catinfo.ui.viewmodel.CatsInfoUiState
import com.jomy.catinfo.ui.viewmodel.CatsInfoViewModel

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

        is CatsInfoUiState.ErrorGetCatsInfo -> {}
        is CatsInfoUiState.SuccessGetCatsInfo -> {
            if (loader) {
                ProgressSpinner()
            }
            val catInfoList =
                (catInfoUiState.value as CatsInfoUiState.SuccessGetCatsInfo).catsInfoPagingDataFlow.collectAsLazyPagingItems()
            catInfoList?.let {
                if (catInfoList.itemCount > 0) {
                    loader = false
                }
                CatInfoListScreen(paddingValues = paddingValues, catInfoList, navController = navController)
            }
        }

        else -> {}

    }
}