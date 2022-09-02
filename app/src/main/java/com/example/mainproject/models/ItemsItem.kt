package com.example.mainproject.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemsItem(
        //private static String repo;
        @SerializedName("forks")
        val forks: String?,

        @SerializedName("added_stars")
        val addedStars: String?,

        @SerializedName("repo_link")
        val repoLink: String?,

        @SerializedName("repo")
        val repo: String?,

        @SerializedName("stars")
        val stars: String?,

        @SerializedName("lang")
        val lang: String?,

        @SerializedName("desc")
        val desc: String?,

        @SerializedName("avatars")
        val avatars: ArrayList<String?>,

        var expanded: Boolean = false
) : Parcelable