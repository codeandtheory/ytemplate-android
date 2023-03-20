package ytemplate.android.core.analyticslib

/**
 * Interface to be extended by the provider class to log analytics events and screen names
 */
interface AnalyticsEngine {
    /**
     * This function is to log an event.
     *
     * @param event - Analytics Event
     */
    fun logEvent(event: AnalyticsEvent)

    /**
     * This function is to initialize all the global user properties to default values.
     */
    fun initializeUserPropertiesToDefaultValues() {}

    /**
     * Method for enable/disable analytics provider. Default value considering as true
     * @return [Boolean]
     */
    fun isEnabled() = true
}

/**
 * Class helping for creating analytics event. Here analytics event categorised in three section
 *  1- Event : Representing for all analytic log event data
 *  2- ScreenName: Representing analytics screen name value.
 *  3- Property: Representing user property value
 */
sealed class AnalyticsEvent {
    /**
     * Event data class
     * @param eventName: Event name
     * @param values: Event values
     */
    open class Event(val eventName: String, val values: Map<String, Any>) :
        AnalyticsEvent()

    /**
     * Screen Name data class for tracking user screens
     * @param screenName: Screen name value
     */
    data class ScreenName(val screenName: String, val values: Map<String, Any>? = null) :
        AnalyticsEvent()

    /**
     * For setting property value
     * @param propertyName: Respective property name
     * @param propertyValue: Respective property value
     */
    data class Property(val propertyName: String, val propertyValue: String) : AnalyticsEvent()
}
