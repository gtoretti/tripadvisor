/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gtoretti.drego.ui.menu

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gtoretti.drego.R
import com.gtoretti.drego.ui.theme.DREGoTheme

@Composable
fun AppNavRail(currentRoute: String, navigateToHome: () -> Unit, navigateToAccountingAccounts: () -> Unit,navigateToBalanceSheet: () -> Unit,navigateToAssetEvolution: () -> Unit,modifier: Modifier = Modifier) {
    NavigationRail(
        header = {
            Icon(
                painterResource(R.drawable.ic_more_vert),
                null,
                Modifier.padding(vertical = 12.dp),
                tint = MaterialTheme.colorScheme.primary,
            )
        },
        modifier = modifier,
    ) {
        Spacer(Modifier.weight(1f))
        NavigationRailItem(
            selected = currentRoute == DREGoDestinations.HOME,
            onClick = navigateToHome,
            icon = { Icon(painterResource(id = R.drawable.ic_home), stringResource(R.string.home_title)) },
            label = { Text(stringResource(R.string.home_title)) },
            alwaysShowLabel = false,
        )
        NavigationRailItem(
            selected = currentRoute == DREGoDestinations.HOSPEDAGEM,
            onClick = navigateToAccountingAccounts,
            icon = { Icon(painterResource(id = R.drawable.ic_list_alt), stringResource(R.string.hospedagem_title)) },
            label = { Text(stringResource(R.string.hospedagem_title)) },
            alwaysShowLabel = false,
        )
        NavigationRailItem(
            selected = currentRoute == DREGoDestinations.GASTRONOMIA,
            onClick = navigateToAccountingAccounts,
            icon = { Icon(painterResource(id = R.drawable.ic_list_alt), stringResource(R.string.gastronomia_title)) },
            label = { Text(stringResource(R.string.gastronomia_title)) },
            alwaysShowLabel = false,
        )
        NavigationRailItem(
            selected = currentRoute == DREGoDestinations.COMPRAS,
            onClick = navigateToAccountingAccounts,
            icon = { Icon(painterResource(id = R.drawable.ic_list_alt), stringResource(R.string.compras_title)) },
            label = { Text(stringResource(R.string.compras_title)) },
            alwaysShowLabel = false,
        )
        NavigationRailItem(
            selected = currentRoute == DREGoDestinations.LAZER,
            onClick = navigateToAccountingAccounts,
            icon = { Icon(painterResource(id = R.drawable.ic_list_alt), stringResource(R.string.lazer_title)) },
            label = { Text(stringResource(R.string.lazer_title)) },
            alwaysShowLabel = false,
        )
        NavigationRailItem(
            selected = currentRoute == DREGoDestinations.CULTURA,
            onClick = navigateToAccountingAccounts,
            icon = { Icon(painterResource(id = R.drawable.ic_list_alt), stringResource(R.string.cultura_title)) },
            label = { Text(stringResource(R.string.cultura_title)) },
            alwaysShowLabel = false,
        )
        Spacer(Modifier.weight(1f))
    }
}

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppNavRail() {
    DREGoTheme {
        AppNavRail(
            currentRoute = DREGoDestinations.HOME,
            navigateToHome = {},
            navigateToAccountingAccounts = {},
            navigateToBalanceSheet = {},
            navigateToAssetEvolution = {}
        )
    }
}
