package ytemplate.android.ui.mymodel

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ytemplate.android.data.MyPostRepositoryImpl
import ytemplate.android.data.datasource.LocalPostDataSourceImpl
import ytemplate.android.data.datasource.RemotePostDataSourceImpl
import ytemplate.android.test.FakePostDao
import ytemplate.android.test.RemotePostDataMock
import ytemplate.android.ui.theme.YTemplateTheme

@RunWith(AndroidJUnit4::class)
class MyModelScreenKtTest {

    @get:Rule
    val composeRule = createComposeRule()
    lateinit var viewModel: MyPostViewModel

    @Before
    fun setUp() {
        val remotePostDataMock = RemotePostDataMock()
        val fakePostDao = FakePostDao()
        viewModel = MyPostViewModel(
            MyPostRepositoryImpl(
                Dispatchers.IO,
                LocalPostDataSourceImpl(fakePostDao),
                remotePostDataSource = RemotePostDataSourceImpl(httpClient = remotePostDataMock.httpClient)
            )
        )
    }

    @Test
    fun testAddButton() {
        composeRule.setContent {
            YTemplateTheme {
                MyModelScreen(viewModel)
            }
        }
        composeRule.onNodeWithTag("add_button").assertIsDisplayed()
        composeRule.onNodeWithTag("name_field").assertIsDisplayed()
    }
}