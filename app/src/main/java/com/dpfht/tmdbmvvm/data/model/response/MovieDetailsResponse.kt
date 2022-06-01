package com.dpfht.tmdbmvvm.data.model.response

import com.dpfht.tmdbmvvm.data.model.Genre
import com.dpfht.tmdbmvvm.data.model.ProductionCompany
import com.dpfht.tmdbmvvm.data.model.ProductionCountry
import com.dpfht.tmdbmvvm.data.model.SpokenLanguage
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class MovieDetailsResponse(

    var adult: Boolean = false,

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null,

    @SerializedName("belongs_to_collection")
    @Expose
    var belongsToCollection: Any? = null,

    var budget: Int = 0,
    var genres: List<Genre>? = null,
    var homepage: String? = null,
    var id: Int = 0,

    @SerializedName("imdb_id")
    @Expose
    var imdbId: String? = null,

    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null,

    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null,

    var overview: String? = null,
    var popularity: Float = 0.0f,

    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null,

    @SerializedName("production_companies")
    @Expose
    var productionCompanies: List<ProductionCompany>? = null,

    @SerializedName("production_countries")
    @Expose
    var productionCountries: List<ProductionCountry>? = null,

    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null,

    var revenue: Int = 0,
    var runtime: Int = 0,

    @SerializedName("spoken_languages")
    @Expose
    var spokenLanguages: List<SpokenLanguage>? = null,

    var status: String? = null,
    var tagline: String? = null,
    var title: String? = null,
    var video: Boolean = false,

    @SerializedName("vote_average")
    @Expose
    var voteAverage: Float = 0.0f,

    @SerializedName("vote_count")
    @Expose
    var voteCount: Int = 0
)
