package com.jomy.catinfo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jomy.catinfo.R
import com.jomy.catinfo.model.CatInfo
import com.jomy.catinfo.ui.viewmodel.CatsInfoViewModel
import okhttp3.internal.wait

@Composable
fun CatInfoDetailScreen(
    navController: NavController,
    viewModel: CatsInfoViewModel,
    catInfo: CatInfo
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        CatImageWithName(catInfo)

        Text(text = catInfo.temperament, style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)))

        catInfo.description?.let {
            Text(
                text = catInfo.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(
                        dimensionResource(id = R.dimen.padding_medium)
                    )
            )
        }




    }

}

@Composable
private fun CatImageWithName(catInfo: CatInfo) {
    Box(modifier = Modifier.height(465.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.TopStart)
        ) {
            DetailScreenImage(data = catInfo.image?.url)
        }
        //Spacer(modifier = Modifier.padding(40.dp).align(Alignment.Center))
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.primary)
                .align(Alignment.BottomStart)
        ) {


            Text(
                text = catInfo.name, style = MaterialTheme.typography.displayMedium,
                color = Color.White,
                modifier = Modifier
                    .padding(
                        top = dimensionResource(
                            id = R.dimen.padding_medium
                        ), start =
                        dimensionResource(
                            id = R.dimen.padding_medium
                        )
                    )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                val weight =
                    stringResource(id = R.string.weight) + "  " + catInfo.weight.imperial + " " + UIConstants.POUNNDUNIT
                Text(
                    text = weight, style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.padding_medium),
                        start = dimensionResource(id = R.dimen.padding_medium),
                        bottom = dimensionResource(id = R.dimen.padding_medium),
                    )
                )

                val lifespan =
                    stringResource(id = R.string.lifespan) + "  " + catInfo.lifespan + " " + stringResource(
                        id = R.string.years
                    )
                Text(
                    text = lifespan, style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.padding_medium),
                        start = dimensionResource(id = R.dimen.padding_medium),
                        bottom = dimensionResource(id = R.dimen.padding_medium),
                        end = dimensionResource(
                            id = R.dimen.padding_medium
                        )
                    )
                )
            }
        }
    }
}

@Composable
fun DetailScreenImage(
    data: String?
) {
    data?.let {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(data)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.catphoto),
            modifier = Modifier
                .fillMaxSize()
                .height(500.dp)
                .aspectRatio(4f / 3f)
        )
    }
    if (data == null) {
        Image(
            painter = painterResource(id = R.drawable.placeholder_image),
            contentDescription = null,
            modifier = Modifier
                .size(400.dp),
            contentScale = ContentScale.Inside
        )
    }
}