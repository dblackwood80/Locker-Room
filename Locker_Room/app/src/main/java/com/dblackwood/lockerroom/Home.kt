package com.dblackwood.lockerroom

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.dblackwood.lockerroom.dbfiles.AppDataBase

class Home : AppCompatActivity() {

    private var cDb: AppDataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnAddLocker = findViewById<Button>(R.id.btn_addLocker)
        btnAddLocker.setOnClickListener()
        {
            val intent = Intent(this, LockerPage::class.java)
            startActivity(intent)
        }

        val btnAddClothes = findViewById<Button>(R.id.btn_addClothes)
        btnAddClothes.setOnClickListener()
        {
            val intent = Intent(this, AddingClothes::class.java)
            startActivity(intent)
        }
    }
}