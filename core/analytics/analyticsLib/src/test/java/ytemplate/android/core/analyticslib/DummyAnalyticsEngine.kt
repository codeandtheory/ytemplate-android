package ytemplate.android.core.analyticslib

/**
 * Dummy engine for testing purpose.
 */
class DummyAnalyticsEngine : AnalyticsEngine {
    var isEngineOn: Boolean = false
    var currentEvent: AnalyticsEvent? = null
    override fun logEvent(event: AnalyticsEvent) {
        currentEvent = when (event) {
            is AnalyticsEvent.Event -> {
                // In reality here the provider should consider for logging the event
                event
            }
            is AnalyticsEvent.Property -> {
                // In reality here the provider should consider for setting the property
                event
            }
            is AnalyticsEvent.ScreenName -> {
                // In reality here the provider should consider for setting the screen name
                event
            }
            else -> {
                null
            }
        }
    }

    override fun isEnabled() = isEngineOn

    override fun initializeUserPropertiesToDefaultValues() {
        super.initializeUserPropertiesToDefaultValues()
        isEngineOn = true
    }
}
