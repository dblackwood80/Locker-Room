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
            val intent = Intent(this, AddLockerPage::class.java)
            startActivity(intent)
        }

        val btnDeleteLocker = findViewById<Button>(R.id.btn_removeLocker)
        btnDeleteLocker.setOnClickListener()
        {
            val intent = Intent(this, DeleteLockerPage::class.java)
            startActivity(intent)
        }

        val btnAddClothes = findViewById<Button>(R.id.btn_addClothes)
        btnAddClothes.setOnClickListener()
        {
            val intent = Intent(this, AddingClothes::class.java)
            intent.putExtra("DeletingCloth", false)
            startActivity(intent)
        }

        val btnDeleteClothes = findViewById<Button>(R.id.btn_removeClothes)
        btnDeleteClothes.setOnClickListener()
        {
            val intent = Intent(this, AddingClothes::class.java)
            intent.putExtra("DeletingCloth", true)
            startActivity(intent)
        }

        val btnMoreFunctions = findViewById<Button>(R.id.btn_More)
        btnMoreFunctions.setOnClickListener()
        {
            val intent = Intent(this, Home2::class.java)
            startActivity(intent)
        }
    }
}