package jp.co.yumemi.android.codecheck.presentation.component.atom

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import jp.co.yumemi.android.codecheck.presentation.AppTheme
import jp.co.yumemi.android.codecheck.presentation.R

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = AppTheme.dimens.buttonHeight),
        enabled = enabled,
        shape = RoundedCornerShape(AppTheme.dimens.cornerRadiusMedium),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.blue_normal)
        ),
        contentPadding = PaddingValues(
            horizontal = AppTheme.dimens.paddingButton,
            vertical = AppTheme.dimens.spacingS
        )
    ) {
        Text(
            text = text,
            style = AppTheme.typography.button
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
