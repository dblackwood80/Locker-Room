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
        val lockersID = intent.extras?.getInt("PassID")
        val lockersName = intent.extras?.getString("LockerName")

        val lockersClothingID = intent.extras?.getLong("ClothingID")
        val lockersPrice = intent.extras?.getDouble("ClothingPrice")
        val lockersBrand = intent.extras?.getString("ClothingBrand")
        val lockersQty = intent.extras?.getInt("ClothingQty")
        val lockersDescrip = intent.extras?.getString("ClothingDescrip")
        val lockersClr = intent.extras?.getString("ClothingClr")
        val lockersSize = intent.extras?.getString("ClothingSize")

        val updating: Boolean? = intent.extras?.getBoolean("UpdatingClothing")
        val searching: Boolean? = intent.extras?.getBoolean("SearchingClothing")

        val priEditText: EditText = EditText(baseContext)
        priEditText.setText(lockersPrice.toString())

        //var ogField = arrayOf<EditText>(lockersPrice, lockersBrand, lockersQty, lockersDescrip, lockersClr, lockersSize)


        var price: EditText = findViewById(R.id.editTextPrice)
        var brand: EditText = findViewById(R.id.editTextBrand)
        var qty: EditText = findViewById(R.id.editTextQuantity)
        var descrip: EditText = findViewById(R.id.editTextDescription)
        var clr: EditText = findViewById(R.id.editTextColor)
        var size: EditText = findViewById(R.id.editTextSize)

        var fields = arrayOf(price, brand, qty, descrip, clr, size)

        fields[0] = priEditText

        if (updating!!)
        {
            price.setText(lockersPrice.toString())
            brand.setText(lockersBrand.toString())
            qty.setText(lockersQty.toString())
            descrip.setText(lockersDescrip.toString())
            clr.setText(lockersClr.toString())
            size.setText(lockersSize.toString())
        }

        //price.text?. = lockersPrice

        Log.i("PRICE CHECK: ", articleType.toString())
        Log.i("PRICE CHECK2: ", lockersName.toString())

        var finishBtn: Button = findViewById(R.id.clothingFinishBtn)

        var curr = arrayOf(fields)

        var que = ""
        var fieldNum = 0

        fun validate(fields: Array<EditText>):Boolean
        {
            var count = 0
            if (searching!!)
            {
                for(i in fields.indices)
                {
                    val currentField = fields[i]
                    if(currentField.text.toString().isNotEmpty())
                    {
                        count++

                        if (count == 1)
                        {
                            que = currentField.text.toString()
                            fieldNum = i
                            //return true
                        }
                        else if (count > 1)
                        {
                            Log.i("ONETWO", "HERE")
                            Toast.makeText(baseContext, "YOU CAN SEARCH WITH ONLY ONE ATTRIBUTE", Toast.LENGTH_LONG).show()
                            return false
                        }
                    }
                }

                if (count == 1)
                {
                    return true
                }

                /*if (count == 1)
                {
                    if

                    return true
                }
                else if (count > 1)
                {
                    Log.i("ONETWO", "HERE")
                    Toast.makeText(baseContext, "YOU CAN SEARCH WITH ONLY ONE ATTRIBUTE", Toast.LENGTH_LONG).show()
                    return false
                }*/
            }


            for(i in fields.indices)
            {
                val currentField = fields[i]
                    if(currentField.text.toString().isEmpty())
                    {
                        return false
                    }
            }
            return true
        }

        val tw = object: TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                Log.i("HERE", "OOOO")
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
                "Accessories" -> run{

                    if (searching!!)
                    {
                        var datasetStrings = arrayListOf<String>()
                        Log.i(que, ", " + fieldNum.toString())
                        val task2 = Runnable { val ins = cDb?.accessoriesDAO()?.getAll()
                            if (ins != null && searching!!) {
                                for (a in ins)
                                {
                                  when (fieldNum)
                                  {
                                      0 -> {
                                                if (a.prc == que.toDouble())
                                                {
                                                    datasetStrings.add("$" + a.prc.toString() + ", " + a.brnd.toString() + ", " + a.qty.toString() + ", " + a.clr.toString() + ", " + a.sz)
                                                    Toast.makeText(baseContext, "HOORAY", Toast.LENGTH_SHORT).show()
                                                    val intent = Intent(baseContext, SearchClothing::class.java)
                                                    var extras = Bundle()
                                                    extras.putBoolean("SearchingClothing", true)
                                                    extras.putString("ClothingType", articleType)
                                                    extras.putStringArrayList("MatchingValues", datasetStrings)
                                                    extras.putString("LockerName", lockersName)
                                                    extras.putLong("PassID", lockersNameID.toLong())
                                                    intent.putExtras(extras)
                                                    startActivity(intent)
                                                    finish()
                                                }
                                           }

                                      1 -> {
                                          if (a.brnd == que)
                                          {
                                              datasetStrings.add("$" + a.prc.toString() + ", " + a.brnd.toString() + ", " + a.qty.toString() + ", " + a.clr.toString() + ", " + a.sz)
                                              Toast.makeText(baseContext, "HOORAY", Toast.LENGTH_SHORT).show()
                                              val intent = Intent(baseContext, SearchClothing::class.java)
                                              var extras = Bundle()
                                              extras.putBoolean("SearchingClothing", true)
                                              extras.putString("ClothingType", articleType)
                                              extras.putStringArrayList("MatchingValues", datasetStrings)
                                              extras.putString("LockerName", lockersName)
                                              extras.putLong("PassID", lockersNameID.toLong())
                                              intent.putExtras(extras)
                                              startActivity(intent)
                                              finish()
                                          }
                                      }

                                      2 -> {
                                          if (a.qty == que.toInt())
                                          {
                                              datasetStrings.add("$" + a.prc.toString() + ", " + a.brnd.toString() + ", " + a.qty.toString() + ", " + a.clr.toString() + ", " + a.sz)
                                              Toast.makeText(baseContext, "HOORAY", Toast.LENGTH_SHORT).show()
                                              val intent = Intent(baseContext, SearchClothing::class.java)
                                              var extras = Bundle()
                                              extras.putBoolean("SearchingClothing", true)
                                              extras.putString("ClothingType", articleType)
                                              extras.putStringArrayList("MatchingValues", datasetStrings)
                                              extras.putString("LockerName", lockersName)
                                              extras.putLong("PassID", lockersNameID.toLong())
                                              intent.putExtras(extras)
                                              startActivity(intent)
                                              finish()
                                          }
                                      }

                                      3 -> {
                                          if (a.clr == que)
                                          {
                                              datasetStrings.add("$" + a.prc.toString() + ", " + a.brnd.toString() + ", " + a.qty.toString() + ", " + a.clr.toString() + ", " + a.sz)
                                              Toast.makeText(baseContext, "HOORAY", Toast.LENGTH_SHORT).show()
                                              val intent = Intent(baseContext, SearchClothing::class.java)
                                              var extras = Bundle()
                                              extras.putBoolean("SearchingClothing", true)
                                              extras.putString("ClothingType", articleType)
                                              extras.putStringArrayList("MatchingValues", datasetStrings)
                                              extras.putString("LockerName", lockersName)
                                              extras.putLong("PassID", lockersNameID.toLong())
                                              intent.putExtras(extras)
                                              startActivity(intent)
                                              finish()
                                          }
                                      }

                                      4 -> {
                                          if (a.sz == que)
                                          {
                                              datasetStrings.add("$" + a.prc.toString() + ", " + a.brnd.toString() + ", " + a.qty.toString() + ", " + a.clr.toString() + ", " + a.sz)
                                              Toast.makeText(baseContext, "HOORAY", Toast.LENGTH_SHORT).show()
                                              val intent = Intent(baseContext, SearchClothing::class.java)
                                              var extras = Bundle()
                                              extras.putBoolean("SearchingClothing", true)
                                              extras.putString("ClothingType", articleType)
                                              extras.putStringArrayList("MatchingValues", datasetStrings)
                                              extras.putString("LockerName", lockersName)
                                              extras.putLong("PassID", lockersNameID.toLong())
                                              intent.putExtras(extras)
                                              startActivity(intent)
                                              finish()
                                          }
                                      }
                                  }
                                }
                            }
                        }
                        cDbWorkerThread.postTask(task2)
                    }

                    if (updating!!)
                    {
                        val del = Accessories(lockersClothingID!!, lockersID!!, lockersPrice!!, lockersBrand!!, lockersQty!!, lockersDescrip, lockersClr!!, lockersSize!!)

                        val removeOld = Runnable { val idNum = cDb?.accessoriesDAO()?.deleteAcce(del) }
                        cDbWorkerThread.postTask(removeOld)
                    }

                    if (!searching!!) {
                        val getSize = Runnable {
                            val fullSize = cDb?.accessoriesDAO()?.getSize()

                            var inst = Accessories(0, 1, price.text.toString().toDouble(), brand.text.toString(), Integer.parseInt(qty.text.toString()), descrip.text.toString(), clr.text.toString(), size.text.toString())
                            val task = Runnable { cDb?.accessoriesDAO()?.insertAcce(inst) }
                            cDbWorkerThread.postTask(task)

                        }
                        cDbWorkerThread.postTask(getSize)
                    }


                    val task2 = Runnable { val ins = cDb?.accessoriesDAO()?.getAll()
                        if (ins != null && searching!!) {
                            for (a in ins)
                            {
                                //if (a.prc == price.text)
                                //{

                                //}

                                Log.i("ACCE CHECK: " , a.acce_id.toString() + ", " + a.lck_id.toString())
                            }
                        }
                    } // TODO: CHECK IF THE AUTOGEN FOR PRIMARYKEY ID IS WORKING!
                    cDbWorkerThread.postTask(task2)
                }

                "Tees/Long Sleeves" -> {
                    if (searching!!)
                    {
                        var datasetStrings = arrayListOf<String>()
                        Log.i(que, ", " + fieldNum.toString())
                        val task2 = Runnable { val ins = cDb?.teesDAO()?.getAll()
                            if (ins != null && searching!!) {
                                for (a in ins)
                                {
                                    when (fieldNum)
                                    {
                                        0 -> {
                                            if (a.prc == que.toDouble())
                                            {
                                                datasetStrings.add("$" + a.prc.toString() + ", " + a.brnd.toString() + ", " + a.qty.toString() + ", " + a.clr.toString() + ", " + a.sz)
                                                Toast.makeText(baseContext, "HOORAY", Toast.LENGTH_SHORT).show()
                                                val intent = Intent(baseContext, SearchClothing::class.java)
                                                var extras = Bundle()
                                                extras.putBoolean("SearchingClothing", true)
                                                extras.putString("ClothingType", articleType)
                                                extras.putStringArrayList("MatchingValues", datasetStrings)
                                                extras.putString("LockerName", lockersName)
                                                extras.putLong("PassID", lockersNameID.toLong())
                                                intent.putExtras(extras)
                                                startActivity(intent)
                                                finish()
                                            }
                                        }

                                        1 -> {
                                            if (a.brnd == que)
                                            {
                                                datasetStrings.add("$" + a.prc.toString() + ", " + a.brnd.toString() + ", " + a.qty.toString() + ", " + a.clr.toString() + ", " + a.sz)
                                                Toast.makeText(baseContext, "HOORAY", Toast.LENGTH_SHORT).show()
                                                val intent = Intent(baseContext, SearchClothing::class.java)
                                                var extras = Bundle()
                                                extras.putBoolean("SearchingClothing", true)
                                                extras.putString("ClothingType", articleType)
                                                extras.putStringArrayList("MatchingValues", datasetStrings)
                                                extras.putString("LockerName", lockersName)
                                                extras.putLong("PassID", lockersNameID.toLong())
                                                intent.putExtras(extras)
                                                startActivity(intent)
                                                finish()
                                            }
                                        }

                                        2 -> {
                                            if (a.qty == que.toInt())
                                            {
                                                datasetStrings.add("$" + a.prc.toString() + ", " + a.brnd.toString() + ", " + a.qty.toString() + ", " + a.clr.toString() + ", " + a.sz)
                                                Toast.makeText(baseContext, "HOORAY", Toast.LENGTH_SHORT).show()
                                                val intent = Intent(baseContext, SearchClothing::class.java)
                                                var extras = Bundle()
                                                extras.putBoolean("SearchingClothing", true)
                                                extras.putString("ClothingType", articleType)
                                                extras.putStringArrayList("MatchingValues", datasetStrings)
                                                extras.putString("LockerName", lockersName)
                                                extras.putLong("PassID", lockersNameID.toLong())
                                                intent.putExtras(extras)
                                                startActivity(intent)
                                                finish()
                                            }
                                        }

                                        3 -> {
                                            if (a.clr == que)
                                            {
                                                datasetStrings.add("$" + a.prc.toString() + ", " + a.brnd.toString() + ", " + a.qty.toString() + ", " + a.clr.toString() + ", " + a.sz)
                                                Toast.makeText(baseContext, "HOORAY", Toast.LENGTH_SHORT).show()
                                                val intent = Intent(baseContext, SearchClothing::class.java)
                                                var extras = Bundle()
                                                extras.putBoolean("SearchingClothing", true)
                                                extras.putString("ClothingType", articleType)
                                                extras.putStringArrayList("MatchingValues", datasetStrings)
                                                extras.putString("LockerName", lockersName)
                                                extras.putLong("PassID", lockersNameID.toLong())
                                                intent.putExtras(extras)
                                                startActivity(intent)
                                                finish()
                                            }
                                        }

                                        4 -> {
                                            if (a.sz == que)
                                            {
                                                datasetStrings.add("$" + a.prc.toString() + ", " + a.brnd.toString() + ", " + a.qty.toString() + ", " + a.clr.toString() + ", " + a.sz)
                                                Toast.makeText(baseContext, "HOORAY", Toast.LENGTH_SHORT).show()
                                                val intent = Intent(baseContext, SearchClothing::class.java)
                                                var extras = Bundle()
                                                extras.putBoolean("SearchingClothing", true)
                                                extras.putString("ClothingType", articleType)
                                                extras.putStringArrayList("MatchingValues", datasetStrings)
                                                extras.putString("LockerName", lockersName)
                                                extras.putLong("PassID", lockersNameID.toLong())
                                                intent.putExtras(extras)
                                                startActivity(intent)
                                                finish()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        cDbWorkerThread.postTask(task2)

                    }

                    if (updating!!)
                    {
                        val del = Tees(lockersClothingID!!, lockersID!!, lockersPrice!!, lockersBrand!!, lockersQty!!, lockersDescrip, lockersClr!!, lockersSize!!)

                        val removeOld = Runnable { val idNum = cDb?.teesDAO()?.deleteTee(del) }
                        cDbWorkerThread.postTask(removeOld)
                    }
                    if (!searching!!) {
                        val getSize = Runnable {
                            val fullSize = cDb?.teesDAO()?.getSize()
                            var inst = Tees(0, 2, price.text.toString().toDouble(), brand.text.toString(), Integer.parseInt(qty.toString()), descrip.text.toString(), clr.text.toString(), size.text.toString())
                            val task = Runnable { cDb?.teesDAO()?.insertTee(inst) }
                            cDbWorkerThread.postTask(task)
                        }
                        cDbWorkerThread.postTask(getSize)
                    }

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

                    if (updating!!)
                    {
                        val del = OuterWear(lockersClothingID!!, lockersID!!, lockersPrice!!, lockersBrand!!, lockersQty!!, lockersDescrip, lockersClr!!, lockersSize!!)

                        val removeOld = Runnable { val idNum = cDb?.outerWearDAO()?.deleteOuter(del) }
                        cDbWorkerThread.postTask(removeOld)
                    }
                    if (!searching!!) {
                        val getSize = Runnable {
                            val fullSize = cDb?.outerWearDAO()?.getSize()
                            var inst = OuterWear(0, 1, price.text.toString().toDouble(), brand.text.toString(), Integer.parseInt(qty.toString()), descrip.text.toString(), clr.text.toString(), size.text.toString())
                            val task = Runnable { cDb?.outerWearDAO()?.insertOuter(inst) }
                            cDbWorkerThread.postTask(task)
                        }
                        cDbWorkerThread.postTask(getSize)
                    }

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

                    if (updating!!)
                    {
                        val del = Shoes(lockersClothingID!!, lockersID!!, lockersPrice!!, lockersBrand!!, lockersQty!!, lockersDescrip, lockersClr!!, lockersSize!!)

                        val removeOld = Runnable { val idNum = cDb?.shoesDAO()?.deleteShoe(del) }
                        cDbWorkerThread.postTask(removeOld)
                    }
                    if (!searching!!) {
                        val getSize = Runnable {
                            val fullSize = cDb?.shoesDAO()?.getSize()
                            var inst = Shoes(0, 1, price.text.toString().toDouble(), brand.text.toString(), Integer.parseInt(qty.toString()), descrip.text.toString(), clr.text.toString(), size.text.toString())
                            val task = Runnable { cDb?.shoesDAO()?.insertShoe(inst) }
                            cDbWorkerThread.postTask(task)
                        }
                        cDbWorkerThread.postTask(getSize)
                    }

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

                    if (updating!!)
                    {
                        val del = Socks(lockersClothingID!!, lockersID!!, lockersPrice!!, lockersBrand!!, lockersQty!!, lockersDescrip, lockersClr!!, lockersSize!!)

                        val removeOld = Runnable { val idNum = cDb?.socksDAO()?.deleteSock(del) }
                        cDbWorkerThread.postTask(removeOld)
                    }

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


                        var inst = Socks(0, 1, price.text.toString().toDouble(), brand.text.toString(), Integer.parseInt(qty.toString()), descrip.text.toString(), clr.text.toString(), size.text.toString())
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

                    if (updating!!)
                    {
                        val del = Pants(lockersClothingID!!, lockersID!!, lockersPrice!!, lockersBrand!!, lockersQty!!, lockersDescrip, lockersClr!!, lockersSize!!)

                        val removeOld = Runnable { val idNum = cDb?.pantsDAO()?.deletePant(del) }
                        cDbWorkerThread.postTask(removeOld)
                    }

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


                        var inst = Pants(0, 1, price.text.toString().toDouble(), brand.text.toString(), Integer.parseInt(qty.toString()), descrip.text.toString(), clr.text.toString(), size.text.toString())
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

                    if (updating!!)
                    {
                        val del = Dresses(lockersClothingID!!, lockersID!!, lockersPrice!!, lockersBrand!!, lockersQty!!, lockersDescrip, lockersClr!!, lockersSize!!)

                        val removeOld = Runnable { val idNum = cDb?.dressesDAO()?.deleteDress(del) }
                        cDbWorkerThread.postTask(removeOld)
                    }
                    if (!searching!!) {
                        val getSize = Runnable {
                            val fullSize = cDb?.dressesDAO()?.getSize()
                            var inst = Dresses(0, 1, price.text.toString().toDouble(), brand.text.toString(), Integer.parseInt(qty.toString()), descrip.text.toString(), clr.text.toString(), size.text.toString())
                            val task = Runnable { cDb?.dressesDAO()?.insertDress(inst) }
                            cDbWorkerThread.postTask(task)
                        }
                        cDbWorkerThread.postTask(getSize)
                    }

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

                    if (updating!!)
                    {
                        val del = Other(lockersClothingID!!, lockersID!!, lockersPrice!!, lockersBrand!!, lockersQty!!, lockersDescrip, lockersClr!!, lockersSize!!)

                        val removeOld = Runnable { val idNum = cDb?.otherDAO()?.deleteOther(del) }
                        cDbWorkerThread.postTask(removeOld)
                    }
                    if (!searching!!) {
                        val getSize = Runnable {
                            val fullSize = cDb?.otherDAO()?.getSize()
                            var inst = Other(0, 1, price.text.toString().toDouble(), brand.text.toString(), Integer.parseInt(qty.toString()), descrip.text.toString(), clr.text.toString(), size.text.toString())
                            val task = Runnable { cDb?.otherDAO()?.insertOther(inst) }
                            cDbWorkerThread.postTask(task)
                        }
                        cDbWorkerThread.postTask(getSize)
                    }

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