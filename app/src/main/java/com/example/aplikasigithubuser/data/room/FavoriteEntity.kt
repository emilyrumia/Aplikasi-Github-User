package com.example.aplikasigithubuser.data.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite")
@Parcelize
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = false)
    var username: String = "",

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null,
): Parcelable