package ytemplate.android.ui.mymodel

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ytemplate.android.data.di.FakeMyModelRepository
import ytemplate.android.ui.theme.YTemplateTheme

@RunWith(AndroidJUnit4::class)
class MyModelScreenKtTest {

    @get:Rule
    val composeRule = createComposeRule()
    lateinit var viewModel: MyModelViewModel
    @Before
    fun setUp() {
        viewModel = MyModelViewModel(FakeMyModelRepository())
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
        composeRule.onNodeWithText("test1").assertIsDisplayed()
    }
}