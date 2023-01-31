package ytemplate.android.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ytemplate.android.ui.mymodel.MyModelScreen

/**
 * Application navigation component.
 */
@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MyModelScreen()
        }
    }
}