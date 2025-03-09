package jp.co.yumemi.android.codecheck.feature.top

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.codecheck.NowProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
    private val nowProvider: NowProvider,
) : ViewModel() {
    private val _lastSearchDate = MutableStateFlow<LocalDate?>(null)
    val lastSearchDate = _lastSearchDate.asStateFlow()

    fun onSearched() {
        _lastSearchDate.value = nowProvider.localDateNow()
    }
}