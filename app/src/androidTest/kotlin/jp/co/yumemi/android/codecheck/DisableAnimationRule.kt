package jp.co.yumemi.android.codecheck

import android.app.Instrumentation
import android.os.IBinder
import android.provider.Settings
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class DisableAnimationsRule : TestRule {
    private val WINDOW_ANIMATION_SCALE = "window_animation_scale"
    private val TRANSITION_ANIMATION_SCALE = "transition_animation_scale"
    private val ANIMATOR_DURATION_SCALE = "animator_duration_scale"

    private var originalWindowScale = 0f
    private var originalTransitionScale = 0f
    private var originalAnimatorScale = 0f

    private val instrumentation: Instrumentation
        get() = InstrumentationRegistry.getInstrumentation()

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                try {
                    disableAnimations()
                    base.evaluate()
                } finally {
                    enableAnimations()
                }
            }
        }
    }

    private fun disableAnimations() {
        originalWindowScale = getAnimationScale(WINDOW_ANIMATION_SCALE)
        originalTransitionScale = getAnimationScale(TRANSITION_ANIMATION_SCALE)
        originalAnimatorScale = getAnimationScale(ANIMATOR_DURATION_SCALE)

        setAnimationScale(WINDOW_ANIMATION_SCALE, 0f)
        setAnimationScale(TRANSITION_ANIMATION_SCALE, 0f)
        setAnimationScale(ANIMATOR_DURATION_SCALE, 0f)
    }

    private fun enableAnimations() {
        setAnimationScale(WINDOW_ANIMATION_SCALE, originalWindowScale)
        setAnimationScale(TRANSITION_ANIMATION_SCALE, originalTransitionScale)
        setAnimationScale(ANIMATOR_DURATION_SCALE, originalAnimatorScale)
    }

    private fun getAnimationScale(key: String): Float {
        return Settings.Global.getFloat(
            instrumentation.targetContext.contentResolver,
            key,
            1f
        )
    }

    private fun setAnimationScale(key: String, value: Float) {
        try {
            val serviceName = "statusbar"
            val windowManagerClass = Class.forName("android.view.IWindowManager")
            val windowManagerObject = getWindowManager(serviceName)
            val setAnimationScaleMethod = windowManagerClass.getDeclaredMethod(
                "setAnimationScale",
                Int::class.javaPrimitiveType,
                Float::class.javaPrimitiveType
            )

            val animationTypeIndex = when (key) {
                WINDOW_ANIMATION_SCALE -> 0
                TRANSITION_ANIMATION_SCALE -> 1
                ANIMATOR_DURATION_SCALE -> 2
                else -> throw IllegalArgumentException("Unknown animation scale type: $key")
            }

            setAnimationScaleMethod.invoke(windowManagerObject, animationTypeIndex, value)
        } catch (e: Exception) {
            println("Failed to modify animation scale: ${e.message}")
        }
    }

    private fun getWindowManager(serviceName: String): Any? {
        try {
            val serviceManagerClass = Class.forName("android.os.ServiceManager")
            val getServiceMethod =
                serviceManagerClass.getDeclaredMethod("getService", String::class.java)
            val windowManagerStub = getServiceMethod.invoke(null, serviceName) as IBinder
            val asInterfaceMethod = Class.forName("android.view.IWindowManager\$Stub")
                .getDeclaredMethod("asInterface", IBinder::class.java)
            return asInterfaceMethod.invoke(null, windowManagerStub)
        } catch (e: Exception) {
            println("Failed to get window manager: ${e.message}")
            return null
        }
    }
}
