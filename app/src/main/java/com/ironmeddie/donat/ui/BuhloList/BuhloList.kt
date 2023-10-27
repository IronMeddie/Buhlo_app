package com.ironmeddie.donat.ui.BuhloList

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.ironmeddie.donat.R
import com.ironmeddie.donat.models.Category
@Composable
fun BuhloList(list: List<Category> = createlistOfBuhlo()){
    LazyColumn(modifier = Modifier.fillMaxSize()){
         items(list){
             BuhloListItem(item = it)

         }
    }
}

fun createlistOfBuhlo(): List<Category>{
    return listOf(
        Category(name = "Вино", picture = R.drawable.ic_vine, description = "для ценителей италии и франции"),
        Category(name = "Пиво", picture = R.drawable.ic_beer, description = "для ценителей темного и светлого, а также нефильтрованного"),
        Category(name = "Виски", picture = R.drawable.ic_whiskey, description = "ну тут не помню, кажется любят пшеницу, а если бурбон, то кукурузу"),
    )

}

