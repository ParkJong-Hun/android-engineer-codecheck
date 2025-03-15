// TODO: detektにComposeのための設定を入れる。
@file:Suppress("FunctionNaming", "ForbiddenComment")

package jp.co.yumemi.android.codecheck.feature.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.co.yumemi.android.codecheck.domain.entity.History

@Composable
fun SearchHistoryScreen(
    viewModel: SearchHistoryViewModel,
    modifier: Modifier = Modifier,
) {
    MaterialTheme {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            SearchHistoryScreen(uiState)
        }
    }
}

@Composable
private fun SearchHistoryScreen(
    uiState: SearchHistoryUiState,
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
                HistoryList(histories = uiState.histories)
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
            HistoryItem(history = history)
        }
    }
}

@Composable
fun HistoryItem(history: History) {
    Text(history.openedSearchedRepository.name)
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
            HistoryItem(history = history)
        }
    }
}
