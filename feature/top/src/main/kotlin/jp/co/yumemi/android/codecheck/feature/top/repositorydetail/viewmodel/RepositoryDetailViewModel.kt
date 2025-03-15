package jp.co.yumemi.android.codecheck.feature.top.repositorydetail.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.codecheck.domain.entity.History
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import jp.co.yumemi.android.codecheck.domain.middleware.AppIntent
import jp.co.yumemi.android.codecheck.domain.middleware.AppState
import jp.co.yumemi.android.codecheck.domain.middleware.core.Middleware
import jp.co.yumemi.android.codecheck.presentation.time.NowProvider
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RepositoryDetailViewModel @Inject constructor(
    private val appMiddleware: Middleware<AppState, AppIntent>,
    private val nowProvider: NowProvider,
) : ViewModel() {
    fun onRepositoryDetailCreate(openedSearchedRepository: SearchedRepository) {
        val history =
            History(
                id = UUID.randomUUID().toString(),
                openedDateTime = nowProvider.localDateTimeNow(),
                openedSearchedRepository = openedSearchedRepository,
            )
        appMiddleware.conveyIntention(AppIntent.RecordHistory(history))
    }
}
