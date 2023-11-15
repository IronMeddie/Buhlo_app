package com.ironmeddie.donat.ui.mainScrreen.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ironmeddie.donat.models.Category

@Composable
fun CategoryStatistic(categories: List<Category>) {


    if (categories.isNotEmpty()) {
        val list = categories.sortedByDescending { it.amount }
        LaunchedEffect(key1 = true) {
            Log.d("checkCode first categori amount", list[0].amount.toString())
            Log.d("checkCode first categori name", list[0].name)
            Log.d("checkCode first categori id", list[0].id)
        }
        val main = if (list[0].amount > 0) list[0].amount else 1f
        LazyRow() {
            items(list) {
                StatisticItem(category = it, first = main)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    CategoryStatistic(getCategorys())
}

@Composable
fun StatisticItem(category: Category, first: Float) {

    val cat = category.amount
    val height = cat / first

    Column(modifier = Modifier.padding(6.dp), horizontalAlignment = Alignment.CenterHorizontally) {

        Box(
            modifier = Modifier

                .width(56.dp)
                .height(150.dp)
                .shadow(4.dp, MaterialTheme.shapes.medium)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .align(Alignment.BottomCenter)
                    .fillMaxHeight(height)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = category.amount.toString(),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 7.dp),
                    color = MaterialTheme.colorScheme.background,
                )
            }


        }
        Text(
            text = category.name,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onBackground
        )


    }

}