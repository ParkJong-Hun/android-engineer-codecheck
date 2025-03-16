package jp.co.yumemi.android.codecheck.feature.history.searchhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.codecheck.feature.history.router.HistoryRouter
import jp.co.yumemi.android.codecheck.feature.history.viewmodel.SearchHistoryUiEvent
import jp.co.yumemi.android.codecheck.feature.history.viewmodel.SearchHistoryUiState
import jp.co.yumemi.android.codecheck.feature.history.viewmodel.SearchHistoryViewModel
import javax.inject.Inject

@AndroidEntryPoint
class SearchHistoryFragment : Fragment() {
    private val viewModel: SearchHistoryViewModel by viewModels()

    @Inject
    lateinit var historyRouter: HistoryRouter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val onClickedHistory = (uiState as? SearchHistoryUiState.Idle)?.onClickedHistory
                val hasOnClickedHistory = onClickedHistory?.second
                LaunchedEffect(hasOnClickedHistory) {
                    onClickedHistory?.let { (history, hasOnClickedHistory) ->
                        if (hasOnClickedHistory) {
                            historyRouter.navigateToRepositoryDetail(
                                navController = findNavController(),
                                history = requireNotNull(history),
                            )
                        }
                    }
                }
                SearchHistoryScreen(
                    uiState = uiState,
                    onClickHistory = {
                        viewModel.uiEvent.tryEmit(SearchHistoryUiEvent.OnClickHistory(it))
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
