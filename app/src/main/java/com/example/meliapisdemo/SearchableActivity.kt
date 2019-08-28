package com.example.meliapisdemo

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.meliapisdemo.model.Product
import com.example.meliapisdemo.viewmodel.ProductViewModel

class SearchableActivity : AppCompatActivity() {

    private var productViewModel: ProductViewModel = ProductViewModel()
    private val products: ArrayList<Product> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                var productList = fetchProducts(query)
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra(resources.getString(R.string.PRODUCT_LIST), productList)
                }
                startActivity(intent)
            }
        }

    }
    private fun fetchProducts(query: String) : ArrayList<Product> {
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        productViewModel.getProductReposioryFromSearch(query).observe(this, Observer { productResponse ->
            var productsFromLiveData : List<Product> = productResponse.getProducts()
            products.addAll(productsFromLiveData)
        })
        return products
    }


}
