package com.example.meliapisdemo.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SuggestionDTO(@SerializedName("q") val query: String,
                    @SerializedName("suggested_queries")val suggestions: List<Suggestion>) : Serializable