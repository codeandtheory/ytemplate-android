package ytemplate.android.ui.templates

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import ytemplate.android.core.ui.templates.FullWidthDeletableCard

class TestFullWidthCard {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testFullwidthCard() {
        composeTestRule.setContent {
            FullWidthDeletableCard(Modifier, {
            }, {
                Text("sampleText")
            })
        }
        composeTestRule.onNodeWithText("sampleText").assertIsDisplayed()
    }
}
