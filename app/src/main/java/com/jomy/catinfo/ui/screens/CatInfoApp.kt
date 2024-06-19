package com.jomy.catinfo.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jomy.catinfo.R
import com.jomy.catinfo.model.CatInfo
import com.jomy.catinfo.ui.viewmodel.CatsInfoUiState
import com.jomy.catinfo.ui.viewmodel.CatsInfoViewModel

/**
 * Enum to represent different screens
 */
enum class CatInfoScreen(@StringRes val title: Int) {
    HOME(title = R.string.app_name),
    DETAILS(title = R.string.details),
    FAVOURITE(title = R.string.favourites),
}


/**
 * Composable to set the top app bar and contain Navhost for navigation
 */
@Composable
fun CatInfoApp(viewModel: CatsInfoViewModel, catInfoUiState: State<CatsInfoUiState>) {

    val navController = rememberNavController()

    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()

    //to get back button in the details screen, invoking backstack entry to recompose app bar
    CatInfoScreen.valueOf(
        backStackEntry?.destination?.route ?: CatInfoScreen.HOME.name
    )

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CatInfoTopAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() })
        }
    )
    { innerPadding ->

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
                    CatInfoDetailScreen(viewModel = viewModel, catInfo = catInfoData)
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