package ytemplate.android.feature.post.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test
import ytemplate.android.core.ui.theme.YTemplateTheme

class TestNewLocalPostScreen {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testShowLocalPostScreen() {
        // Start the app
        composeTestRule.setContent {
            YTemplateTheme {
                ShowCreatePostOption(Modifier) {
                }
            }
        }

        composeTestRule.onNodeWithTag("show_new_post_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("show_new_post_button").performClick()
        composeTestRule.onNodeWithTag("title_tag").assertIsDisplayed()
    }

    @Test
    fun testCreateNewPost(){
        composeTestRule.setContent {
            YTemplateTheme {
                ShowCreatePostOption(Modifier) {
                }
            }
        }

        composeTestRule.onNodeWithTag("show_new_post_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("show_new_post_button").performClick()
        composeTestRule.onNodeWithTag("new_post_title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("title_tag").performTextInput("TestTitle")
        composeTestRule.onNodeWithTag("details_tag").performTextInput("TestDetails")
        composeTestRule.onNodeWithTag("add_button").performClick()
    }
}
