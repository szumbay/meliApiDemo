package com.example.meliapisdemo.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.app.SearchManager
import android.database.MatrixCursor
import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.example.meliapisdemo.model.Product
import com.example.meliapisdemo.model.Suggestion
import com.example.meliapisdemo.model.SuggestionDTO
import com.example.meliapisdemo.networking.ProductRepository
import com.example.meliapisdemo.networking.SuggestionRepository


class ProductSearchProvider : ContentProvider() {

    private val STORES = "stores/" + SearchManager.SUGGEST_URI_PATH_QUERY + "/*"

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.example.meliapisdemo.provider", STORES, 2
        )
    }

    private val matrixCursorColumns = arrayOf(
        "_id",
        SearchManager.SUGGEST_COLUMN_TEXT_1,
        SearchManager.SUGGEST_COLUMN_INTENT_DATA
    )

    override fun insert(uri: Uri, p1: ContentValues?): Uri? {
        return null
    }

    override fun query(uri: Uri, p1: Array<String>?, p2: String?, p3: Array<String>?, p4: String?): Cursor? {

        if (uriMatcher.match(uri) == 2){
            val query = uri.lastPathSegment?.toLowerCase()
            val cursor = query?.let { getSearchSuggestionResultCursor(it) }
            return cursor
        } else return null
    }

    private fun getSearchSuggestionResultCursor(searchSuggestion: String) : MatrixCursor {
        var suggestionLiveData = MutableLiveData<SuggestionDTO>()
        SuggestionRepository.getSuggestion(searchSuggestion, suggestionLiveData)
        val matrixCursor = MatrixCursor(matrixCursorColumns)
        val mRow = arrayOfNulls<Any>(3)
        var counterId = 0
        searchSuggestion.toLowerCase()

        Thread.sleep(300)

        suggestionLiveData.value?.suggestions?.apply {
            for (sugg in this){
                mRow[0] = "" + counterId++
                mRow[1] = sugg.query
                mRow[2] = sugg.query
                matrixCursor.addRow(mRow)
            }
        }

        return matrixCursor
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun update(uri: Uri, p1: ContentValues?, p2: String?, p3: Array<String>?): Int {
        return 0
    }

    override fun delete(uri: Uri, p1: String?, p2: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}