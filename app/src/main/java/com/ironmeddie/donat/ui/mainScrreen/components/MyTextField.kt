package com.ironmeddie.donat.ui.mainScrreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ironmeddie.donat.ui.theme.GreyText

@Composable
fun MyTextField(
    value: String,
    modifier: Modifier = Modifier,
    hint: String,
    onValueChange: (String) -> Unit
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        BasicTextField(
            value = value, onValueChange = {
                onValueChange(it)
            }, modifier = Modifier
                .matchParentSize()
                .padding(horizontal = 16.dp, vertical = 6.dp),
        )
        if (value.isEmpty()) Text(
            text = hint,
            color = GreyText,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}