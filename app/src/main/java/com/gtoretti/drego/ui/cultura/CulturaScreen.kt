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

package com.gtoretti.drego.ui.cultura


import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.verticalScroll


import androidx.compose.material3.CenterAlignedTopAppBar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Scaffold

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf

import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.gtoretti.drego.R
import com.gtoretti.drego.ui.compras.LocationScreen
import com.gtoretti.drego.ui.compras.calculateDistance
import com.gtoretti.drego.ui.compras.getCurrentLat
import com.gtoretti.drego.ui.compras.getCurrentLocation
import com.gtoretti.drego.ui.compras.getCurrentLong
import kotlin.Boolean



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CulturaScreen(
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    CulturaHomeScreen(
        isExpandedScreen = isExpandedScreen,
        openDrawer = openDrawer,
        snackbarHostState,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CulturaHomeScreen(
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    snackbarHostState: SnackbarHostState,
) {

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.cultura_title),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                navigationIcon = {
                    if (!isExpandedScreen) {
                        IconButton(onClick = openDrawer) {
                            Icon(
                                painter = painterResource(R.drawable.ic_more_vert),
                                contentDescription = stringResource(
                                    R.string.cd_open_navigation_drawer,
                                ),
                            )
                        }
                    }
                },
                actions = {
                },
            )
        },
    ) { innerPadding ->
        val screenModifier = Modifier.padding(innerPadding)
        CulturaHomeScreenContent(
            screenModifier,
        )
    }
}



@Composable
private fun CulturaHomeScreenContent(
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .verticalScroll(rememberScrollState()),
        ) {

            val context = LocalContext.current
            var latAtual = remember { mutableDoubleStateOf(0.0) }
            var longAtual = remember { mutableDoubleStateOf(0.0) }

            /////////////////////////////// Botao usado para exibir a localização atual:
            LocationScreen(
                onRequestLocation = {
                    getCurrentLocation(context,latAtual, longAtual, it)
                }
            )
            ///////////////////////////////////////////////////////////////////////////////////

            val listaItens = listaItensCultura()
            listaItens.forEach { item->
                exibirCadaClienteCultura(context,item.descricao,item.endereco,latAtual.value,longAtual.value,item.lat,item.long)
            }



        }
    }
}


fun openMapWithAddress(address: String, context: Context) {
    val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    context.startActivity(mapIntent)
}


@Composable
fun exibirCadaClienteCultura(context: Context, descricao: String, endereco: String, latOrigem: Double, longOrigem: Double, latDestino: Double, longDestino: Double){

    var distancia = calculateDistance(latOrigem, longOrigem, latDestino, longDestino)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Image on the left
        Image(
            painter = painterResource(id = R.drawable.post_2_thumb),
            contentDescription = "App Icon",
            modifier = Modifier
                .size(100.dp)
                .padding(all = 8.dp)
        )

        // Text on the right
        Text(modifier = Modifier
            .fillMaxWidth(0.4f),
            text = descricao,
            fontSize = 18.sp,

            color = Color.Red, // Set text color
            fontWeight = FontWeight.Bold
        )

        // botao exibir no Maps
        TextButton(
            modifier = Modifier.padding(5.dp),
            onClick =
                {
                    openMapWithAddress(endereco,
                        context
                    )
                }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.location_on_24px),
                contentDescription = "Mapa",
                modifier = Modifier
                    .padding(end = 2.dp)
                    .size(30.dp)
            )
        }

        // Text on the right
        Text(
            text = "Distância:" + distancia,
            fontSize = 15.sp,

        )

    }


}


data class ItemCultura(
    var descricao: String,
    var endereco: String,
    var lat: Double,
    var long: Double,
)

fun listaItensCultura(): List<ItemCultura>{
    var retorno = ArrayList<ItemCultura>()

    var cliente1: ItemCultura = ItemCultura("Res. Figueira Branca","Av. Alexandre Cazellato, 2660, Betel, Paulinia, SP",-22.78,-47.10)
    var cliente2: ItemCultura = ItemCultura("Parque Brasil 500","Parque Brasil 500 - Paulinia - SP",-22.78,-47.10)
    var cliente3: ItemCultura = ItemCultura("Theatro Municipal de Paulínia","Av. José Lozano Araújo, 1551 - Parque Brasil 500, Paulínia - SP, 13140-000",-22.78,-47.10)
    var cliente4: ItemCultura = ItemCultura("Rã-Chu Churrascaria à la carte - Unidade Paulínia","R. Chile, 172 - Jardim America, Paulínia - SP, 13140-000",-22.78,-47.10)
    var cliente5: ItemCultura = ItemCultura("Paulínia Winner Mall Shopping","Av. José Lozano Araújo, n° 1515 - Nossa Senhora Aparecida, Paulínia - SP, 13140-560",-22.78,-47.10)

    retorno.add(cliente1)
    retorno.add(cliente2)
    retorno.add(cliente3)
    retorno.add(cliente4)
    retorno.add(cliente5)

    return retorno
}



