package com.jomy.catinfo.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.jomy.catinfo.R
import com.jomy.catinfo.model.CatInfo
import com.jomy.catinfo.ui.viewmodel.CatsInfoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * Composable to show the list from LazyPagingItems,
 * each items shows image, name and weight with favourite icon of a cat
 */
@Composable
fun CatInfoListScreen(
    paddingValues: PaddingValues,
    catInfoLazyPagingItems: LazyPagingItems<CatInfo>,
    navController: NavController,
    viewModel: CatsInfoViewModel
) {
        LazyColumn(contentPadding = paddingValues) {
            items(catInfoLazyPagingItems.itemCount,
                key = catInfoLazyPagingItems.itemKey { it.id }) { item ->
                val catInfoItem = catInfoLazyPagingItems[item]
                catInfoItem?.let {
                    CatInfoItem(viewModel = viewModel, catInfo = catInfoItem, onItemClick = {
                        navigateTo(
                            navController,
                            CatInfoScreen.DETAILS.name,
                            NavParam(UIConstants.CATINFO, it)
                        )
                    })
                }
            }
        }
}

@Composable
fun CatInfoItem(viewModel: CatsInfoViewModel,catInfo: CatInfo, onItemClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        border = BorderStroke(width = 2.dp, color = Color.White),
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_small))
            .height(
                dimensionResource(id = R.dimen.card_size)
            )
            .fillMaxWidth(),
        onClick = onItemClick
    ) {

        Box(modifier = Modifier.weight(1f)) {
            if (catInfo.image == null) {
                Image(
                    painter = painterResource(id = R.drawable.placeholder_image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Fit
                )
            }

            CommonImage(data = catInfo.image?.url, contentScale = ContentScale.Crop)
            Row(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.information_size))
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .align(Alignment.BottomStart)

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CatNameAndWeightText(catInfo)


                    /**
                     * favourite is tracked from data store list.
                     * isFavourite is to cause recomposition when favourite clicked
                     */
                    var isFavourite by rememberSaveable { mutableStateOf(false) }
                    isFavourite = catInfo.isFavourite
                    LaunchedEffect(true) {
                        //read the value from datastore to check if this model is in favorite list
                        catInfo.isFavourite = viewModel.checkIfModelisFavourite(catInfo.id)
                        isFavourite = catInfo.isFavourite
                    }

                    Image(
                        imageVector = if(!isFavourite) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                        colorFilter = if(!isFavourite) ColorFilter.tint(Color.White) else ColorFilter.tint(Color.Cyan),
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 7.dp)
                            .clickable {
                                catInfo.isFavourite = !(catInfo.isFavourite)
                                isFavourite = catInfo.isFavourite
                                val coroutineScope = CoroutineScope(Dispatchers.IO)
                                coroutineScope.launch {
                                    if (catInfo.isFavourite) {
                                        viewModel.saveFavouriteInDataStore(catInfo.id)
                                    } else {
                                        viewModel.deleteFavouriteFromDataStore(catInfo.id)
                                    }
                                }
                            },
                        contentDescription = stringResource(
                            id = R.string.favouriteIcon
                        ),
                    )

                }

            }
        }
    }
}

@Composable
private fun CatNameAndWeightText(catInfo: CatInfo) {
    Column {
        Text(
            text = catInfo.name ?: "",
            style = MaterialTheme.typography.displayMedium,
            color = Color.White,
            modifier = Modifier
                .padding(
                    top = dimensionResource(
                        id = R.dimen.padding_small
                    ), start =
                    dimensionResource(
                        id = R.dimen.padding_small
                    )
                )
        )

        val weight =
            stringResource(id = R.string.weight) + "  " + catInfo.weight?.imperial + " " + UIConstants.POUNNDUNIT
        Text(
            text = weight, style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_small))
        )
    }
}

