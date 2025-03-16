package jp.co.yumemi.android.codecheck.presentation.component.organism

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.codecheck.presentation.AppTheme

@Composable
fun AppListItemCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.padding(
            horizontal = AppTheme.dimens.spacingS,
            vertical = AppTheme.dimens.spacingXS
        ),
        shape = RoundedCornerShape(AppTheme.dimens.cornerRadiusMedium),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = AppTheme.dimens.elevationSmall
        ),
        onClick = onClick
    ) {
        content()
    }
}

@Composable
fun AppSearchCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(AppTheme.dimens.cornerRadiusLarge),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = AppTheme.dimens.elevationMedium
        )
    ) {
        content()
    }
}

@Preview(name = "AppListItemCard")
@Composable
private fun AppListItemCardPreview() {
    AppTheme {
        Surface {
            AppListItemCard(onClick = {}) {
                Text("Sample")
            }
        }
    }
}

@Preview(name = "AppSearchCard")
@Composable
private fun AppSearchCardPreview() {
    AppTheme {
        Surface {
            AppSearchCard {
                Text("Sample")
            }
        }
    }
}

@Preview("All Card Styles", showBackground = true)
@Composable
private fun AllCardStylesPreview() {
    AppTheme {
        Surface {
            Column(modifier = Modifier.padding(16.dp)) {
                AppListItemCard(onClick = {}) {
                    Text("Sample")
                }
                AppSearchCard {
                    Text("Sample")
                }
            }
        }
    }
}
