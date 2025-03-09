package jp.co.yumemi.android.codecheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import jp.co.yumemi.android.codecheck.presentation.autoCleared
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AutoClearedValueTest {

    @JvmField
    @Rule
    val activityScenarioRule = activityScenarioRule<SingleFragmentActivity>()

    private lateinit var testFragment: TestFragment

    @Before
    fun init() {
        testFragment = TestFragment()
        activityScenarioRule.scenario.onActivity {
            it.setFragment(testFragment)
        }
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
    }

    @Test
    fun clearOnReplace() {
        activityScenarioRule.scenario.onActivity {
            it.replaceFragment(TestFragment())
        }
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        try {
            testFragment.testValue
            Assert.fail("should throw if accessed when not set")
        } catch (_: IllegalStateException) {
        }
    }

    @Test
    fun clearOnReplaceBackStack() {
        activityScenarioRule.scenario.onActivity {
            it.replaceFragment(TestFragment(), true)
        }
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        try {
            testFragment.testValue
            Assert.fail("should throw if accessed when not set")
        } catch (_: IllegalStateException) {
        }
        activityScenarioRule.scenario.onActivity {
            it.supportFragmentManager.popBackStack()
        }
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        assertThat(testFragment.testValue, `is`("foo"))
    }

    @Test
    fun dontClearForChildFragment() {
        testFragment.childFragmentManager.beginTransaction()
            .add(Fragment(), "foo").commit()
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        assertThat(testFragment.testValue, `is`("foo"))
    }

    @Test
    fun dontClearForDialog() {
        val dialogFragment = DialogFragment()
        dialogFragment.show(testFragment.parentFragmentManager, "dialog")
        dialogFragment.dismiss()
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        assertThat(testFragment.testValue, `is`("foo"))
    }

    class TestFragment : Fragment() {
        var testValue by autoCleared<String>()

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            testValue = "foo"
            return View(context)
        }
    }
}
