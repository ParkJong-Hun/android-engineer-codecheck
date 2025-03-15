package jp.co.yumemi.android.codecheck.feature.history

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import jp.co.yumemi.android.codecheck.domain.entity.History
import jp.co.yumemi.android.codecheck.domain.entity.SearchedRepository
import java.time.LocalDateTime

@Suppress("MagicNumber")
private val mockOpenedDateTime = LocalDateTime.of(2025, 3, 11, 22, 8, 32, 0)

private val mockHistories = listOf(
    History(
        id = "1",
        openedDateTime = mockOpenedDateTime,
        openedSearchedRepository = SearchedRepository(
            name = "kotlin/kotlinx.coroutines",
            ownerIconUrl = "https://avatar.url/kotlin",
            language = "Kotlin",
            stargazersCount = 12500,
            watchersCount = 450,
            forksCount = 1800,
            openIssuesCount = 120
        )
    ),
    History(
        id = "2",
        openedDateTime = mockOpenedDateTime,
        openedSearchedRepository = SearchedRepository(
            name = "JetBrains/compose-multiplatform",
            ownerIconUrl = "https://avatar.url/jetbrains",
            language = "Kotlin",
            stargazersCount = 9800,
            watchersCount = 320,
            forksCount = 850,
            openIssuesCount = 75
        )
    ),
    History(
        id = "3",
        openedDateTime = mockOpenedDateTime,
        openedSearchedRepository = SearchedRepository(
            name = "google/accompanist",
            ownerIconUrl = "https://avatar.url/google",
            language = "Kotlin",
            stargazersCount = 6200,
            watchersCount = 210,
            forksCount = 520,
            openIssuesCount = 45
        )
    )
)


internal class SearchHistoryUiStateProvider : PreviewParameterProvider<SearchHistoryUiState> {
    override val values: Sequence<SearchHistoryUiState>
        get() {
            return sequenceOf(
                SearchHistoryUiState.Empty,
                SearchHistoryUiState.Idle(histories = listOf(mockHistories.first())),
                SearchHistoryUiState.Idle(histories = mockHistories)
            )
        }
}

internal class HistoryProvider : PreviewParameterProvider<History> {
    override val values: Sequence<History>
        get() = mockHistories.asSequence()
}
