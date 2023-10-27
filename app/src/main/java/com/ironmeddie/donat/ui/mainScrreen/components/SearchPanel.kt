package com.ironmeddie.donat.ui.mainScrreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ironmeddie.donat.R
import com.ironmeddie.donat.ui.theme.GreyTextLoc


@Composable
fun SearchPanel(
    value: String,
    modifier: Modifier = Modifier,
    hint: String,
    onValueChange: (String) -> Unit
) {

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .matchParentSize()
                .padding(horizontal = 16.dp, vertical = 6.dp)
        )
        if (value.isEmpty()) Text(
            text = hint,
            color = GreyTextLoc,
            modifier = Modifier.align(Alignment.Center)
        )
        Icon(
            painter = rememberVectorPainter(image = Icons.Default.Search),
            contentDescription = "search field icon",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 7.dp)
                .padding(8.dp)
        )

    }
}