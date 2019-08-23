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
        transaction.add(R.id.content,SearchFragment())
        transaction.commit()
    }
}
