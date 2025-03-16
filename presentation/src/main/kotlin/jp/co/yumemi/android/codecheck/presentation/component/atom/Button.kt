package jp.co.yumemi.android.codecheck.presentation.component.atom

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jp.co.yumemi.android.codecheck.presentation.AppTheme

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = AppTheme.dimens.buttonHeight),
        enabled = enabled,
        shape = RoundedCornerShape(AppTheme.dimens.cornerRadiusMedium),
        contentPadding = PaddingValues(
            horizontal = AppTheme.dimens.paddingButton,
            vertical = AppTheme.dimens.spacingS,
        ),
    ) {
        Text(
            text = text,
            style = AppTheme.typography.button,
        )
    }
}

@Preview
@Composable
private fun AppButtonPreview() {
    AppTheme {
        Surface {
            AppButton(text = "Button", onClick = {})
        }
    }
}
