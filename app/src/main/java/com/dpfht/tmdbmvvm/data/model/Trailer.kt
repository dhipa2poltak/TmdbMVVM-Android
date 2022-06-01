package com.dpfht.tmdbmvvm.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class Trailer(
    var id: String? = null,

    @SerializedName("iso_639_1")
    @Expose
    var iso6391: String? = null,

    @SerializedName("iso_3166_1")
    @Expose
    var iso31661: String? = null,

    var key: String? = null,
    var name: String? = null,
    var site: String? = null,
    var size: Int = 0,
    var type: String? = null
)
