package com.example.meliapisdemo.model.suggestion

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Suggestion(@SerializedName("q") val query: String) : Serializable