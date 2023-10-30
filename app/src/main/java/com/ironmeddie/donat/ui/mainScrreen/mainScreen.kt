package com.ironmeddie.donat.ui.mainScrreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ironmeddie.donat.ui.mainScrreen.components.CategoryRow
import com.ironmeddie.donat.ui.mainScrreen.components.PartHeader
import com.ironmeddie.donat.ui.mainScrreen.components.SearchPanel
import com.ironmeddie.donat.ui.theme.SearchField
import com.ironmeddie.donat.utils.Constance


@Composable
fun MainScreen(navController: NavController
, context: Context, viewModel: MainScreenViewModel = viewModel()) {

    val categories = viewModel.categories.collectAsState().value
    val currentCategory = viewModel.currentcategory.collectAsState().value
    val search = viewModel.search.collectAsState().value


    Box() {
        LazyColumn() {

            item {
                SearchPanel(
                    value = search,
                    modifier = Modifier
                        .padding(vertical = 9.dp, horizontal = 26.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(SearchField)
                        .fillMaxWidth(),
                    hint = "поиск",
                    onValueChange = {viewModel.search(it)}
                    )


            }
            item {
                Spacer(modifier = Modifier.height(12.dp) )
                PartHeader("Категории"){

                }
            }
            item { Spacer(modifier = Modifier.height(22.dp)) }
            item { CategoryRow(categories, currentCategory){
                viewModel.changeCategory(it)
            } }
            item { Spacer(modifier = Modifier.height(56.dp)) }
            item { Button(modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .height(36.dp),onClick = {
                openIntent(context){
                    openLink(context)
                }
            }) {
                Text(text = "Задонатить")
            } }


        }

    }

}

fun openIntent(context: Context, other: () -> Unit){
    try {
        val uri = "intent://ru.sberbankmobile/payments/p2p?source=QR_FROM_SELF_EMPLOYED_EXTERNAL&type=phone_number&requisiteNumber=79254582814&external_source=pbpn-_--_--_--_--_--_-_y_1698677837122447754_d_17334569-840c-4138-93a9-1bad10675fe9_g_0.0"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(uri)
        startActivity(context, intent, null)
    }catch (e: Throwable){
        Log.d(Constance.TAG, e.message.toString())
        other()
    }

}

fun openLink(context: Context){
    try {
    val url = "https://www.sberbank.com/sms/pbpn?requisiteNumber=79254582814"
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    startActivity(context, intent, null)
    }catch (e: Throwable){
        Log.d(Constance.TAG, e.message.toString())
    }

}

//      intent://ru.sberbankmobile/payments/p2p?source=QR_FROM_SELF_EMPLOYED_EXTERNAL&type=phone_number&requisiteNumber=79254582814&external_source=pbpn-_--_--_--_--_--_-_y_1698677837122447754_d_17334569-840c-4138-93a9-1bad10675fe9_g_0.0