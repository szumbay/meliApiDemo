package com.example.meliapisdemo


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var supportManager: FragmentManager = supportFragmentManager
        val transaction = supportManager.beginTransaction()
        var list = intent.getSerializableExtra(resources.getString(R.string.PRODUCT_LIST))
        var bundle = Bundle()
        bundle.putSerializable(resources.getString(R.string.PRODUCT_LIST),list)
        var searchFragment = SearchFragment().apply {
            arguments = bundle
        }
        transaction.add(R.id.content,searchFragment)
        transaction.commit()

    }
}
