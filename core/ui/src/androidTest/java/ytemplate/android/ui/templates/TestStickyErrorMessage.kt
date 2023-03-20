package ytemplate.android.ui.templates

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import ytemplate.android.core.ui.templates.StickyErrorMessageCard
import ytemplate.android.core.ui.theme.YTemplateTheme

class TestStickyErrorMessage {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testStickyErrorMessage() {
        // Start the app
        composeTestRule.setContent {
            YTemplateTheme {
                StickyErrorMessageCard("error Message")
            }
        }

        composeTestRule.onNodeWithText("error Message").assertIsDisplayed()
    }
}
