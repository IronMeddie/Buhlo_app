package com.ironmeddie.donat.ui.mainScrreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ironmeddie.donat.models.Category
import com.ironmeddie.donat.ui.theme.GreyIconBack

@Composable
fun CategoryRow(categories: List<Category>, currentCategory: Category, onCategoryChange: (Category) -> Unit) {

    if (categories.isEmpty()){
        ProgressBar()
    }else{

        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(categories, key = { it.name}) {
                CategoryItem(it, currentCategory.id == it.id){
                    onCategoryChange(it)
                }
            }
        }
    }
}

@Composable
@Preview
fun Test(){
    val list = getCategorys()
    CategoryRow(list, list[1]){

    }
}

@Composable
fun CategoryItem(category: Category, isChoosed: Boolean = false, onClick : () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .padding(horizontal = 2.dp)
        .width(78.dp)
        .height(100.dp)
        .clip(MaterialTheme.shapes.small)
        .background(if (isChoosed)GreyIconBack else MaterialTheme.colorScheme.background)
        .clickable { onClick() }) {
        Box(
            modifier = Modifier
                .padding(top = 11.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(if (!isChoosed)GreyIconBack else MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center
        ) {
//            Icon(
//                painter = painterResource(id = category.picture),
//                contentDescription = "category icon"
//            )
        }
        Spacer(modifier = Modifier.height(11.dp))
        Text(
            text = category.name,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

fun getCategorys(): List<Category> = listOf(
    Category("Виски", "R.drawable.ic_whiskey", id = "0"),
    Category("Пиво","R.drawable.ic_whiskey", id ="1", amount = "200"),
    Category("Вино","R.drawable.ic_whiskey", id = "2"),
    Category("Текила","R.drawable.ic_whiskey", id = "3"),
    Category( "Шампанское","R.drawable.ic_whiskey", id = "4"),
    Category( "Водка","R.drawable.ic_whiskey", id = "5"),
    Category( "Абсент","R.drawable.ic_whiskey", id = "6"),
    Category( "Ягер","R.drawable.ic_whiskey", id = "7"),
    )




@Composable
fun PartHeader(title: String, onClikViewAll : () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 11.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = title, style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)
//        Text(text = stringResource(R.string.view_all), modifier = Modifier.clickable { onClikViewAll() } , style = MaterialTheme.typography.h2, fontSize = 9.sp, color = OnotherOneGrey)
    }
}