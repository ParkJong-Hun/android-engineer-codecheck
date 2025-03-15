package jp.co.yumemi.android.codecheck.feature.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchHistoryFragment : Fragment() {
    private val viewModel: SearchHistoryViewModel by viewModels()

    @Inject
    lateinit var historyRouter: HistoryRouter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SearchHistoryScreen(
                    viewModel = viewModel,
                    onClickSearchHistoryItem = {
                        historyRouter.navigateToRepositoryDetail(
                            navController = findNavController(),
                            history = it,
                        )
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
