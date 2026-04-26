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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


import com.gtoretti.drego.ui.compras.ComprasScreen
import com.gtoretti.drego.ui.cultura.CulturaScreen
import com.gtoretti.drego.ui.gastronomia.GastronomiaScreen


import com.gtoretti.drego.ui.home.HomeScreen
import com.gtoretti.drego.ui.hospedagem.HospedagemScreen
import com.gtoretti.drego.ui.lazer.LazerScreen


@Composable
fun DREGoAppNavGraph(
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit = {},
    startDestination: String = DREGoDestinations.HOME,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(DREGoDestinations.HOME) {
            HomeScreen(
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }

        composable(DREGoDestinations.HOSPEDAGEM) {
            HospedagemScreen(
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }
        composable(DREGoDestinations.GASTRONOMIA) {
            GastronomiaScreen(
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }
        composable(DREGoDestinations.COMPRAS) {
            ComprasScreen(
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }
        composable(DREGoDestinations.LAZER) {
            LazerScreen(
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }
        composable(DREGoDestinations.CULTURA) {
            CulturaScreen(
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
            )
        }

    }
}
