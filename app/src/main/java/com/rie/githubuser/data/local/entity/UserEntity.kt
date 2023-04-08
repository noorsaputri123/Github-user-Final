package com.rie.githubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
//Noor Saputri
@Entity(tableName = "user")
@Parcelize
data class UserEntity(
    @ColumnInfo(name = "login")
    @PrimaryKey
    val login: String,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "avatar")
    val avatar: String? = null,

    @ColumnInfo(name = "company")
    val company: String? = null,

    @ColumnInfo(name = "followers")
    val followers: Int? = null,

    @ColumnInfo(name = "following")
    val following: Int? = null,

    @ColumnInfo(name = "public_repos")
    val publicRepos: Int? = null,

    @ColumnInfo(name = "location")
    val location: String? = null,

    @ColumnInfo(name = "favorite")
    var isFavorite: Boolean
) : Parcelable