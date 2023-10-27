package com.ironmeddie.donat.ui.mainScrreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ironmeddie.donat.models.Category
import com.ironmeddie.donat.ui.theme.GreyIconBack
import com.ironmeddie.donat.R

@Composable
fun CategoryRow() {
    val categorys = getCategorys()
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(categorys) {
            CategoryItem(it)
        }
    }
}

@Composable
fun CategoryItem(category: Category) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .padding(horizontal = 8.dp)
        .width(52.dp)
        .clickable { }) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(GreyIconBack), contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = category.picture),
                contentDescription = "category icon"
            )
        }
        Spacer(modifier = Modifier.height(11.dp))
        Text(
            text = category.name,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

fun getCategorys(): List<Category> = listOf(
    Category("Виски", R.drawable.ic_whiskey),
    Category("Пиво",R.drawable.ic_whiskey),
    Category("Вино",R.drawable.ic_whiskey),
    Category("Текила",R.drawable.ic_whiskey),
    Category( "Шампанское",R.drawable.ic_whiskey),
    Category( "Водка",R.drawable.ic_whiskey),
    Category( "Абсент",R.drawable.ic_whiskey),
    Category( "Ягер",R.drawable.ic_whiskey),
    )




@Composable
fun PartHeader(title: String, onClikViewAll : () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 11.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = title, style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)
//        Text(text = stringResource(R.string.view_all), modifier = Modifier.clickable { onClikViewAll() } , style = MaterialTheme.typography.h2, fontSize = 9.sp, color = OnotherOneGrey)
    }
}