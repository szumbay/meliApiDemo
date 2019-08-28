package com.example.meliapisdemo

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.meliapisdemo.model.ProductResponse
import com.example.meliapisdemo.viewmodel.ProductViewModel

class SearchableActivity : AppCompatActivity() {

    private var productViewModel: ProductViewModel = ProductViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                val intent = Intent(this, MainActivity::class.java)
                fetchProducts(query, intent)
                startActivity(intent)
            }
        }

    }

    fun fetchProducts(query: String, intent: Intent){
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel.getProductRepository(query).observe(this, Observer { productResponse ->
            when(productResponse){
                is ProductResponse.Success -> intent.apply { putExtra(resources.getString(R.string.SEARCH_SUCCESS), productResponse) }
                is ProductResponse.Error -> intent.apply { putExtra(resources.getString(R.string.SEARCH_SUCCESS), productResponse) }
            }
        })
    }





}
