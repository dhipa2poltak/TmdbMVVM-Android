package com.dpfht.tmdbmvvm.data.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class ProductionCompany(
    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("origin_country")
    @Expose
    var originCountry: String? = null,

    @SerializedName("logo_path")
    @Expose
    var logoPath: String? = null,

    @SerializedName("id")
    @Expose
    var id: Int = 0,
)
