package ytemplate.android.core.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * App dimensions standard set of values used inside app
 *
 * @property paddingSmall
 * @property paddingMedium
 * @property largeIconSize
 * @property mediumIconSize
 * @property bannerSize
 * @property smallIconSize
 * @property paddingLarge
 * @property verticalSpacer
 * @property horizontalSpacer
 * @property cardElevation
 * @property cornerRadius
 * @property smallText
 * @property mediumText
 * @property largeText
 * @constructor Create empty App dimensions
 */
data class AppDimensions(
    val paddingSmall: Dp = 4.dp,
    val paddingMedium: Dp = 8.dp,
    val largeIconSize: Dp = 50.dp,
    val mediumIconSize: Dp = 24.dp,
    val bannerSize: Dp = 200.dp,
    val smallIconSize: Dp = 12.dp,
    val paddingLarge: Dp = 24.dp,
    val verticalSpacer: Dp = 12.dp,
    val horizontalSpacer: Dp = 12.dp,
    val cardElevation: Dp = 10.dp,
    val cornerRadius: Dp = 10.dp,
    val thinBorder: Dp = 1.dp,
    val thickBorder: Dp = 3.dp
)
