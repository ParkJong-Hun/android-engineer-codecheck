@file:Suppress("ForbiddenComment")

package jp.co.yumemi.android.codecheck

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

@HiltViewModel
class TopViewModel(
    // TODO: DI.
    private val nowProvider: NowProvider = NowProviderImpl(),
) : ViewModel() {
    private val _lastSearchDate = MutableStateFlow<LocalDate?>(null)
    val lastSearchDate = _lastSearchDate.asStateFlow()

    fun onSearched() {
        _lastSearchDate.value = nowProvider.localDateNow()
    }
}
