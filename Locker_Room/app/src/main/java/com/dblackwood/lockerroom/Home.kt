package com.dblackwood.lockerroom

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.dblackwood.lockerroom.DBFiles.AppDataBase

class Home : AppCompatActivity() {

    private var cDb: AppDataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnAddLocker = findViewById<Button>(R.id.btn_addLocker);
        btnAddLocker.setOnClickListener()
        {
            var txt = findViewById<EditText>(R.id.lockerName)
            txt.visibility = View.VISIBLE

            txt.setOnKeyListener() {v, keyCode, event ->
                if (keyCode = )
            }
        }
    }
}
