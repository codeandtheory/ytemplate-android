package ytemplate.android.feature.post.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import ytemplate.android.core.common.model.Post
import ytemplate.android.core.ui.templates.AppLoadingIndicator
import ytemplate.android.core.ui.templates.StickyErrorMessageCard
import ytemplate.android.core.ui.theme.YTemplateTheme
import ytemplate.android.core.ui.theme.dimensions
import ytemplate.android.feature.post.MyPostModelUiState
import ytemplate.android.feature.post.MyPostViewModel
import ytemplate.android.feature.post.R

/**
 * Post list screen
 *
 * @param viewModel
 */
@Composable
fun PostListScreen(viewModel: MyPostViewModel = hiltViewModel()) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val dataState by produceState<MyPostModelUiState>(
        initialValue = MyPostModelUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel,
        producer = {
            lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.getAllPost()
                viewModel.uiState.collect {
                    value = it
                }
            }
        }
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ShowCreatePostOption(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensions.paddingMedium)
        ) { newPost ->
            viewModel.addPost(newPost)
        }
        when (dataState) {
            is MyPostModelUiState.Success -> {
                SimplePostList(list = (dataState as MyPostModelUiState.Success).data) {
                    viewModel.deletePostItem(it)
                }
            }
            is MyPostModelUiState.Loading -> {
                AppLoadingIndicator()
            }
            is MyPostModelUiState.Error -> {
                StickyErrorMessageCard((dataState as MyPostModelUiState.Error).throwable.localizedMessage)
            }
        }
    }
}

/**
 * Simple post list
 *
 * @param list
 * @param onDeletePostItem
 * @receiver
 */
@Composable
fun SimplePostList(list: List<Post>, onDeletePostItem: (Post) -> Unit) {
    val lazyListState = rememberLazyListState()
    if (lazyListState.firstVisibleItemIndex <= 1) {
        // scroll to top when first item not visible
        LaunchedEffect(key1 = list[0]) {
            lazyListState.animateScrollToItem(0)
        }
    }
    Column() {
        Text(modifier = Modifier.padding(dimensions.paddingMedium), text = stringResource(id = R.string.local_list_with_count, list.size), style = MaterialTheme.typography.bodyMedium)
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensions.paddingLarge)
        ) {
            items(list, key = { post ->
                post.id
            }) { postItem ->
                PostListItem(postItem) {
                    onDeletePostItem(it)
                }
            }
        }
    }
}

/**
 * Preview post list screen
 *
 */
@Preview("Post List screen", device = Devices.PIXEL_C, showBackground = true)
@Preview(
    "Post List screen (dark)",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_C,
    showBackground = true
)
@Preview(
    "Post List screen (big font)",
    fontScale = 1.5f,
    device = Devices.PIXEL_C,
    showBackground = true
)
@Composable
fun PreviewPostListScreen() {
    YTemplateTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ShowCreatePostOption(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensions.paddingLarge)
            ) { newPost ->
            }
            SimplePostList(
                listOf(
                    Post(0, 0, "title1", "Body"),
                    Post(0, 0, "title2", "Body"),
                    Post(0, 0, "title3", "Body")
                )
            ) {
            }
        }
    }
}
