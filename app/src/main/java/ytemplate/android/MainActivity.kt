package ytemplate.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import ytemplate.android.ui.AppScreen

/**
 * Main activity: Launcher Activity
 *
 * @constructor Create empty Main activity
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
//       for larger screen get screen width size class here and pass it to app screen    val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            AppScreen()
        }
    }
}
