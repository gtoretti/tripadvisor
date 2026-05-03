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

package com.gtoretti.viajasp.ui.home


import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText

import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button


import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

import com.gtoretti.viajasp.R
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.ArrayList

import kotlin.Boolean

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.gtoretti.viajasp.ui.utils.getScrollColor
import com.gtoretti.viajasp.ui.utils.isDarkTheme
import java.io.IOException
import java.math.RoundingMode
import kotlin.toBigDecimal

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

    //exibe menu superior e título da página (Viaja SP!).
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(modifier = Modifier
                        .fillMaxSize()
                        .padding(1.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start){

                        if (isDarkTheme()){
                            Image(
                                painter = painterResource(id = R.drawable.logo_parques_ecologicos),
                                contentDescription = "Viaja SP!",
                                modifier = Modifier.size(width = 100.dp, height = 100.dp)
                            )
                        }else{
                            Image(
                                painter = painterResource(id = R.drawable.logo_parques_ecologicos),
                                contentDescription = "Viaja SP!",
                                modifier = Modifier.size(width = 100.dp, height = 100.dp)
                            )
                        }

                        if (isDarkTheme()){
                            Text(
                                text = "Viaja SP!",
                                modifier = Modifier,
                                style = TextStyle(fontFamily = FontFamily.SansSerif),
                                color = Color(0xFF8BC5B7),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                            )
                        }else{
                            Text(
                                text = "Viaja SP!",
                                modifier = Modifier,
                                style = TextStyle(fontFamily = FontFamily.SansSerif),
                                color = Color(0xFF0020BF),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                            )
                        }


                    }

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
        // exibe conteúdo da página:
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
    val latitudeAtual = remember { mutableDoubleStateOf(0.0) }
    val longitudeAtual = remember { mutableDoubleStateOf(0.0) }
    val locaisJson = remember { mutableStateOf<List<LocalJson>>(ArrayList<LocalJson>()) }

    val context = LocalContext.current

    CarregarPosicaoGPSAtualELocais(context,latitudeAtual,longitudeAtual,locaisJson)

    val locais = calcularDistancias(latitudeAtual.doubleValue,longitudeAtual.doubleValue,locaisJson.value)

    Column(modifier) {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1.0f)
                .padding(horizontal = 1.dp)
        ) {

            // inicio da tela: Cabeçalho
            Spacer(Modifier.height(10.dp))

            Text(
                text = "Pontos turísticos mais próximos da sua localização atual:",
                modifier = Modifier,
                style = TextStyle(fontFamily = FontFamily.SansSerif),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )

            Spacer(Modifier.height(10.dp))
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            )
            //fim cabeçalho.



            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(getScrollColor())
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState()),
            ) {

                //inicio exibir lista de locais:
                Spacer(Modifier.height(30.dp))

                //enquanto estiver carregando posição atual de GPS e buscando lista de locais na nuvem, exibe animação de loading.
                if (locaisJson.value.isEmpty() || (latitudeAtual.value == 0.0 && longitudeAtual.value == 0.0)) {
                    LoadingScreen()
                } else {
                    //se ja tiver carregado posição atual de GPS e lista de locais, exibe cada local da lista, ordenado pela distancia.
                    locais.sortedBy { it.distancia }.forEach { local ->
                        ExibirCadaLocal(context,local)
                    }
                }
                //fim da lista de locais.
            }

        }

    }
}



@Composable
fun ExibirCadaLocal(context: Context, local: Local){

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = local.localJson.nome,
            fontSize = 18.sp,
            //color = Color.Red, // Set text color
            fontWeight = FontWeight.Bold
        )
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (local.localJson.image.isNotEmpty()){

            AsyncImage(
                model = "file:///android_asset/"+local.localJson.image, // Coil can take a File directly
                contentDescription = local.localJson.nome,
                modifier = Modifier
                    .size(250.dp)
                    .fillMaxHeight()
                    .aspectRatio(1f) // Keep square ratio (adjust as needed)
                    .padding(1.dp),
                contentScale = ContentScale.Fit
            )
        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {


            //botao Google Maps
            val context = LocalContext.current
            TextButton(
                modifier = Modifier.padding(5.dp),
                onClick =
                    {
                        abrirGoogleMaps(local.localJson.nome,context)
                    }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.location_on_24px),
                    contentDescription = "Maps",
                    modifier = Modifier
                        .padding(2.dp)
                        .size(20.dp)
                )
            }

            Text(
                modifier = Modifier,
                text = local.distancia.toBigDecimal().setScale(2, RoundingMode.UP).toString().replace(".", ",") + " km",
                fontSize = 15.sp,
            )
        }

    }

    Row(
        modifier = Modifier
            //.fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 1.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = local.localJson.descricao,
            fontSize = 14.sp,
            textAlign = TextAlign.Justify
        )
    }

    //site
    if (local.localJson.site.isNotEmpty()){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            val uriHandler = LocalUriHandler.current
            val annotatedLinkString = buildAnnotatedString {
                pushStringAnnotation(
                    tag = "URL",
                    annotation = local.localJson.site
                )
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 15.sp,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(local.localJson.site)
                }
                pop()
            }

            ClickableText(
                text = annotatedLinkString,
                onClick = { offset ->
                    annotatedLinkString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                        .firstOrNull()?.let { annotation ->
                            uriHandler.openUri(annotation.item) // Opens in browser
                        }
                }
            )
        }
    }

    Spacer(Modifier.height(20.dp))
    HorizontalDivider(
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
    )

}




@Composable
fun DadosParaContato(){
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Text(
            text = stringResource(R.string.home_contato),
            modifier = Modifier,
            style = TextStyle(fontFamily = FontFamily.SansSerif),
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )

        Spacer(Modifier.height(5.dp))

        Button(
            onClick = {
                sendMail(context,"","Contato via app Traveller","")
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 100.dp),


            ) {
            Text("Enviar e-mail")
        }
    }
}

fun sendMail(context: Context,email: String, assunto: String, texto: String){
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, assunto)
        putExtra(Intent.EXTRA_TEXT, texto)
    }

    try {
        context.startActivity(intent)
    } catch (ex: ActivityNotFoundException) {
        Toast.makeText(context, "No email client found", Toast.LENGTH_SHORT).show()
    }
}

@Serializable
data class LocalJson(
    val latitude: Double,
    val longitude: Double,
    val nome: String,
    val image: String,
    val descricao: String,
    val site: String,
)

@Serializable
data class Local(
    val localJson: LocalJson,
    val distancia: Float,
)

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}



suspend fun carregarLocais(context: Context, permissionLauncher:  ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>) : List<LocalJson> {
    var lista = ArrayList<LocalJson>()

    //inicia GPS antes de procurar lista de locais na nuvem.
    permissionLauncher.launch(
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )


    val src = context.assets.open("locais.json")
    val jsonString = src.use { input ->
        input.readBytes()
    }
    val json = Json { ignoreUnknownKeys = true }
    val decoded = json.decodeFromString<List<LocalJson>>(String(jsonString))
    lista = ArrayList<LocalJson>(decoded)




    /*
    //procura lista de locais na nuvem.
    try {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json() // JSON serialization
            }
        }

        //https://jsonsilo.com/ - usuario: gtorettidevimoveis@gmail.com
        //var uri = "https://api.jsonsilo.com/public/3cfa6160-25a4-4466-8e5e-de350ffecf55"

        //opção: https://tiiny.host/manage

        var uri = "https://teste-e77a92.tiiny.site/teste.json"

        val data: String = client.get(uri).body()
        val json = Json { ignoreUnknownKeys = true }
        val decoded = json.decodeFromString<List<LocalJson>>(data)
        lista = ArrayList<LocalJson>(decoded)
    } catch (e: Exception) {
        Log.d("getLocals() load error: ",e.message!!)
    }
    */

    return lista
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CarregarPosicaoGPSAtualELocais(context: Context, latitudeAtual: MutableState<Double>, longitudeAtual: MutableState<Double>, locais: MutableState<List<LocalJson>>){
    val context = LocalContext.current
    var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            getCurrentLocation(fusedLocationClient) { lat, lon ->
                latitudeAtual.value = lat
                longitudeAtual.value = lon
            }
        } else {
            Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    val scope = rememberCoroutineScope()
    scope.launch {
        locais.value = carregarLocais(context,permissionLauncher)
    }
}

fun calcularDistancias(latitudeAtual: Double, longitudeAtual: Double, locaisJson: List<LocalJson>): List<Local> {
    val ret = ArrayList<Local>()

    locaisJson.forEach { localJson->
        val distancia = calculateDistance(localJson.latitude,localJson.longitude,latitudeAtual,longitudeAtual)
        ret.add(Local(localJson,distancia))
    }
    return ret
}


@SuppressLint("MissingPermission") // We check permission before calling
private fun getCurrentLocation(
    fusedLocationClient: FusedLocationProviderClient,
    onLocationReceived: (Double, Double) -> Unit
) {
    fusedLocationClient.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        null
    ).addOnSuccessListener { location ->
        if (location != null) {
            onLocationReceived(location.latitude, location.longitude)
        }
    }
}

fun calculateDistance(
    lat1: Double, lon1: Double,
    lat2: Double, lon2: Double
): Float {
    if (lat1 !in -90.0..90.0 || lat2 !in -90.0..90.0 ||
        lon1 !in -180.0..180.0 || lon2 !in -180.0..180.0
    ) {
        throw IllegalArgumentException("Latitude must be between -90 and 90, longitude between -180 and 180.")
    }

    val startPoint = Location("start").apply {
        latitude = lat1
        longitude = lon1
    }

    val endPoint = Location("end").apply {
        latitude = lat2
        longitude = lon2
    }
    return (startPoint.distanceTo(endPoint))/1000 // retorna distancia em km
}

fun abrirGoogleMaps(address: String, context: Context) {
    val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    context.startActivity(mapIntent)
}