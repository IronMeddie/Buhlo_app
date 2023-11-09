package com.ironmeddie.donat.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ironmeddie.donat.models.Category

@Entity
data class Category(
    @ColumnInfo(name = "name")val name: String ="",
    @ColumnInfo(name = "picture")val picture: String = "",
    @ColumnInfo(name = "description")val description: String = "",
    @ColumnInfo(name = "voutes")val voutes: String = "",
    @ColumnInfo(name = "amount")val amount: String = "",
    @PrimaryKey
    val id : String = ""
){
    fun toCategory() = Category(
        name = name,
        picture = picture,
        description = description,
        id = id,
        amount = amount,
        voutes = voutes
    )
}

fun Category.toEntity() : com.ironmeddie.donat.data.database.entity.Category{
    return com.ironmeddie.donat.data.database.entity.Category(
        name = name,
        picture = picture,
        id = id,
        description = description,
        amount = amount,
        voutes = voutes
    )

}

