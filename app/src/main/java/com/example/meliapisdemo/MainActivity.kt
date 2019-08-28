package com.example.meliapisdemo


import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportFragmentManager.popBackStack()
        var transaction = supportFragmentManager.beginTransaction()
        var fragment = SearchFragment().apply {
            arguments = Bundle().apply {
                putString("query", "iphone")
            }
        }
        transaction.add(R.id.content,fragment)
        transaction.commit()
        handleIntent(intent)
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

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                var transaction = supportFragmentManager.beginTransaction()
                var fragment = SearchFragment().apply {
                    arguments = Bundle().apply {
                        putString("query", query)
                    }
                }
                transaction.add(R.id.content, fragment)
                transaction.commit()
            }
        }
    }
}
