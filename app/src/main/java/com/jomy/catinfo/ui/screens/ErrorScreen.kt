package com.jomy.catinfo.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jomy.catinfo.R

@Composable
fun ErrorScreen(paddingValues: PaddingValues) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxHeight().padding(paddingValues)
    ){
        Text(
            text = stringResource(id = R.string.emptylisterror),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Text(
            text = stringResource(id = R.string.checknetwork),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier=Modifier.padding(top=40.dp)
        )
    }



}