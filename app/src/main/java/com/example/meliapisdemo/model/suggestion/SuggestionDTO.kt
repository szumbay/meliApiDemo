package com.example.meliapisdemo.model.suggestion

import com.example.meliapisdemo.model.suggestion.Suggestion
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SuggestionDTO(@SerializedName("q") val query: String,
                    @SerializedName("suggested_queries")val suggestions: List<Suggestion>) : Serializable