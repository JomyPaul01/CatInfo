package com.jomy.catinfo.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState



enum class BottomNavigationItem(val icon: ImageVector, val navDestination: CatInfoScreen) {
    HOME(Icons.Default.Home, CatInfoScreen.HOME),
    FAVOURITE(Icons.Filled.Favorite, CatInfoScreen.FAVOURITE),
}

@Composable
fun BottomNavigationMenu(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 4.dp)
            .background(Color.White)
    ) {
        for (item in BottomNavigationItem.values()) {
            Icon(
                imageVector = item.icon, contentDescription = null, modifier = Modifier
                    .size(40.dp)
                    .padding(5.dp)
                    .weight(1f)
            )

        }
    }
}

//@Composable
//fun BottomNavigationBar(navController: NavController) {
//    BottomNavigation {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//
//        val items = listOf(BottomNavItem.Home,BottomNavItem.Favourite)
//        items.forEach { item ->
//            BottomNavigationItem(
//                selected = currentRoute == item.route,
//                onClick = {
//                    navController.navigate(item.route) {
//                        popUpTo(navController.graph.startDestinationId){
//                            saveState = true
//                        }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                },
//                icon = { Icon(item.icon, contentDescription = null) },
//            )
//        }
//    }
//}
