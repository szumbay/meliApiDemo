package com.example.meliapisdemo


import android.app.SearchManager
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.meliapisdemo.model.product.Product
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.product_item.*

class MainActivity : AppCompatActivity(), SearchFragment.Comunicator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lastSearch =  MyApplication.prefs.lastSearch()
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.title = lastSearch
        setSupportActionBar(toolbar)
        supportFragmentManager.popBackStack()
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = SearchFragment().apply {
            arguments = Bundle().apply {
                putString("query",lastSearch)
            }
        }
        transaction.add(R.id.content,fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        toolbar.title = MyApplication.prefs.lastSearch()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            if (item.itemId == R.id.search_m) {
                super.onSearchRequested()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun sendProduct(product: Product) {
        toolbar.title = "Producto"
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = DetailFragment().apply {
            arguments = Bundle().apply {
                putString("productId", product.id)
            }
        }
        transaction.replace(R.id.content, fragment)
        transaction.addToBackStack("backFragment")
        transaction.commit()
    }

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                toolbar.title = query
                MyApplication.prefs.lastSearch(query)
                val transaction = supportFragmentManager.beginTransaction()
                val fragment = SearchFragment().apply {
                    arguments = Bundle().apply {
                        putString("query", query)
                    }
                }
                transaction.replace(R.id.content, fragment)
                transaction.addToBackStack("backFragment")
                transaction.commit()
            }
        }
        if(Intent.ACTION_VIEW == intent.action){
            intent.dataString?.also {
                toolbar.title = it
                MyApplication.prefs.lastSearch(it)
                val transaction = supportFragmentManager.beginTransaction()
                val fragment = SearchFragment().apply {
                    arguments = Bundle().apply {
                        putString("query", it)
                    }
                }
                transaction.replace(R.id.content, fragment)
                transaction.addToBackStack("backFragment")
                transaction.commit()
            }
        }
    }


}
