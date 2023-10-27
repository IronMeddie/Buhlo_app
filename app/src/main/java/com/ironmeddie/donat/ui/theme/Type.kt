package com.ironmeddie.donat.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ironmeddie.donat.R

// Set of Material typography styles to start with
val Typography = Typography(

    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.montserat)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.montserat)),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.5.sp
    ),

    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.montserat)),
        fontWeight = FontWeight.Normal,
        fontSize = 8.sp,
        letterSpacing = 0.5.sp
    ),

    headlineLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.montserat)),
        fontWeight = FontWeight.W500,
        fontSize = 15.sp,
        letterSpacing = -(0.3).sp,
        color = AppBlack
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.montserat)),
        fontWeight = FontWeight.W500,
        fontSize = 8.sp,
        letterSpacing = -(0.3).sp,
        color = CategoryText
    ),


    )