package com.dblackwood.lockerroom

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup

class AddingClothes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_clothes)

        val rg: RadioGroup = findViewById(R.id.ClothingGroup)
        var selected: RadioButton
        var nxtBtn: Button = findViewById(R.id.clothingNxtBtn)

        val intent: Intent = intent
        val deleting: Boolean? = intent.extras?.getBoolean("DeletingCloth")
        val updating: Boolean? = intent.extras?.getBoolean("UpdatingClothing")
        val searching: Boolean? = intent.extras?.getBoolean("SearchingClothing")

        Log.i("WHAT: ", rg.checkedRadioButtonId.toString())

        Log.i("CURRENT CLASS: ", localClassName)

        rg.setOnCheckedChangeListener(object: RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                if (rg.checkedRadioButtonId != -1)
                {
                    val btnId : Int = rg.checkedRadioButtonId
                    selected = findViewById(btnId)
                    selected.setTextColor(Color.BLUE)
                    Log.i("TEST: ", btnId.toString())

                    for (i in 0..8)
                    {
                        var o : View? = rg.getChildAt(i)

                        if (o is RadioButton && o != selected)
                        {
                            o.setTextColor(Color.BLACK)
                        }
                    }

                    nxtBtn.visibility = View.VISIBLE

                    nxtBtn.setOnClickListener()
                    {
                        //val clothingType: String =

                        if (deleting!!)
                        {
                            val intent = Intent(this@AddingClothes, DeleteClothes::class.java)
                            intent.putExtra("ClothingType", selected.text.toString())
                            startActivity(intent)
                            finish()
                        }
                        else
                        {
                            val intent = Intent(this@AddingClothes, ChooseLocker::class.java)
                            intent.putExtra("ClothingType", selected.text.toString())
                            startActivity(intent)
                            finish()
                        }

                        if (updating!!)
                        {
                            val intent = Intent(this@AddingClothes, ChooseLocker::class.java)
                            var extras = Bundle()
                            extras.putBoolean("UpdatingClothing", true)
                            extras.putString("ClothingType", selected.text.toString())
                            intent.putExtras(extras)
                            startActivity(intent)
                            finish()
                        }

                        if (searching!!)
                        {
                            val intent = Intent(this@AddingClothes, ChooseLocker::class.java)
                            var extras = Bundle()
                            extras.putBoolean("SearchingClothing", true)
                            extras.putString("ClothingType", selected.text.toString())
                            intent.putExtras(extras)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        })
    }
}
