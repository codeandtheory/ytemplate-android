package ytemplate.android.core.analyticslib

/**
 * Analytics manager class for logging analytics event.
 */
class AnalyticsManager(val providerList: List<AnalyticsEngine>) : AnalyticsEngine {
    init {
        providerList.forEach {
            it.initializeUserPropertiesToDefaultValues()
        }
    }

    override fun logEvent(event: AnalyticsEvent) {
        if (isEnabled()) {
            providerList.filter { it.isEnabled() }.forEach {
                it.logEvent(event)
            }
        }
    }
}
