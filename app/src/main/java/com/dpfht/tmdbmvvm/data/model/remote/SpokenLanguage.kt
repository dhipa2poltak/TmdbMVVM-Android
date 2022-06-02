package com.dpfht.tmdbmvvm.data.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class SpokenLanguage(
    @SerializedName("iso_639_1")
    @Expose
    var iso6391: String? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("english_name")
    @Expose
    var englishName: String? = null
)
