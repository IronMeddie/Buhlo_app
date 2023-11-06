package com.ironmeddie.donat.ui.mainScrreen.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.ironmeddie.donat.models.Transaction

@Composable
fun TransactionItem(transaction: Transaction){
    Box(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
        .size(120.dp)
        .shadow(4.dp, MaterialTheme.shapes.medium)
        .clip(MaterialTheme.shapes.medium)
        .background(MaterialTheme.colorScheme.background)) {
        Column {
            Row(modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = transaction.userName, style = MaterialTheme.typography.titleLarge)

                Column {

                    Text(text = transaction.dateTime, style = MaterialTheme.typography.bodyMedium)
                    transaction.categories.forEach {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = it, style = MaterialTheme.typography.bodyMedium)

                    }
                }


            }

        }
        Box(modifier = Modifier.padding(16.dp).align(Alignment.BottomStart)) {
            Text(text = transaction.money, style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(
                Alignment.Center))
        }
       

    }
}