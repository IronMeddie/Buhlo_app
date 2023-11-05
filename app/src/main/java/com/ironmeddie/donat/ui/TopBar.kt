package com.ironmeddie.donat.ui

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ironmeddie.donat.R
import com.ironmeddie.donat.ui.theme.Border

@Composable
fun TopBar(uri : String, onTap : () -> Unit){
    Box(modifier = Modifier.fillMaxWidth().height(55.dp).background(MaterialTheme.colorScheme.primary)) {
        AsyncImage(
            model = if (uri.isBlank()) {
                R.drawable.avatar
            } else uri, contentDescription = "user avatar",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(CircleShape)
                .size(45.dp)
                .border(1.dp, Border, CircleShape)
                .align(Alignment.CenterEnd)
                .clickable {
                    onTap()
                }, contentScale = ContentScale.Crop
        )

    }
}