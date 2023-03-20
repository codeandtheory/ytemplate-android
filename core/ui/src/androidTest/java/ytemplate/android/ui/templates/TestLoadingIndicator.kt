package ytemplate.android.ui.templates

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test
import ytemplate.android.core.ui.templates.AppLoadingIndicator
import ytemplate.android.core.ui.theme.YTemplateTheme

class TestLoadingIndicator {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoadingIndicator() {
        // Start the app
        composeTestRule.setContent {
            YTemplateTheme {
                AppLoadingIndicator()
            }
        }

        composeTestRule.onNodeWithTag("loadingIndicator").assertIsDisplayed()
    }
}
