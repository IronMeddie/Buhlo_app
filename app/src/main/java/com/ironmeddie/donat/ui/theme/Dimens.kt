package com.ironmeddie.donat.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val SmallPagerIemWidth : Dp = 66.dp,
    val SmallPagerIemHeight : Dp = 38.dp,
    val SmallPagerIemCornerRadius : RoundedCornerShape = RoundedCornerShape(6.dp),
    val SmallPagerIemSpacing : Dp  = 10.dp,

    val MainPagerItemHeight: Dp = 279.dp,
    val MainPagerItemPadding: PaddingValues = PaddingValues(end = 52.dp),
    val MainPagerIemShape: RoundedCornerShape = RoundedCornerShape(9.dp),

    val ButtonsBoxShareAndFavoritePaddings: PaddingValues = PaddingValues(end = 34.dp, top = 156.dp),
    val ButtonsBoxShareAndFavoriteWidth: Dp = 42.dp,
    val ButtonsBoxShareAndFavoritePHeight: Dp = 95.dp,
    val ButtonsBoxShareAndFavoriteShape: RoundedCornerShape = RoundedCornerShape(14.dp),


    val DetailsPlusButtonHeight: Dp = 22.dp,
    val DetailsPlusButtonWidth: Dp = 38.dp,
    val DetailsPlusButtonsSpacer: Dp = 20.dp,
    val DetailsPlusButtonShape: RoundedCornerShape = RoundedCornerShape(8.dp),
    val DetailsPlusButtonsPadding: PaddingValues = PaddingValues(start = 24.dp, top = 38. dp),

    val AddToCartButtonPaddings: PaddingValues = PaddingValues(end = 24.dp, top = 19.dp, bottom = 19.dp),
    val AddToCartButtonWidth: Dp = 170.dp,
    val AddToCartButtonHeight: Dp = 44.dp,

    val DetailsStarSize : Dp = 10.dp,


    val LatestAddButtonSize : Dp = 20.dp,
    val LatestItemWidth : Dp = 114.dp,
    val LatestItemHeight : Dp = 149.dp,
    val SaleAddButtonSize : Dp = 35.dp,
    val SaleFavoriteButtonSize : Dp = 28.dp,
    val SaleItemWidth : Dp = 174.dp,
    val SaleItemHeight : Dp = 221.dp,


    val BottomMenuHeight : Dp = 63.dp
)
val LocalDimens = compositionLocalOf { Dimens() }


val MaterialTheme.dimens: Dimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current