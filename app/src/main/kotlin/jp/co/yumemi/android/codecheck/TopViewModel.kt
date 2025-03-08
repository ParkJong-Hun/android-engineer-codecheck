package jp.co.yumemi.android.codecheck

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class TopViewModel : ViewModel() {
    private val _lastSearchDate = MutableStateFlow<LocalDate?>(null)
    val lastSearchDate = _lastSearchDate.asStateFlow()

    fun onSearched() {
        _lastSearchDate.value = LocalDate.now()
    }
}
