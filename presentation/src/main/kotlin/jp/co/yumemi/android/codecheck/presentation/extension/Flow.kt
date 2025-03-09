package jp.co.yumemi.android.codecheck.presentation.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.collect(
    lifecycleOwner: LifecycleOwner,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend (value: T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(state) {
            collect {
                action(it)
            }
        }
    }
}
