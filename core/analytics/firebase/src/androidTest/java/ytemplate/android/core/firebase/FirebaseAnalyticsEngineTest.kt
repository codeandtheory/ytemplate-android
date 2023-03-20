package ytemplate.android.core.firebase

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.analytics.FirebaseAnalytics
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.justRun
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import ytemplate.android.core.analytics.firebase.FirebaseAnalyticsEngine
import ytemplate.android.core.analyticslib.AnalyticsEvent

class FirebaseAnalyticsEngineTest {

    private lateinit var context: Context
    lateinit var firebaseEngine: FirebaseAnalyticsEngine

    @MockK(relaxUnitFun = true)
    lateinit var analytics: FirebaseAnalytics

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        context = InstrumentationRegistry.getInstrumentation().context
        firebaseEngine = spyk(FirebaseAnalyticsEngine(context))
        firebaseEngine.initializeUserPropertiesToDefaultValues()
        firebaseEngine.firebaseAnalytics = analytics
    }

    @Test
    fun testMockedObject() {
        assertNotNull(firebaseEngine.firebaseAnalytics)
    }

    @Test
    fun testLogEvent() {
        val testMap = mapOf("key" to "value")
        val event = AnalyticsEvent.Event("Event Name", testMap)
        justRun { analytics.logEvent(any(), any()) }
        firebaseEngine.logEvent(event)
        verify { analytics.logEvent(any(), any()) }
    }

    @Test
    fun testLogScreenName() {
        val event = AnalyticsEvent.ScreenName("Screen Name")
        justRun { analytics.logEvent(any(), any()) }
        firebaseEngine.logEvent(event)
        verify { analytics.logEvent(any(), any()) }
    }

    @Test
    fun testSetProperty() {
        val event = AnalyticsEvent.Property("property name", "property value")
        justRun { analytics.setUserProperty(any(), any()) }
        firebaseEngine.logEvent(event)
        verify { analytics.setUserProperty(any(), any()) }
    }
}
