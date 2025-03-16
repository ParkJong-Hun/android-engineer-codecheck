package jp.co.yumemi.android.codecheck.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit

@Composable
@ReadOnlyComposable
fun textSizeResource(id: Int): TextUnit {
    val context = LocalContext.current
    val pixelValue = context.resources.getDimension(id)
    return with(LocalDensity.current) {
        pixelValue.toSp()
    }
}
