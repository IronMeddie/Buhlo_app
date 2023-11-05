package com.ironmeddie.donat.ui.mainScrreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ironmeddie.donat.ui.mainScrreen.components.CategoryRow
import com.ironmeddie.donat.ui.mainScrreen.components.MyTextField
import com.ironmeddie.donat.ui.mainScrreen.components.PartHeader
import com.ironmeddie.donat.ui.mainScrreen.components.SearchPanel
import com.ironmeddie.donat.ui.mainScrreen.components.TransactionItem
import com.ironmeddie.donat.ui.theme.GreyField
import com.ironmeddie.donat.ui.theme.SearchField
import com.ironmeddie.donat.utils.Constance


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(navController: NavController
, context: Context, viewModel: MainScreenViewModel = hiltViewModel()) {

    val categories = viewModel.categories.collectAsState().value
    val transactions = viewModel.transactions.collectAsState().value
    val currentCategory = viewModel.currentcategory.collectAsState().value
    val search = viewModel.search.collectAsState().value
    val summ = viewModel.summ.collectAsState().value
    val currentMoney = viewModel.currentMoney.collectAsState().value
    val refreshing = viewModel.isPullRefreshing.collectAsState().value
    val refreshState = rememberPullRefreshState(refreshing, viewModel::pullRefresh)

    Box(
        Modifier
            .fillMaxSize()
            .pullRefresh(refreshState, true)) {
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
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)) {
                    Spacer(modifier = Modifier.height(96.dp))

                    MyTextField(value = summ, hint = "сумма", onValueChange = viewModel::summ, modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(29.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(GreyField))

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp),onClick = {
                        openIntent(context){
                            openLink(context)
                        }
                        viewModel.updateMoney()
                    }, enabled = summ != "") {
                        Text(text = "Задонатить")
                    }
                }

                }
            
            item {
            Spacer(modifier = Modifier.height(32.dp) )
                Box(modifier = Modifier
                    .padding(64.dp)
                    .fillMaxWidth()
                    .size(150.dp)
                    .shadow(4.dp, CircleShape)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)

                    , contentAlignment = Alignment.Center) {
                    if (currentMoney.isNotBlank()){
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Баланс", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = currentMoney, style = MaterialTheme.typography.bodyLarge)

                        }
        }

                    else CircularProgressIndicator()
            } }

            items(transactions){
                TransactionItem(transaction = it)
            }



        }

    PullRefreshIndicator(refreshing, refreshState, Modifier.align(Alignment.TopCenter))
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