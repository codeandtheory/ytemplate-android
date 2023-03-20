package ytemplate.android.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ytemplate.android.feature.post.ui.PostListScreen

/**
 * Application navigation component.
 */
@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(modifier = modifier, navController = navController, startDestination = "main") {
        composable("main") {
            PostListScreen()
        }
    }
}
