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
package com.protonvpn.android.profiles.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text
import com.protonvpn.android.R
import com.protonvpn.android.profiles.data.Profile
import com.protonvpn.android.profiles.data.ProfileColor
import com.protonvpn.android.profiles.data.ProfileIcon
import com.protonvpn.android.profiles.data.ProfileInfo
import com.protonvpn.android.redesign.vpn.ConnectIntent
import me.proton.core.compose.theme.ProtonTheme

@Composable
fun ProfileTypeAndLocationRoute(
    profile: Profile,
    onNext: (Profile) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding()
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.create_profile_type_and_location_title),
                color = ProtonTheme.colors.textNorm,
                style = ProtonTheme.typography.body1Bold
            )
        }

        ProfileNavigationButtons(onNext = { onNext(profile) }, onBack = onBack)
    }
}

@Preview
@Composable
fun PreviewProfileTypeAndLocation() {
   ProfileTypeAndLocationRoute(        profile = Profile(
       ProfileInfo(
           id = 0,
           name = "Good profile",
           color = ProfileColor.Color1,
           icon = ProfileIcon.Icon2,
           isGateway = false,
       ),
       ConnectIntent.Fastest,
   ),
       onNext = {},
       onBack = {}
   )
}