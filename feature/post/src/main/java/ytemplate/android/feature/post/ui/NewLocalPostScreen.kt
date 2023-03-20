@file:OptIn(ExperimentalMaterial3Api::class)

package ytemplate.android.feature.post.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import ytemplate.android.core.common.model.Post
import ytemplate.android.core.ui.theme.YTemplateTheme
import ytemplate.android.core.ui.theme.appButtonColors
import ytemplate.android.core.ui.theme.dimensions
import ytemplate.android.feature.post.R

/**
 * Show create post option
 *
 * @param modifier
 * @param onNewPostCreated
 * @receiver
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowCreatePostOption(modifier: Modifier = Modifier, onNewPostCreated: (Post) -> Unit) {
    var showNewPostScreen by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(dimensions.paddingLarge)) {
        if (showNewPostScreen) {
            ShowNewPostScreen(modifier, onNewPostCreated) {
                showNewPostScreen = false
            }
        } else {
            Button(
                onClick = {
                    showNewPostScreen = true
                },
                modifier = Modifier
                    .testTag("show_new_post_button")
                    .fillMaxWidth()
                    .padding(dimensions.paddingSmall),
                colors = appButtonColors
            ) {
                Text(
                    text = stringResource(R.string.create_new_post),
                    modifier = Modifier.testTag("add_button_tag"),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

/**
 * Show new post screen
 *
 * @param modifier
 * @param onNewPostCreated
 * @param onDismiss
 * @receiver
 * @receiver
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowNewPostScreen(
    modifier: Modifier = Modifier,
    onNewPostCreated: (Post) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }
    Column(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth().padding(dimensions.paddingSmall)) {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = stringResource(R.string.new_post),
                style = MaterialTheme.typography.headlineMedium
            )
            IconButton(modifier = Modifier.align(Alignment.CenterEnd), onClick = { onDismiss() }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(R.string.close_new_post_screen)
                )
            }
        }
        Spacer(modifier = Modifier.height(dimensions.verticalSpacer))
        OutlinedTextField(
            value = title,
            onValueChange = { text -> title = text },
            modifier = Modifier
                .testTag("title_tag")
                .fillMaxWidth()
                .padding(dimensions.paddingSmall),
            textStyle = MaterialTheme.typography.bodyMedium,
            label = { Text(stringResource(id = R.string.title)) }
        )
        Spacer(modifier = Modifier.height(dimensions.verticalSpacer))
        TextField(
            value = details,
            onValueChange = { text -> details = text },
            modifier = Modifier
                .testTag("details_tag")
                .fillMaxWidth()
                .padding(dimensions.paddingSmall),
            textStyle = MaterialTheme.typography.bodyMedium,
            label = { Text(stringResource(id = R.string.details)) }
        )
        Divider(
            modifier = Modifier
                .height(dimensions.thinBorder)
                .fillMaxWidth()
                .padding(dimensions.paddingSmall)
        )
        Button(
            enabled = title.isNotEmpty() && details.isNotEmpty(),
            onClick = {
                onNewPostCreated(Post(0, 0, title, details))
                onDismiss()
            },
            modifier = Modifier
                .testTag("add_button")
                .fillMaxWidth()
                .padding(dimensions.paddingSmall),
            colors = appButtonColors
        ) {
            Text(
                text = stringResource(R.string.add),
                modifier = Modifier.testTag("add_button_tag"),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

/**
 * Previewshow new post screen
 *
 */
@Preview("show NewPost option Screen", device = Devices.PIXEL_C)
@Preview(
    "show NewPost option Screen (dark)",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_C
)
@Preview("show NewPost option Screen (big font)", fontScale = 1.5f, device = Devices.PIXEL_C)
@Composable
fun PreviewshowNewPostScreen() {
    YTemplateTheme {
        ShowCreatePostOption(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensions.paddingLarge)
        ) {
        }
    }
}

/**
 * Previewshow new post screen
 *
 */
@Preview("show NewPost Edit Screen", device = Devices.PIXEL_C)
@Preview(
    "show NewPost Edit Screen (dark)",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_C
)
@Preview("show NewPost Edit Screen (big font)", fontScale = 1.5f, device = Devices.PIXEL_C)
@Composable
fun PreviewshowNewPostEditScreen() {
    YTemplateTheme {
        ShowNewPostScreen(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensions.paddingLarge),
            {
            }
        ) {
        }
    }
}
