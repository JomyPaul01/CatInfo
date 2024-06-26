package com.jomy.catinfo.ui.screens

import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jomy.catinfo.R


@Composable
fun CommonImage(
    data: String?,
    modifier: Modifier = Modifier.wrapContentSize(),
    contentScale: ContentScale,
    ) {
    data?.let {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(data)
                .build(),
            contentDescription = stringResource(R.string.catphoto),
            modifier = modifier,
            contentScale = contentScale
        )
    }
}



@Composable
fun ProgressSpinner() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.5f)
            .background(Color.LightGray)
            .clickable(enabled = false) {},
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator()
    }
}

data class NavParam(
    val name: String,
    val value: Parcelable
)

fun navigateTo(navController: NavController, route: String, vararg params: NavParam) {

    for (param in params) {
        navController.currentBackStackEntry?.savedStateHandle?.set(param.name,param.value)
    }

    navController.navigate(route) {
        popUpTo(route){
        }
        launchSingleTop = true
    }
}




