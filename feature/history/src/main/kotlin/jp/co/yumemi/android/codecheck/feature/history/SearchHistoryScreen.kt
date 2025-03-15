// TODO: detektにComposeのための設定を入れる。
@file:Suppress("FunctionNaming", "ForbiddenComment")

package jp.co.yumemi.android.codecheck.feature.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.co.yumemi.android.codecheck.domain.entity.History

@Composable
fun SearchHistoryScreen(
    viewModel: SearchHistoryViewModel,
    onClickSearchHistoryItem: (History) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(uiState) {
        (uiState as? SearchHistoryUiState.Idle)?.let { idleState ->
            val (clickedHistory, hasClickedHistory) = idleState.onClickedHistory
            if (hasClickedHistory) onClickSearchHistoryItem(requireNotNull(clickedHistory))
        }
    }
    MaterialTheme {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            SearchHistoryScreen(
                uiState,
                onClickHistory = {
                    viewModel.uiEvent.tryEmit(SearchHistoryUiEvent.OnClickHistory(it))
                }
            )
        }
    }
}

@Composable
private fun SearchHistoryScreen(
    uiState: SearchHistoryUiState,
    onClickHistory: (History) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        when (uiState) {
            is SearchHistoryUiState.Idle -> {
                HistoryList(
                    histories = uiState.histories,
                    onClickHistory = onClickHistory,
                )
            }

            is SearchHistoryUiState.Empty -> {
                Text("it's empty")
            }
        }
    }
}

@Composable
fun HistoryList(
    histories: List<History>,
    onClickHistory: (History) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = histories,
            key = { history -> history.id }
        ) { history ->
            HistoryItem(
                history = history,
                onClickHistory = onClickHistory,
            )
        }
    }
}

@Composable
fun HistoryItem(
    history: History,
    onClickHistory: (History) -> Unit,
    modifier: Modifier = Modifier,
) {
//    AndroidView(
//        factory = {
//            val view = LayoutInflater.from(it)
//                .inflate(R.layout.layout_searched_repository, null, false)
//            view
//        },
//        update = {
//          it.findViewById<TextView>(R.id.repositoryNameView).text = history.openedSearchedRepository.name
//        }
//    )
    Column(
        modifier = modifier.clickable { onClickHistory(history) },
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            text = history.openedSearchedRepository.name,
            fontSize = 12.sp,
            color = colorResource(jp.co.yumemi.android.codecheck.presentation.R.color.black)
        )
        HorizontalDivider()
    }
}

@Preview
@Composable
fun SearchHistoryScreenPreview(
    @PreviewParameter(SearchHistoryUiStateProvider::class) uiState: SearchHistoryUiState
) {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SearchHistoryScreen(
                uiState = uiState,
                onClickHistory = {},
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Preview
@Composable
fun HistoryItemPreview(
    @PreviewParameter(HistoryProvider::class) history: History
) {
    MaterialTheme {
        Surface {
            HistoryItem(
                history = history,
                onClickHistory = {},
            )
        }
    }
}
