package ytemplate.android.core.analyticslib

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AnalyticsManagerTest {

    lateinit var analyticsManager: AnalyticsManager
    private val dummyAnalyticsEngine = DummyAnalyticsEngine()

    @BeforeEach
    fun init() {
        val engineList = mutableListOf<AnalyticsEngine>()
        engineList.add(dummyAnalyticsEngine)
        analyticsManager = AnalyticsManager(engineList)
    }

    @Test
    fun testEngineDefaultValue() {
        val engine = DummyAnalyticsEngine()
        val engineList = mutableListOf<AnalyticsEngine>()
        engineList.add(engine)
        assertEquals(false, engine.isEngineOn)
        AnalyticsManager(engineList)
        assertEquals(true, engine.isEngineOn)
    }

    @Test
    fun testEngineProviderList() {
        assertTrue(analyticsManager.providerList.isNotEmpty())
    }

    @Test
    fun testEngineLogAnalyticsEvent() {
        val valueMap = mapOf<String, Any>()
        val event = AnalyticsEvent.Event("testEvent", valueMap)
        analyticsManager.logEvent(event)
        assertEquals(event, dummyAnalyticsEngine.currentEvent)
    }

    @Test
    fun testEngineLogAnalyticsProperty() {
        val event = AnalyticsEvent.Property("testPropertyName", "testPropertyValue")
        analyticsManager.logEvent(event)
        assertEquals(event, dummyAnalyticsEngine.currentEvent)
    }

    @Test
    fun testEngineLogAnalyticsScreenName() {
        val event = AnalyticsEvent.ScreenName("testScreenName")
        analyticsManager.logEvent(event)
        assertEquals(event, dummyAnalyticsEngine.currentEvent)
    }

    @Test
    fun testEngineLogAnalyticsEventClass() {
        val eventScreenNameOne = AnalyticsEvent.ScreenName("testScreenNameOne")
        analyticsManager.logEvent(eventScreenNameOne)
        val eventScreenNameTwo = AnalyticsEvent.ScreenName("testScreenNameTwo")
        assertNotEquals(eventScreenNameOne, eventScreenNameTwo)
    }

    @Test
    fun testEngineOffState() {
        val testEvent = AnalyticsEvent.ScreenName("testEngineOffState")
        dummyAnalyticsEngine.isEngineOn = false
        analyticsManager.logEvent(testEvent)
        assertNotEquals(testEvent, dummyAnalyticsEngine.currentEvent)
        dummyAnalyticsEngine.isEngineOn = true
        analyticsManager.logEvent(testEvent)
        assertEquals(testEvent, dummyAnalyticsEngine.currentEvent)
    }
}
