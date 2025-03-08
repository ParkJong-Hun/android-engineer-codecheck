package jp.co.yumemi.android.codecheck

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

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
