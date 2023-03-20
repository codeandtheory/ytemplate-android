package ytemplate.android.core.ui.templates

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import ytemplate.android.core.ui.theme.YTemplateTheme
import ytemplate.android.core.ui.theme.dimensions

/**
 * App loading indicator
 *
 */
@Composable
fun AppLoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center).size(size = dimensions.largeIconSize).testTag("loadingIndicator"))
    }
}

/**
 * Previewloading indicator
 *
 */
@Preview("loadingIndicator", device = Devices.PIXEL_C)
@Preview(
    "loadingIndicator (dark)",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_C
)
@Preview("loadingIndicator (big font)", fontScale = 1.5f, device = Devices.PIXEL_C)
@Composable
fun PreviewloadingIndicator() {
    YTemplateTheme {
        AppLoadingIndicator()
    }
}
