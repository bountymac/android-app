/*
 * Copyright (c) 2023 Proton AG
 *
 * This file is part of ProtonVPN.
 *
 * ProtonVPN is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ProtonVPN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ProtonVPN.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.protonvpn.android.base.ui.theme

import android.app.Activity
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.protonvpn.android.base.ui.LocalLocale
import com.protonvpn.android.base.ui.LocalStringProvider
import com.protonvpn.android.base.ui.StringProvider
import me.proton.core.compose.theme.ProtonTheme
import me.proton.core.compose.theme.ProtonTheme3
import me.proton.core.compose.theme.defaultUnspecified
import me.proton.core.compose.theme.isNightMode
import me.proton.core.presentation.utils.currentLocale

@Composable
fun VpnTheme(isDark: Boolean = isNightMode(), content: @Composable () -> Unit) {
    ProtonTheme(isDark = isDark) {
        ProtonTheme3(isDark = isDark) {
            val view = LocalView.current
            // Apply colors to system bars if the theme is used in an activity (don't apply when in fragments).
            if (!view.isInEditMode && view.context is Activity) {
                val bottomBarColor = ProtonTheme.colors.backgroundSecondary
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    val window = (view.context as Activity).window
                    window.navigationBarColor = bottomBarColor.toArgb()
                    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDark
                    systemUiController.setStatusBarColor(color = Color.Transparent, darkIcons = !isDark)
                }
            }
            CompositionLocalProvider(
                LocalContentColor provides ProtonTheme.colors.textNorm,
                LocalTextStyle provides ProtonTheme.typography.defaultUnspecified,
                LocalStringProvider provides StringProvider { id, formatArgs -> stringResource(id, *formatArgs) },
                LocalLocale provides LocalConfiguration.current.currentLocale(),
            ) {
                content()
            }
        }
    }
}
