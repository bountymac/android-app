/*
 * Copyright (c) 2024 Proton AG
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

package com.protonvpn.android.redesign.base.ui

import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import me.proton.core.compose.theme.ProtonTheme

@Composable
fun ServerLoadBar(progress: Float) {
    val color = when {
        progress <= 0.75F -> ProtonTheme.colors.notificationSuccess
        progress <= 0.9F -> ProtonTheme.colors.notificationWarning
        else -> ProtonTheme.colors.notificationError
    }
    LinearProgressIndicator(
        progress = progress,
        color = color,
        strokeCap = StrokeCap.Round,
        trackColor = ProtonTheme.colors.shade40,
        modifier = Modifier
            .width(36.dp)
    )
}