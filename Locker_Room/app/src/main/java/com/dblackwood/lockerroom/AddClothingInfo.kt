package com.dblackwood.lockerroom

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.dblackwood.lockerroom.dbfiles.*

class AddClothingInfo : AppCompatActivity() {

    private var cDb: AppDataBase? = null

    private lateinit var cDbWorkerThread: DbWorkerThread
    private val cUiHandler = Handler()

    //private lateinit var inst: Accessories
    var lockersNameID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_clothing_info)

        cDbWorkerThread = DbWorkerThread("dbWorkerThread")
        cDbWorkerThread.start()

        cDb = AppDataBase.getInstance(this)

        val intent: Intent = intent
        val articleType: String? = intent.extras?.getString("ClothingType")
        val lockersName = intent.extras?.getString("LockerName")


        var price: EditText = findViewById(R.id.editTextPrice)
        var brand: EditText = findViewById(R.id.editTextBrand)
        var qty: EditText = findViewById(R.id.editTextQuantity)
        var descrip: EditText = findViewById(R.id.editTextDescription)
        var clr: EditText = findViewById(R.id.editTextColor)
        var size: EditText = findViewById(R.id.editTextSize)

        Log.i("PRICE CHECK: ", articleType.toString())
        Log.i("PRICE CHECK2: ", lockersName.toString())

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
                    Log.i("COMPLETE FIELDS: ", "TYPE: $s")
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
        descrip.addTextChangedListener(tw)
        clr.addTextChangedListener(tw)
        size.addTextChangedListener(tw)



        finishBtn.setOnClickListener()
        {
            Log.i("PRICE: ", price.text.toString())
            Log.i("BRAND: ", brand.text.toString())
            Log.i("QTY: ", qty.text.toString())
            Log.i("DESCRIP: ", descrip.text.toString())
            Log.i("COLOR: ", clr.text.toString())
            Log.i("SIZE: ", size.text.toString())


            //val intent = Intent(this, AddClothingInfo::class.java)
            //startActivity(intent)
            //TODO: Gather information from edittext fields and call insert based on clothing type selected earlier


            //val userInfo: articleType
            //var z = Class.forName("com.dblackwood.lockerroom.$articleType")
           // var constructor = z.getConstructor(Long::class.java, Int::class.java, Double::class.java, String::class.java, Int::class.java, String::class.java, String::class.java, String::class.java)
            //var inst = constructor.newInstance(null, 1, price, brand, qty, descrip, clr, size)

            val getLock = Runnable { val idNum = cDb?.lockerDAO()?.getID(lockersName!!)
                lockersNameID = idNum!!
            }
            cDbWorkerThread.postTask(getLock)

            Log.i("CHOSEN ID", lockersNameID.toString())

            when (articleType)
            {
                "Accessories" -> {
                    val getSize = Runnable {
                        val fullSize = cDb?.accessoriesDAO()?.getSize()

                        var inst = Accessories(fullSize, 1, price.text.toString().toDouble(), brand.text.toString(), qty.text.toString().toInt(), descrip.text.toString(), clr.text.toString(), size.text.toString())
                        val task = Runnable { cDb?.accessoriesDAO()?.insertAcce(inst) }
                        cDbWorkerThread.postTask(task)
                    }
                    cDbWorkerThread.postTask(getSize)

                    val task2 = Runnable { val ins = cDb?.accessoriesDAO()?.getAll()
                        if (ins != null) {
                            for (a in ins)
                            {
                                Log.i("ACCE CHECK: " , a.acce_id.toString() + ", " + a.lck_id.toString())
                            }
                        }
                    } // TODO: CHECK IF THE AUTOGEN FOR PRIMARYKEY ID IS WORKING!
                    cDbWorkerThread.postTask(task2)
                }

                "Tees/Long Sleeves" -> {
                    val getSize = Runnable {
                        val fullSize = cDb?.teesDAO()?.getSize()
                        var inst = Tees(fullSize, 1, price.text.toString().toDouble(), brand.text.toString(), qty.text.toString().toInt(), descrip.text.toString(), clr.text.toString(), size.text.toString())
                        val task = Runnable { cDb?.teesDAO()?.insertTee(inst) }
                        cDbWorkerThread.postTask(task)
                    }
                    cDbWorkerThread.postTask(getSize)

                    val task2 = Runnable { val ins = cDb?.teesDAO()?.getAll()
                        if (ins != null) {
                            for (a in ins)
                            {
                                Log.i("Tees CHECK: " , a.tee_id.toString() + ", " + a.lck_id.toString())
                            }
                        }
                    } // TODO: CHECK IF THE AUTOGEN FOR PRIMARYKEY ID IS WORKING!
                    cDbWorkerThread.postTask(task2)
                }

                "Outerwear" -> {
                    val getSize = Runnable {
                        val fullSize = cDb?.outerWearDAO()?.getSize()
                        var inst = OuterWear(fullSize, 1, price.text.toString().toDouble(), brand.text.toString(), qty.text.toString().toInt(), descrip.text.toString(), clr.text.toString(), size.text.toString())
                        val task = Runnable { cDb?.outerWearDAO()?.insertOuter(inst) }
                        cDbWorkerThread.postTask(task)
                    }
                    cDbWorkerThread.postTask(getSize)

                    val task2 = Runnable { val ins = cDb?.outerWearDAO()?.getAll()
                        if (ins != null) {
                            for (a in ins)
                            {
                                Log.i("Outerwear CHECK: " , a.outer_id.toString() + ", " + a.lck_id.toString())
                            }
                        }
                    } // TODO: CHECK IF THE AUTOGEN FOR PRIMARYKEY ID IS WORKING!
                    cDbWorkerThread.postTask(task2)
                }

                "Shoes" -> {
                    val getSize = Runnable {
                        val fullSize = cDb?.shoesDAO()?.getSize()
                        var inst = Shoes(fullSize, 1, price.text.toString().toDouble(), brand.text.toString(), qty.text.toString().toInt(), descrip.text.toString(), clr.text.toString(), size.text.toString())
                        val task = Runnable { cDb?.shoesDAO()?.insertShoe(inst) }
                        cDbWorkerThread.postTask(task)
                    }
                    cDbWorkerThread.postTask(getSize)

                    val task2 = Runnable { val ins = cDb?.shoesDAO()?.getAll()
                        Log.i("NOT RUN", "HERE")
                        if (ins != null) {
                            for (a in ins)
                            {
                                Log.i("Shoes CHECK: " , a.shoe_id.toString() + ", " + a.lck_id.toString())
                            }
                        }
                        else
                        {
                            Log.i("IS NULL", "WHY")
                        }
                    } // TODO: CHECK IF THE AUTOGEN FOR PRIMARYKEY ID IS WORKING!
                    cDbWorkerThread.postTask(task2)

                    Log.i("FINISHED", "SHOES")
                }

                "Socks" -> {
                    val getSize = Runnable {
                        val fullSize = cDb?.socksDAO()?.getSize()

                        if (fullSize != null)
                        {
                            Log.i("SOCKS SIZE: ", fullSize.toString())
                        }
                        else
                        {
                            Log.i("SOCKS SIZE: ", "IS NULL")
                        }


                        var inst = Socks(fullSize, 1, price.text.toString().toDouble(), brand.text.toString(), qty.text.toString().toInt(), descrip.text.toString(), clr.text.toString(), size.text.toString())
                        val task = Runnable { cDb?.socksDAO()?.insertSock(inst) }
                        cDbWorkerThread.postTask(task)
                    }
                    cDbWorkerThread.postTask(getSize)

                    val task2 = Runnable { val ins = cDb?.socksDAO()?.getAll()
                        if (ins != null) {
                            for (a in ins)
                            {
                                Log.i("Socks CHECK: " , a.sock_id.toString() + ", " + a.lck_id.toString() + ", " + a.brnd)
                            }
                        }
                    } // TODO: CHECK IF THE AUTOGEN FOR PRIMARYKEY ID IS WORKING!
                    cDbWorkerThread.postTask(task2)
                }

                "Pants/Shorts" -> {
                    val getSize = Runnable {
                        val fullSize = cDb?.pantsDAO()?.getSize()


                        if (fullSize != null)
                        {
                            Log.i("Pants SIZE: ", fullSize.toString())
                        }
                        else
                        {
                            Log.i("Pants SIZE: ", "IS NULL")
                        }


                        var inst = Pants(fullSize, 1, price.text.toString().toDouble(), brand.text.toString(), qty.text.toString().toInt(), descrip.text.toString(), clr.text.toString(), size.text.toString())
                        val task = Runnable { cDb?.pantsDAO()?.insertPant(inst) }
                        cDbWorkerThread.postTask(task)
                    }
                    cDbWorkerThread.postTask(getSize)

                    val task2 = Runnable { val ins = cDb?.pantsDAO()?.getAll()
                        if (ins != null) {
                            for (a in ins)
                            {
                                Log.i("Pants CHECK: " , a.pant_id.toString() + ", " + a.lck_id.toString() + ", " + a.brnd)
                            }
                        }
                        else
                        {
                            Log.i("PANTS ", "IS NULL")
                        }
                    } // TODO: CHECK IF THE AUTOGEN FOR PRIMARYKEY ID IS WORKING!
                    cDbWorkerThread.postTask(task2)
                }

                "Dresses" -> {
                    val getSize = Runnable {
                        val fullSize = cDb?.dressesDAO()?.getSize()
                        var inst = Dresses(fullSize, 1, price.text.toString().toDouble(), brand.text.toString(), qty.text.toString().toInt(), descrip.text.toString(), clr.text.toString(), size.text.toString())
                        val task = Runnable { cDb?.dressesDAO()?.insertDress(inst) }
                        cDbWorkerThread.postTask(task)
                    }
                    cDbWorkerThread.postTask(getSize)

                    val task2 = Runnable { val ins = cDb?.dressesDAO()?.getAll()
                        if (ins != null) {
                            for (a in ins)
                            {
                                Log.i("Dresses CHECK: " , a.dress_id.toString() + ", " + a.lck_id.toString())
                            }
                        }
                    } // TODO: CHECK IF THE AUTOGEN FOR PRIMARYKEY ID IS WORKING!
                    cDbWorkerThread.postTask(task2)
                }

                "Other" -> {
                    val getSize = Runnable {
                        val fullSize = cDb?.otherDAO()?.getSize()
                        var inst = Other(fullSize, 1, price.text.toString().toDouble(), brand.text.toString(), qty.text.toString().toInt(), descrip.text.toString(), clr.text.toString(), size.text.toString())
                        val task = Runnable { cDb?.otherDAO()?.insertOther(inst) }
                        cDbWorkerThread.postTask(task)
                    }
                    cDbWorkerThread.postTask(getSize)

                    val task2 = Runnable { val ins = cDb?.otherDAO()?.getAll()
                        if (ins != null) {
                            for (a in ins)
                            {
                                Log.i("Other CHECK: " , a.oth_id.toString() + ", " + a.lck_id.toString())
                            }
                        }
                    } // TODO: CHECK IF THE AUTOGEN FOR PRIMARYKEY ID IS WORKING!
                    cDbWorkerThread.postTask(task2)
                }
            }
            finish()
        }
    }
}