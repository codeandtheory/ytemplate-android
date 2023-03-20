package ytemplate.android.core.analytics.firebase

import android.content.Context
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import ytemplate.android.core.analyticslib.AnalyticsEngine
import ytemplate.android.core.analyticslib.AnalyticsEvent

class FirebaseAnalyticsEngine(private val context: Context) : AnalyticsEngine {
    @VisibleForTesting
    lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun logEvent(event: AnalyticsEvent) {
        when (event) {
            is AnalyticsEvent.Event -> {
                firebaseAnalytics.logEvent(event.eventName, event.values.toBundle())
            }
            is AnalyticsEvent.ScreenName -> {
                firebaseAnalytics.logEvent(
                    FirebaseAnalytics.Event.SCREEN_VIEW,
                    Bundle().apply {
                        putString(FirebaseAnalytics.Param.SCREEN_NAME, event.screenName)
                    }
                )
            }
            is AnalyticsEvent.Property -> {
                firebaseAnalytics.setUserProperty(event.propertyName, event.propertyValue)
            }
        }
    }

    override fun initializeUserPropertiesToDefaultValues() {
        super.initializeUserPropertiesToDefaultValues()
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }
}

internal fun Map<String, Any>.toBundle() = bundleOf(*this.toList().toTypedArray())
