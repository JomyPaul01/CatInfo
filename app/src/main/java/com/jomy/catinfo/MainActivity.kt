package com.jomy.catinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jomy.catinfo.ui.screens.CatInfoApp
import com.jomy.catinfo.ui.theme.CatInfoTheme
import com.jomy.catinfo.ui.viewmodel.CatsInfoUiState
import com.jomy.catinfo.ui.viewmodel.CatsInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatInfoTheme {
                val viewModel = hiltViewModel<CatsInfoViewModel>()
                val catInfoUiState: State<CatsInfoUiState> = viewModel.catsInfoUiState.collectAsState()

                CatInfoApp(viewModel = viewModel,catInfoUiState = catInfoUiState)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CatInfoTheme {
        //CatInfoApp(20,modifier=Modifier)
    }
}