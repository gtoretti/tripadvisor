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

package com.gtoretti.drego.ui.compras


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource



import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.gtoretti.drego.R
import kotlin.Boolean



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComprasScreen(
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    ComprasHomeScreen(
        isExpandedScreen = isExpandedScreen,
        openDrawer = openDrawer,
        snackbarHostState,
    )
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComprasHomeScreen(
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
                        text = stringResource(R.string.compras_title),
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
        ComprasHomeScreenContent(
            screenModifier,
        )
    }
}



@Composable
private fun ComprasHomeScreenContent(
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







            /////////////////////////////// Botao usado para exibir a localização atual:
            val context = LocalContext.current
            LocationScreen(
                onRequestLocation = { getCurrentLocation(context,it) }
            )
            ///////////////////////////////////////////////////////////////////////////////////













        }
    }
}



// Request location with permission check
@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context, onLocationReceived: (String) -> Unit) {
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        onLocationReceived("Permission not granted")
        return
    }

    var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                onLocationReceived("Lat: ${location.latitude}, Lng: ${location.longitude}")
            } else {
                onLocationReceived("Location unavailable")
            }
        }
        .addOnFailureListener {
            onLocationReceived("Error: ${it.message}")
        }
}


// Request location with permission check
@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context, latAtual: MutableState<Double>, longAtual: MutableState<Double>,  onLocationReceived: (String) -> Unit) {
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        onLocationReceived("Permission not granted")
        return
    }

    var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                latAtual.value = location.latitude
                longAtual.value = location.longitude
                onLocationReceived("Lat: ${location.latitude}, Lng: ${location.longitude}")
            } else {
                onLocationReceived("Location unavailable")
            }
        }
        .addOnFailureListener {
            onLocationReceived("Error: ${it.message}")
        }
}



// Request location with permission check
@SuppressLint("MissingPermission")
fun getCurrentLat(context: Context) : Double{

    var ret: Double = 0.0
    var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                ret= location.latitude
            }
        }
        return ret
}
// Request location with permission check
@SuppressLint("MissingPermission")
fun getCurrentLong(context: Context) : Double{

    var ret: Double = 0.0
    var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                ret= location.longitude
            }
        }
    return ret
}


@Composable
fun LocationScreen(onRequestLocation: ((String) -> Unit) -> Unit) {
    var locationText = remember { mutableStateOf("") }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onRequestLocation { locationText.value = it }
        } else {
            locationText.value = "Permission denied"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(locationText.value)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        ) {
            Text("Ver Localização Atual")
        }
    }
}

fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
    // Validate latitude and longitude ranges
    require(lat1 in -90.0..90.0 && lat2 in -90.0..90.0) { "Latitude must be between -90 and 90" }
    require(lon1 in -180.0..180.0 && lon2 in -180.0..180.0) { "Longitude must be between -180 and 180" }

    val startLocation = Location("start").apply {
        latitude = lat1
        longitude = lon1
    }

    val endLocation = Location("end").apply {
        latitude = lat2
        longitude = lon2
    }

    return startLocation.distanceTo(endLocation) // returns distance in meters
}