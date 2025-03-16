@file:Suppress("TooManyFunctions", "UnusedPrivateMember", "FunctionNaming")
package jp.co.yumemi.android.codecheck.presentation.component.atom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.codecheck.presentation.AppTheme

@Composable
fun Headline1(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null
) {
    Text(
        text = text,
        style = AppTheme.typography.headline1,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun Headline2(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null
) {
    Text(
        text = text,
        style = AppTheme.typography.headline2,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun Headline3(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null
) {
    Text(
        text = text,
        style = AppTheme.typography.headline3,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun Subtitle1(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null
) {
    Text(
        text = text,
        style = AppTheme.typography.subtitle1,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun Subtitle2(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    color: Color? = null,
) {
    Text(
        text = text,
        style = AppTheme.typography.subtitle2,
        color = color ?: AppTheme.typography.subtitle2.color,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun Body1(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null
) {
    Text(
        text = text,
        style = AppTheme.typography.body1,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun Body2(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null
) {
    Text(
        text = text,
        style = AppTheme.typography.body2,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun Caption(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null
) {
    Text(
        text = text,
        style = AppTheme.typography.caption,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Preview(name = "Headline1")
@Composable
private fun Headline1Preview() {
    AppTheme {
        Surface {
            Headline1(text = "Headline1")
        }
    }
}

@Preview(name = "Headline2")
@Composable
private fun Headline2Preview() {
    AppTheme {
        Surface {
            Headline2(text = "Headline2")
        }
    }
}

@Preview(name = "Headline3")
@Composable
private fun Headline3Preview() {
    AppTheme {
        Surface {
            Headline3(text = "Headline3")
        }
    }
}

@Preview(name = "Subtitle1")
@Composable
private fun Subtitle1Preview() {
    AppTheme {
        Surface {
            Subtitle1(text = "Subtitle1")
        }
    }
}

@Preview(name = "Subtitle2")
@Composable
private fun Subtitle2Preview() {
    AppTheme {
        Surface {
            Subtitle2(text = "Subtitle2")
        }
    }
}

@Preview(name = "Body1")
@Composable
private fun Body1Preview() {
    AppTheme {
        Surface {
            Body1(text = "Body1")
        }
    }
}

@Preview(name = "Body2")
@Composable
private fun Body2Preview() {
    AppTheme {
        Surface {
            Body2(text = "Body2")
        }
    }
}

@Preview(name = "Caption")
@Composable
private fun CaptionPreview() {
    AppTheme {
        Surface {
            Caption(text = "Caption")
        }
    }
}

@Preview(name = "All Text Styles", showBackground = true)
@Composable
private fun AllTextStylesPreview() {
    AppTheme {
        Surface {
            Column(modifier = Modifier.padding(16.dp)) {
                Headline1(text = "Headline1", modifier = Modifier.padding(vertical = 4.dp))
                Headline2(text = "Headline2", modifier = Modifier.padding(vertical = 4.dp))
                Headline3(text = "Headline3", modifier = Modifier.padding(vertical = 4.dp))
                Subtitle1(text = "Subtitle1", modifier = Modifier.padding(vertical = 4.dp))
                Subtitle2(text = "Subtitle2", modifier = Modifier.padding(vertical = 4.dp))
                Body1(text = "Body1", modifier = Modifier.padding(vertical = 4.dp))
                Body2(text = "Body2", modifier = Modifier.padding(vertical = 4.dp))
                Caption(text = "Caption", modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}
