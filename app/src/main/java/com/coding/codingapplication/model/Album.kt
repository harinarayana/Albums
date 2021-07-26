package com.coding.codingapplication.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Album(
    @SerializedName("userId")
    val userId : Int,
    @PrimaryKey
    @SerializedName("id")
    val album_id: Int,
    @SerializedName("title")
    val title : String
): Parcelable
