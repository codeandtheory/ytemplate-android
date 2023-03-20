package ytemplate.android.core.ui.templates

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import ytemplate.android.core.ui.theme.YTemplateTheme
import ytemplate.android.core.ui.theme.dimensions

/**
 * Sticky error message card
 *
 * @param message
 */
@Composable
fun StickyErrorMessageCard(message: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(dimensions.paddingMedium)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = dimensions.cardElevation)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(
                    dimensions.paddingLarge
                )
            ) {
                Icon(imageVector = Icons.Filled.Warning, contentDescription = "error $message")
                Spacer(modifier = Modifier.width(dimensions.paddingMedium))
                Text(
                    text = message,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensions.paddingLarge),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

/**
 * Previewsticky error message card
 *
 */
@Preview("stickyErrorMessageCard", device = Devices.PIXEL_C)
@Preview(
    "stickyErrorMessageCard (dark)",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_C
)
@Preview("stickyErrorMessageCard (big font)", fontScale = 1.5f, device = Devices.PIXEL_C)
@Composable
fun PreviewstickyErrorMessageCard() {
    YTemplateTheme {
        StickyErrorMessageCard("Error message cant be dismissed")
    }
}
