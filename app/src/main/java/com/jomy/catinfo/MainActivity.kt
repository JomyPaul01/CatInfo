package com.jomy.catinfo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.jomy.catinfo.ui.screens.BottomNavigationMenu
import com.jomy.catinfo.ui.screens.CatInfoApp
import com.jomy.catinfo.ui.screens.CatInfoTopAppBar
import com.jomy.catinfo.ui.theme.CatInfoTheme
import com.jomy.catinfo.ui.viewmodel.CatsInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatInfoTheme {
                CatInfoApp()
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