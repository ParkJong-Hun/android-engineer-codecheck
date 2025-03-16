package jp.co.yumemi.android.codecheck.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

@Composable
fun getColorScheme(darkTheme: Boolean): ColorScheme {
    return if (darkTheme) {
        darkColorScheme(
            primary = colorResource(id = R.color.gray_normal),
            onPrimary = colorResource(id = R.color.black_normal),
            primaryContainer = colorResource(id = R.color.gray_dark),
            secondary = colorResource(id = R.color.blue_light),
            onSecondary = colorResource(id = R.color.white_normal),
            secondaryContainer = colorResource(id = R.color.blue_normal),
            background = colorResource(id = R.color.black_light),
            surface = colorResource(id = R.color.black_light),
            onBackground = colorResource(id = R.color.white_normal),
            onSurface = colorResource(id = R.color.white_dark)
        )
    } else {
        lightColorScheme(
            primary = colorResource(id = R.color.gray_normal),
            onPrimary = colorResource(id = R.color.white_normal),
            primaryContainer = colorResource(id = R.color.gray_dark),
            secondary = colorResource(id = R.color.blue_light),
            onSecondary = colorResource(id = R.color.black_light),
            secondaryContainer = colorResource(id = R.color.blue_normal),
            background = colorResource(id = R.color.white_normal),
            surface = colorResource(id = R.color.white_normal),
            onBackground = colorResource(id = R.color.black_normal),
            onSurface = colorResource(id = R.color.black_light)
        )
    }
}

class AppDimensions {
    val spacingXS: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.spacing_xs)
    val spacingS: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.spacing_s)
    val spacingM: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.spacing_m)
    val spacingL: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.spacing_l)
    val spacingXL: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.spacing_xl)
    val spacingXXL: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.spacing_xxl)

    val paddingButton: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.padding_button)
    val paddingCard: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.padding_card)
    val paddingInput: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.padding_input)

    val marginBetweenItems: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.margin_between_items)
    val marginScreenHorizontal: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.margin_screen_horizontal)
    val marginScreenVertical: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.margin_screen_vertical)

    val buttonHeight: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.button_height)
    val inputHeight: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.input_height)
    val iconSizeSmall: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.icon_size_small)
    val iconSizeMedium: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.icon_size_medium)
    val iconSizeLarge: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.icon_size_large)

    val cornerRadiusSmall: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.corner_radius_small)
    val cornerRadiusMedium: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.corner_radius_medium)
    val cornerRadiusLarge: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.corner_radius_large)

    val elevationSmall: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.elevation_small)
    val elevationMedium: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.elevation_medium)
    val elevationLarge: Dp @Composable @ReadOnlyComposable get() = dimensionResource(id = R.dimen.elevation_large)
}

class AppTypography(private val darkTheme: Boolean = false) {
    val headline1: TextStyle
        @Composable
        @ReadOnlyComposable
        get() = TextStyle(
            fontSize = textSizeResource(id = R.dimen.text_size_display),
            fontWeight = FontWeight.Bold,
            color = colorResource(id = if (darkTheme) R.color.white_normal else R.color.black_normal),
            letterSpacing = (-0.02).sp
        )

    val headline2: TextStyle
        @Composable
        @ReadOnlyComposable
        get() = TextStyle(
            fontSize = textSizeResource(id = R.dimen.text_size_xxxl),
            fontWeight = FontWeight.Bold,
            color = colorResource(id = if (darkTheme) R.color.white_normal else R.color.black_normal),
            letterSpacing = (-0.01).sp
        )

    val headline3: TextStyle
        @Composable
        @ReadOnlyComposable
        get() = TextStyle(
            fontSize = textSizeResource(id = R.dimen.text_size_xxl),
            fontWeight = FontWeight.Bold,
            color = colorResource(id = if (darkTheme) R.color.white_normal else R.color.black_normal)
        )

    val subtitle1: TextStyle
        @Composable
        @ReadOnlyComposable
        get() = TextStyle(
            fontSize = textSizeResource(id = R.dimen.text_size_l),
            fontWeight = FontWeight.Bold,
            color = colorResource(id = if (darkTheme) R.color.white_dark else R.color.black_light)
        )

    val subtitle2: TextStyle
        @Composable
        @ReadOnlyComposable
        get() = TextStyle(
            fontSize = textSizeResource(id = R.dimen.text_size_m),
            fontWeight = FontWeight.Bold,
            color = colorResource(id = if (darkTheme) R.color.white_dark else R.color.black_light)
        )

    val body1: TextStyle
        @Composable
        @ReadOnlyComposable
        get() = TextStyle(
            fontSize = textSizeResource(id = R.dimen.text_size_m),
            color = colorResource(id = R.color.gray_normal)
        )

    val body2: TextStyle
        @Composable
        @ReadOnlyComposable
        get() = TextStyle(
            fontSize = textSizeResource(id = R.dimen.text_size_s),
            color = colorResource(id = R.color.gray_normal)
        )

    val caption: TextStyle
        @Composable
        @ReadOnlyComposable
        get() = TextStyle(
            fontSize = textSizeResource(id = R.dimen.text_size_xs),
            color = colorResource(id = R.color.gray_light)
        )

    val button: TextStyle
        @Composable
        @ReadOnlyComposable
        get() = TextStyle(
            fontSize = textSizeResource(id = R.dimen.text_size_s),
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.01.sp,
            color = if (darkTheme) colorResource(id = R.color.white_normal) else Color.Unspecified
        )
}

val LocalAppDimensions = staticCompositionLocalOf { AppDimensions() }
val LocalAppTypography = staticCompositionLocalOf { AppTypography() }

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = getColorScheme(darkTheme)
    val dimensions = AppDimensions()
    val typography = AppTypography(darkTheme)

    CompositionLocalProvider(
        LocalAppDimensions provides dimensions,
        LocalAppTypography provides typography
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}

object AppTheme {
    val dimens: AppDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalAppDimensions.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current

    val colors
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.colorScheme
}

object ResourceUtils {
    private var resourceMap = mutableMapOf<Int, Int>()

    @Composable
    fun getColor(colorRes: Int): Int {
        return resourceMap.getOrPut(colorRes) {
            val context = LocalContext.current
            context.getColor(colorRes)
        }
    }
}
