package jp.co.yumemi.android.codecheck.feature.history.searchhistory

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import jp.co.yumemi.android.codecheck.domain.entity.History
import jp.co.yumemi.android.codecheck.feature.history.R
import jp.co.yumemi.android.codecheck.feature.history.viewmodel.SearchHistoryUiState
import jp.co.yumemi.android.codecheck.feature.history.viewmodel.getFormattedDetails
import jp.co.yumemi.android.codecheck.presentation.AppTheme
import jp.co.yumemi.android.codecheck.presentation.component.atom.Body1
import jp.co.yumemi.android.codecheck.presentation.component.atom.Caption
import jp.co.yumemi.android.codecheck.presentation.component.atom.Subtitle2
import jp.co.yumemi.android.codecheck.presentation.component.organism.AppListItemCard

@Composable
fun SearchHistoryScreen(
    uiState: SearchHistoryUiState,
    onClickHistory: (History) -> Unit,
    modifier: Modifier = Modifier,
) {
    AppTheme {
        Surface(
            modifier = modifier,
            color = AppTheme.colors.background,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
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
                        Body1(text = stringResource(R.string.no_results_found))
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryList(
    histories: List<History>,
    onClickHistory: (History) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.spacingS),
    ) {
        items(
            items = histories,
            key = { history -> history.id },
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
    AppListItemCard(
        modifier = modifier,
        onClick = { onClickHistory(history) },
    ) {
        Column(
            modifier = Modifier.padding(AppTheme.dimens.paddingCard),
        ) {
            Subtitle2(
                text = history.openedSearchedRepository.name,
                color = colorResource(id = jp.co.yumemi.android.codecheck.presentation.R.color.black_light),
                modifier = Modifier.padding(bottom = AppTheme.dimens.spacingXS),
            )

            if (history.openedSearchedRepository.language.isNotEmpty() ||
                history.openedSearchedRepository.stargazersCount > 0
            ) {
                // FIXME: パーフォマンスのためにUI側でやらなくても良い設計に修正する必要がある。
                Caption(text = history.getFormattedDetails())
            }
        }
    }
}

@Preview
@Composable
private fun SearchHistoryScreenPreview(
    @PreviewParameter(SearchHistoryUiStateProvider::class) uiState: SearchHistoryUiState,
) {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppTheme.colors.background,
        ) {
            SearchHistoryScreen(
                uiState = uiState,
                onClickHistory = {},
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun HistoryItemPreview(
    @PreviewParameter(HistoryProvider::class) history: History,
) {
    AppTheme {
        Surface {
            HistoryItem(
                history = history,
                onClickHistory = {},
            )
        }
    }
}
