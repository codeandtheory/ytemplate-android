package ytemplate.android.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import ytemplate.android.MainActivity

/**
 * Main navigation kt test
 *
 * @constructor Creates empty Main navigation kt test
 */
@HiltAndroidTest
class MainNavigationKtTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * Test navigation launched
     *
     */
    @Test
    fun testNavigationLaunched() {
        composeTestRule.onNodeWithTag("show_new_post_button").assertIsDisplayed()
    }
}
