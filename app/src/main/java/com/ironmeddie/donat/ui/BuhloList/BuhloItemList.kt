package com.ironmeddie.donat.ui.BuhloList

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ironmeddie.donat.R
import com.ironmeddie.donat.models.ItemBuhlo

@Composable
fun BuhloListItem(item: ItemBuhlo) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 9.dp)
            .fillMaxWidth()
            .height(94.dp)
            .clip(RoundedCornerShape(24.dp))
            .shadow(4.dp)

    ) {
        Image(painter = painterResource(id = item.picture),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(10.dp)))
        Spacer(modifier = Modifier.width(24.dp))
        Column() {
            Text(text = item.name, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = item.description, style = MaterialTheme.typography.bodyMedium)

        }
    }
}

@Composable
@Preview(showBackground = true)
fun previeItem(){
    val item = ItemBuhlo(name = "Вино", picture = R.drawable.ic_vine, description = "для ценителей италии и франции")
    BuhloListItem(item = item)
}
