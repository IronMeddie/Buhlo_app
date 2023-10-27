package com.ironmeddie.donat.ui.mainScrreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ironmeddie.donat.ui.mainScrreen.components.CategoryRow
import com.ironmeddie.donat.ui.mainScrreen.components.PartHeader
import com.ironmeddie.donat.ui.mainScrreen.components.SearchPanel
import com.ironmeddie.donat.ui.theme.SearchField


@Composable
fun MainScreen(navController: NavController
) {

    val search = remember{ mutableStateOf("") }
    Box() {
        LazyColumn() {

            item {
                SearchPanel(
                    value = search.value,
                    modifier = Modifier
                        .padding(vertical = 9.dp, horizontal = 26.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(SearchField)
                        .fillMaxWidth(),
                    hint = "поиск",
                    onValueChange = {search.value = it},

                    )


            }
            item {
                Spacer(modifier = Modifier.height(12.dp) )
                PartHeader("Категории"){

                }
            }
            item { Spacer(modifier = Modifier.height(22.dp)) }
            item { CategoryRow() }
            item { Spacer(modifier = Modifier.height(22.dp)) }


        }

    }

}