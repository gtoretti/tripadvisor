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

package com.gtoretti.drego.ui.home


import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height


import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button


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

import androidx.compose.runtime.Composable

import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp

import com.gtoretti.drego.R
import com.gtoretti.drego.ui.utils.getLightGreenColor
import com.gtoretti.drego.ui.utils.getLightRedColor

import kotlin.Boolean


/**
 * Displays AccountingAccountsHomeScreen.
 * @param isExpandedScreen (state) true if the screen is expanded
 * @param openDrawer (event) request opening the app drawer
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
            HomeScreen(
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer,
                snackbarHostState,
            )
}


/**
 * Displays AccountingAccountsHomeScreen.
 * @param isExpandedScreen (state) true if the screen is expanded
 * @param openDrawer (event) request opening the app drawer
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
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
                        text = stringResource(R.string.home_title),
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
        HomeScreenContent(
            screenModifier,
        )
    }
}

/**
 */
@Composable
private fun HomeScreenContent(
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
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
        ) {

            Spacer(Modifier.height(30.dp))

            Text(
                text = stringResource(R.string.home_bemvindo),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )


            Spacer(Modifier.height(30.dp))



            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            )
            Spacer(Modifier.height(30.dp))


            Spacer(Modifier.height(30.dp))



            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            )

            Spacer(Modifier.height(30.dp))

            Text(
                text = stringResource(R.string.home_contato),
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )


            Spacer(Modifier.height(30.dp))

            val context = LocalContext.current


            Button(
                onClick = {

                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:") // Ensures only email apps handle this
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("gtoretti@gmail.com"))
                        putExtra(Intent.EXTRA_SUBJECT, "contato para reservas")
                        putExtra(Intent.EXTRA_TEXT, "Olá gostaria de fazer reserva")
                    }

                    try {
                        context.startActivity(intent)
                    } catch (ex: ActivityNotFoundException) {
                        Toast.makeText(context, "No email client found", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar e-mail")
            }

            Spacer(Modifier.height(30.dp))



            Button(
                onClick = {

                    sendWhatsAppMessage(context,"55 19 992815338", "Olá, gostaria de fazer orçamento.")

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar mensagem por WhatsApp")
            }


        }
    }
}

private fun sendWhatsAppMessage(context: Context, phone: String, message: String) {
    try {
        // Ensure phone number is in international format without '+' or spaces
        val formattedPhone = phone.replace("+", "").replace(" ", "")
        val uri = Uri.parse("https://wa.me/$formattedPhone?text=${Uri.encode(message)}")

        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.whatsapp") // Force open in WhatsApp

        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "O WhatsApp não está instalado.", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}




