package jp.co.yumemi.android.codecheck.presentation.component.organism

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import jp.co.yumemi.android.codecheck.presentation.AppTheme
import jp.co.yumemi.android.codecheck.presentation.R

@Composable
fun AppSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = AppTheme.dimens.inputHeight),
        placeholder = {
            Text(
                text = placeholder,
                style = AppTheme.typography.body2.copy(color = colorResource(id = R.color.gray_light)),
            )
        },
        textStyle = AppTheme.typography.body2,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        colors = TextFieldDefaults.textFieldColors(
            focusedLabelColor = Color.Transparent,
            unfocusedLabelColor = Color.Transparent,
            disabledLabelColor = Color.Transparent,
            cursorColor = colorResource(id = R.color.blue_normal),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        singleLine = true,
    )
}

@Preview
@Composable
private fun AppSearchFieldPreview() {
    AppTheme {
        Surface {
            AppSearchField(
                value = "",
                onValueChange = {},
            )
        }
    }
}
