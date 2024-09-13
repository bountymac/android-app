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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
fun CreateNameRoute(
    profile: Profile,
    onNext: (Profile) -> Unit
) {
    // TODO use rememberSaveable
    var mutableProfile by remember { mutableStateOf(profile) }
    var isError by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding()
    ) {
        Column(modifier = Modifier.weight(1f)) {
            TextField(
                value = mutableProfile.info.name,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.create_profile_name_hint),
                        style = ProtonTheme.typography.subheadline,
                        color = ProtonTheme.colors.textHint
                    )
                },
                supportingText = {
                    if (isError) {
                        Text(
                            text = stringResource(id = R.string.create_profile_name_error),
                            color = ProtonTheme.colors.notificationError,
                            style = ProtonTheme.typography.captionMedium
                        )
                    }
                },
                textStyle = ProtonTheme.typography.subheadline,
                isError = isError,
                onValueChange = {
                    mutableProfile = mutableProfile.copy(
                        info = mutableProfile.info.copy(
                            name = it
                        )
                    )
                    if (isError && it.isNotEmpty()) {
                        isError = false
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        ProfileNavigationButtons(onNext = {
            if (mutableProfile.info.name.isEmpty()) {
                isError = true
            } else {
                onNext(mutableProfile)
            }
        })
    }
}

@Preview
@Composable
fun PreviewProfileNameCreation() {
    CreateNameRoute(
        profile = Profile(
            ProfileInfo(
                id = 0,
                name = "Good profile",
                color = ProfileColor.Color1,
                icon = ProfileIcon.Icon2,
                isGateway = false,
            ),
            ConnectIntent.Fastest,
        ),
        onNext = {}
    )
}
