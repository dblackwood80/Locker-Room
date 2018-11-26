package com.dblackwood.lockerroom

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddClothingInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_clothing_info)

        var price: EditText = findViewById(R.id.editTextPrice)
        var brand: EditText = findViewById(R.id.editTextBrand)
        var qty: EditText = findViewById(R.id.editTextQuantity)
        var clr: EditText = findViewById(R.id.editTextColor)
        var size: EditText = findViewById(R.id.editTextSize)

        Log.i("PRICE CHECK: ", price.text.toString())

        var finishBtn: Button = findViewById(R.id.clothingFinishBtn)

        fun validate(fields: Array<EditText>):Boolean
        {
            for(i in fields.indices){
            val currentField = fields[i];
            if(currentField.text.toString().isEmpty()){
                return false
            }
        }
            return true
        }

        val tw = object: TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (validate(arrayOf(findViewById(R.id.editTextPrice), findViewById(R.id.editTextBrand), findViewById(R.id.editTextQuantity),
                                findViewById(R.id.editTextColor), findViewById(R.id.editTextSize))))
                {
                    finishBtn.visibility = View.VISIBLE
                    Log.i("COMPLETE FIELDS: ", "VALIDATED")
                }
                else
                {
                    finishBtn.visibility = View.INVISIBLE
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        }

        price.addTextChangedListener(tw)
        brand.addTextChangedListener(tw)
        qty.addTextChangedListener(tw)
        clr.addTextChangedListener(tw)
        size.addTextChangedListener(tw)

        finishBtn.setOnClickListener()
        {
            //val intent = Intent(this, AddClothingInfo::class.java)
            //startActivity(intent)
            /*Gather information from edittext fields and call insert based on clothing type selected earlier*/
        }
    }
}
