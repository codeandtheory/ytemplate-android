@file:OptIn(ExperimentalFoundationApi::class)

package ytemplate.android.core.ui.templates

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import ytemplate.android.core.ui.theme.YTemplateTheme
import ytemplate.android.core.ui.theme.dimensions
import ytemplate.android.ui.R

/**
 * Full width deletable card
 *
 * @param modifier
 * @param onDeleted
 * @param content
 * @receiver
 * @receiver
 */
@Composable
fun FullWidthDeletableCard(modifier: Modifier = Modifier, onDeleted: () -> Unit, content: @Composable () -> Unit) {
    var expandedOptions by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .padding(dimensions.paddingMedium)
            .fillMaxWidth().combinedClickable(
                onClick = {
                    expandedOptions = false
                },
                onLongClick = {
                    expandedOptions = true
                }
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensions.cardElevation)
    ) {
        DropdownMenu(
            expanded = expandedOptions,
            onDismissRequest = { expandedOptions = false }
        ) {
            DropdownMenuItem(onClick = {
                onDeleted()
                expandedOptions = false
            }, text = {
                    Text(text = stringResource(R.string.delete))
                })
        }
        content()
    }
}

/**
 * Preview full width card
 *
 */
@Preview("FullWidthCard", device = Devices.PIXEL_C)
@Preview(
    "FullWidthCard (dark)",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_C
)
@Preview("FullWidthCard (big font)", fontScale = 1.5f, device = Devices.PIXEL_C)
@Composable
fun PreviewFullWidthCard() {
    YTemplateTheme {
        FullWidthDeletableCard(Modifier.padding(dimensions.paddingLarge), {
        }) {
            Text("sample text")
        }
    }
}
