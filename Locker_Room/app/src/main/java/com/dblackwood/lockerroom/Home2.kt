package com.dblackwood.lockerroom

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class Home2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)
        Log.i("CURRENT CLASS: ", localClassName)

        val btnUpdateClothing = findViewById<Button>(R.id.btn_UpdateClothing)
        btnUpdateClothing.setOnClickListener()
        {
            val intent = Intent(this, AddingClothes::class.java)
            intent.putExtra("UpdatingClothing", true)
            startActivity(intent)
        }

        val btnSearchClothing = findViewById<Button>(R.id.btn_SearchClothing)
        btnSearchClothing.setOnClickListener()
        {
            val intent = Intent(this, AddingClothes::class.java)
            intent.putExtra("SearchingClothing", true)
            startActivity(intent)
        }

    }
}
