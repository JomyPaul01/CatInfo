package com.jomy.catinfo.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Constraints
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.jomy.catinfo.R
import com.jomy.catinfo.model.CatInfo
import com.jomy.catinfo.model.Constants
import com.jomy.catinfo.ui.viewmodel.CatsInfoUiState
import com.jomy.catinfo.ui.viewmodel.CatsInfoViewModel

enum class CatInfoScreen(@StringRes val title: Int) {
    HOME(title = R.string.app_name),
    DETAILS(title = R.string.details),
    FAVOURITE(title = R.string.favourites),
}

//sealed class DestinationScreen(val route: String) {
//    data object Home : DestinationScreen(UIConstants.CATINFO)
//    data object Favourite : DestinationScreen(UIConstants.FAVOURITE)
//    data object DetailPage : DestinationScreen("${UIConstants.DETAILSPAGE}") {
//        //fun createRoute(breedId: String) = "${UIConstants.DETAILSPAGE}/$breedId"
//    }
//
//}

@Composable
fun CatInfoApp() {

    val viewModel = hiltViewModel<CatsInfoViewModel>()
    val catInfoUiState: State<CatsInfoUiState> = viewModel.catsInfoUiState.collectAsState()

    val navController = rememberNavController()
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen =   CatInfoScreen.valueOf(
        backStackEntry?.destination?.route ?: CatInfoScreen.HOME.name
    )

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CatInfoTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() })
        },
        bottomBar = { BottomNavigationMenu(navController = navController) }) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = CatInfoScreen.HOME.name,
            modifier = Modifier
                .fillMaxSize()

        ) {
            composable(route = CatInfoScreen.HOME.name) {
                CatInfoHomeScreen(
                    paddingValues = innerPadding,
                    viewModel = viewModel,
                    catInfoUiState = catInfoUiState,
                    navController = navController
                )
            }
            composable(route = CatInfoScreen.DETAILS.name) {
                val catInfoData =
                    navController.previousBackStackEntry?.savedStateHandle?.get<CatInfo>(UIConstants.CATINFO)
                catInfoData?.let {
                    CatInfoDetailScreen(navController = navController, viewModel = viewModel, catInfo = catInfoData)
                }
            }
            composable(route = CatInfoScreen.FAVOURITE.name) {
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatInfoTopAppBar(
    currentScreen:CatInfoScreen,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.image_size))
                        .padding(
                            end = dimensionResource(R.dimen.padding_small), top = dimensionResource(
                                R.dimen.padding_small
                            ), bottom = dimensionResource(R.dimen.padding_small)
                        ),
                    painter = painterResource(R.drawable.ic_paw_logo),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}