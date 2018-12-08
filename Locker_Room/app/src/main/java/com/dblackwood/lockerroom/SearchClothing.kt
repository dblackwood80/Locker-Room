package com.dblackwood.lockerroom

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.dblackwood.lockerroom.dbfiles.*

class SearchClothing : AppCompatActivity() { //is actually became used to update

    private var cDb: AppDataBase? = null

    private lateinit var cDbWorkerThread: DbWorkerThread

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    var checked2: MutableList<String> = arrayListOf()
    var checked2Keep: MutableList<String> = arrayListOf()

    var fullInfo: MutableList<Accessories> = arrayListOf()
    var fullInfoTees: MutableList<Tees> = arrayListOf()
    var fullInfoOuterWear: MutableList<OuterWear> = arrayListOf()
    var fullInfoShoes: MutableList<Shoes> = arrayListOf()
    var fullInfoSocks: MutableList<Socks> = arrayListOf()
    var fullInfoPants: MutableList<Pants> = arrayListOf()
    var fullInfoDresses: MutableList<Dresses> = arrayListOf()
    var fullInfoOther: MutableList<Other> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_locker_page)

        cDbWorkerThread = DbWorkerThread("dbWorkerThread")
        cDbWorkerThread.start()

        cDb = AppDataBase.getInstance(this)
        viewManager = LinearLayoutManager(this)

        val articleType: String? = intent.extras?.getString("ClothingType")
        val passID: Long = intent.extras?.getLong("PassID")!!
        val lockName: String = intent.extras?.getString("LockerName")!!
        val searching: Boolean? = intent.extras?.getBoolean("SearchingClothing")
        var matchingDatasetStrings = intent.extras?.getStringArrayList("MatchingValues")

        Log.i("CURRENT CLASS: ", localClassName)

        var id: Long = 0
        var price: Double = 0.0
        var brand: String = ""
        var qty: Int = 0
        var descrip: String = ""
        var clr: String = ""
        var size: String = ""


        var datasetStrings: MutableList<String> = mutableListOf()

        var myDataset: MutableList<Accessories> = arrayListOf()
        var myDatasetTees: MutableList<Tees> = arrayListOf()
        var myDatasetOuterWear: MutableList<OuterWear> = arrayListOf()
        var myDatasetShoes: MutableList<Shoes> = arrayListOf()
        var myDatasetSocks: MutableList<Socks> = arrayListOf()
        var myDatasetPants: MutableList<Pants> = arrayListOf()
        var myDatasetDresses: MutableList<Dresses> = arrayListOf()
        var myDatasetOther: MutableList<Other> = arrayListOf()


        //if (price.text == null && brand.text == null && qty.text == null && descrip.text == null && clr.text == null && size.text == null)
        //{
            when (articleType)
            {
                "Accessories" -> {
                    Log.i("WHERE", " IS IT")
                    val task = Runnable { val listOfClothes = cDb?.accessoriesDAO()?.getAll()
                        Log.i("WHERE", " IS IT 2")
                        if (listOfClothes != null && listOfClothes.isNotEmpty()) {
                            Log.i("NOT NULL: ", listOfClothes.size.toString())
                            for (n in listOfClothes)
                            {
                                val tempAcc = Accessories(n.acce_id, n.lck_id, n.prc, n.brnd, n.qty, n.descrip, n.clr, n.sz)

                                myDataset.add(tempAcc)
                                //Log.i("VALUES: ","$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + myDataset[0].sz)
                                fullInfo.add(tempAcc)

                                datasetStrings.add("$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + n.sz)
                            }
                        }
                        else
                        {
                            Log.i("IS NULL", " VALUES NOT ADDED")
                            datasetStrings.add("NO DATA")
                        }
                    }
                    Log.i("WHERE", " IS IT 3")
                    cDbWorkerThread.postTask(task)
                    Log.i("WHERE", " IS IT 4")
                }

                "Tees/Long Sleeves" -> {
                    val task = Runnable { val listOfClothes = cDb?.teesDAO()?.getAll()

                        if (listOfClothes != null && listOfClothes.isNotEmpty()) {
                            //Log.i("NOT NULL: ", listOfClothes.size.toString())
                            for (n in listOfClothes)
                            {
                                val tempAcc = Tees(n.tee_id, n.lck_id, n.prc, n.brnd, n.qty, n.descrip, n.clr, n.sz)

                                myDatasetTees.add(tempAcc)
                                //Log.i("VALUES: ","$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + myDataset[0].sz)
                                fullInfoTees.add(tempAcc)
                                datasetStrings.add("$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + n.sz)
                            }
                        }
                        else
                        {
                            Log.i("IS NULL", " VALUES NOT ADDED")
                            datasetStrings.add("NO DATA")
                        }
                    }
                    cDbWorkerThread.postTask(task)
                }

                "Outerwear" -> {
                    val task = Runnable { val listOfClothes = cDb?.outerWearDAO()?.getAll()

                        if (listOfClothes != null && listOfClothes.isNotEmpty()) {
                            //Log.i("NOT NULL: ", listOfClothes.size.toString())
                            for (n in listOfClothes)
                            {
                                val tempAcc = OuterWear(n.outer_id, n.lck_id, n.prc, n.brnd, n.qty, n.descrip, n.clr, n.sz)

                                myDatasetOuterWear.add(tempAcc)
                                //Log.i("VALUES: ","$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + myDataset[0].sz)
                                fullInfoOuterWear.add(tempAcc)
                                datasetStrings.add("$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + n.sz)
                            }
                        }
                        else
                        {
                            Log.i("IS NULL", " VALUES NOT ADDED")
                            datasetStrings.add("NO DATA")
                        }
                    }
                    cDbWorkerThread.postTask(task)
                }

                "Shoes" -> {
                    val task = Runnable { val listOfClothes = cDb?.shoesDAO()?.getAll()

                        if (listOfClothes != null && listOfClothes.isNotEmpty()) {
                            //Log.i("NOT NULL: ", listOfClothes.size.toString())
                            for (n in listOfClothes)
                            {
                                val tempAcc = Shoes(n.shoe_id, n.lck_id, n.prc, n.brnd, n.qty, n.descrip, n.clr, n.sz)

                                myDatasetShoes.add(tempAcc)
                                //Log.i("VALUES: ","$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + myDataset[0].sz)
                                fullInfoShoes.add(tempAcc)
                                datasetStrings.add("$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + n.sz)
                            }
                        }
                        else
                        {
                            Log.i("IS NULL", " VALUES NOT ADDED")
                            datasetStrings.add("NO DATA")
                        }
                    }
                    cDbWorkerThread.postTask(task)
                }

                "Socks" -> {
                    val task = Runnable { val listOfClothes = cDb?.socksDAO()?.getAll()

                        if (listOfClothes != null && listOfClothes.isNotEmpty()) {
                            //Log.i("NOT NULL: ", listOfClothes.size.toString())
                            for (n in listOfClothes)
                            {
                                val tempAcc = Socks(n.sock_id, n.lck_id, n.prc, n.brnd, n.qty, n.descrip, n.clr, n.sz)

                                myDatasetSocks.add(tempAcc)
                                //Log.i("VALUES: ","$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + myDataset[0].sz)
                                fullInfoSocks.add(tempAcc)
                                datasetStrings.add("$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + n.sz)
                            }
                        }
                        else
                        {
                            Log.i("IS NULL", " VALUES NOT ADDED")
                            datasetStrings.add("NO DATA")
                        }
                    }
                    cDbWorkerThread.postTask(task)
                }

                "Pants/Shorts" -> {
                    val task = Runnable { val listOfClothes = cDb?.pantsDAO()?.getAll()

                        if (listOfClothes != null && listOfClothes.isNotEmpty()) {
                            //Log.i("NOT NULL: ", listOfClothes.size.toString())
                            for (n in listOfClothes)
                            {
                                val tempAcc = Pants(n.pant_id, n.lck_id, n.prc, n.brnd, n.qty, n.descrip, n.clr, n.sz)

                                myDatasetPants.add(tempAcc)
                                //Log.i("VALUES: ","$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + myDataset[0].sz)
                                fullInfoPants.add(tempAcc)
                                datasetStrings.add("$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + n.sz)
                            }
                        }
                        else
                        {
                            Log.i("IS NULL", " VALUES NOT ADDED")
                            datasetStrings.add("NO DATA")
                        }
                    }
                    cDbWorkerThread.postTask(task)
                }

                "Dresses" -> {
                    val task = Runnable { val listOfClothes = cDb?.dressesDAO()?.getAll()

                        if (listOfClothes != null && listOfClothes.isNotEmpty()) {
                            //Log.i("NOT NULL: ", listOfClothes.size.toString())
                            for (n in listOfClothes)
                            {
                                val tempAcc = Dresses(n.dress_id, n.lck_id, n.prc, n.brnd, n.qty, n.descrip, n.clr, n.sz)

                                myDatasetDresses.add(tempAcc)
                                //Log.i("VALUES: ","$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + myDataset[0].sz)
                                fullInfoDresses.add(tempAcc)
                                datasetStrings.add("$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + n.sz)
                            }
                        }
                        else
                        {
                            Log.i("IS NULL", " VALUES NOT ADDED")
                            datasetStrings.add("NO DATA")
                        }
                    }
                    cDbWorkerThread.postTask(task)
                }

                "Other" -> {
                    val task = Runnable { val listOfClothes = cDb?.otherDAO()?.getAll()

                        if (listOfClothes != null && listOfClothes.isNotEmpty()) {
                            //Log.i("NOT NULL: ", listOfClothes.size.toString())
                            for (n in listOfClothes)
                            {
                                val tempAcc = Other(n.oth_id, n.lck_id, n.prc, n.brnd, n.qty, n.descrip, n.clr, n.sz)

                                myDatasetOther.add(tempAcc)
                                //Log.i("VALUES: ","$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + myDataset[0].sz)
                                fullInfoOther.add(tempAcc)
                                datasetStrings.add("$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + n.sz)
                            }
                        }
                        else
                        {
                            Log.i("IS NULL", " VALUES NOT ADDED")
                            datasetStrings.add("NO DATA")
                        }
                    }
                    cDbWorkerThread.postTask(task)
                }
            }
       // }

        if (searching!!)
        {
            viewAdapter = DataAdapter(matchingDatasetStrings!!)
        }
        else
        {
            viewAdapter = DataAdapter(datasetStrings)
        }

        recyclerView = findViewById<RecyclerView>(R.id.delete_locker_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

            addOnScrollListener(object: RecyclerView.OnScrollListener()
            {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    Log.i("IS SCROLLING: ", dx.toString() + ", " + dy.toString())
                }
            })
        }

        recyclerView.addOnItemTouchListener(object: RecyclerView.OnItemTouchListener
        {
            override fun onRequestDisallowInterceptTouchEvent(p0: Boolean) {
                Log.i("CRAZY", "GO")
            }

            @SuppressLint("ClickableViewAccessibility")
            override fun onInterceptTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent): Boolean
            {
                if (motionEvent.action != MotionEvent.ACTION_UP)
                    return false

                var child: View? = recyclerView.findChildViewUnder(motionEvent.x, motionEvent.y)

                if (child == null || motionEvent.action == MotionEvent.ACTION_MOVE || motionEvent.action == MotionEvent.ACTION_SCROLL || motionEvent.action == MotionEvent.ACTION_CANCEL || motionEvent.action == MotionEvent.ACTION_DOWN) {
                    recyclerView.requestDisallowInterceptTouchEvent(true)
                    return true
                }
                else {
                    Log.i("SOMETHING", "GOOD")

                    if (recyclerView.scrollState == RecyclerView.SCROLL_STATE_DRAGGING)
                        return false

                    if (!recyclerView.findContainingViewHolder(child)?.itemView?.isSelected!!) {
                        recyclerView.findContainingViewHolder(child)?.itemView?.setBackgroundColor(Color.CYAN)
                        recyclerView.findContainingViewHolder(child)?.itemView?.isSelected = true
                        Toast.makeText(baseContext, "SELECTED: " + datasetStrings[recyclerView.indexOfChild(child)], Toast.LENGTH_LONG).show()
                        checked2.add(datasetStrings[recyclerView.indexOfChild(child)])

                        when (articleType) {
                            "Accessories" -> {
                                for (n in myDataset)
                                {
                                    val checker: String = ("$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + n.sz.toString())
                                    //Log.i("1st: ", n.name)
                                    for (c in checked2)
                                    {
                                        //Log.i("2nd: ", c)
                                        if (checker == c)
                                        {
                                            id = n.acce_id
                                            price = n.prc
                                            brand = n.brnd
                                            qty = n.qty
                                            descrip = n.descrip!!
                                            clr = n.clr
                                            size = n.sz
                                        }
                                        else
                                        {
                                            checked2Keep.add(c)
                                        }
                                    }
                                }
                            }

                            "Tees/Long Sleeves" -> {
                                for (n in myDatasetTees)
                                {
                                    val checker: String = ("$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + n.sz.toString())
                                    //Log.i("1st: ", n.name)
                                    for (c in checked2)
                                    {
                                        //Log.i("2nd: ", c)
                                        if (checker == c)
                                        {
                                            id = n.tee_id
                                            price = n.prc
                                            brand = n.brnd
                                            qty = n.qty
                                            descrip = n.descrip!!
                                            clr = n.clr
                                            size = n.sz
                                        }
                                        else
                                        {
                                            checked2Keep.add(c)
                                        }
                                    }
                                }

                            }

                            "Outerwear" -> {
                                for (n in myDatasetOuterWear)
                                {
                                    val checker: String = ("$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + n.sz.toString())
                                    //Log.i("1st: ", n.name)
                                    for (c in checked2)
                                    {
                                        //Log.i("2nd: ", c)
                                        if (checker == c)
                                        {
                                            id = n.outer_id
                                            price = n.prc
                                            brand = n.brnd
                                            qty = n.qty
                                            descrip = n.descrip!!
                                            clr = n.clr
                                            size = n.sz
                                        }
                                        else
                                        {
                                            checked2Keep.add(c)
                                        }
                                    }
                                }
                            }

                            "Shoes" -> {
                                for (n in myDatasetShoes)
                                {
                                    val checker: String = ("$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + n.sz.toString())
                                    //Log.i("1st: ", n.name)
                                    for (c in checked2)
                                    {
                                        //Log.i("2nd: ", c)
                                        if (checker == c)
                                        {
                                            id = n.shoe_id
                                            price = n.prc
                                            brand = n.brnd
                                            qty = n.qty
                                            descrip = n.descrip!!
                                            clr = n.clr
                                            size = n.sz
                                        }
                                        else
                                        {
                                            checked2Keep.add(c)
                                        }
                                    }
                                }
                            }

                            "Socks" -> {
                                for (n in myDatasetSocks)
                                {
                                    val checker: String = ("$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + n.sz.toString())
                                    //Log.i("1st: ", n.name)
                                    for (c in checked2)
                                    {
                                        //Log.i("2nd: ", c)
                                        if (checker == c)
                                        {
                                            id = n.sock_id
                                            price = n.prc
                                            brand = n.brnd
                                            qty = n.qty
                                            descrip = n.descrip!!
                                            clr = n.clr
                                            size = n.sz
                                        }
                                        else
                                        {
                                            checked2Keep.add(c)
                                        }
                                    }
                                }
                            }

                            "Pants/Shorts" -> {
                                for (n in myDatasetPants)
                                {
                                    val checker: String = ("$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + n.sz.toString())
                                    //Log.i("1st: ", n.name)
                                    for (c in checked2)
                                    {
                                        //Log.i("2nd: ", c)
                                        if (checker == c)
                                        {
                                            id = n.pant_id
                                            price = n.prc
                                            brand = n.brnd
                                            qty = n.qty
                                            descrip = n.descrip!!
                                            clr = n.clr
                                            size = n.sz
                                        }
                                        else
                                        {
                                            checked2Keep.add(c)
                                        }
                                    }
                                }
                            }

                            "Dresses" -> {
                                for (n in myDatasetDresses)
                                {
                                    val checker: String = ("$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + n.sz.toString())
                                    //Log.i("1st: ", n.name)
                                    for (c in checked2)
                                    {
                                        //Log.i("2nd: ", c)
                                        if (checker == c)
                                        {
                                            id = n.dress_id
                                            price = n.prc
                                            brand = n.brnd
                                            qty = n.qty
                                            descrip = n.descrip!!
                                            clr = n.clr
                                            size = n.sz
                                        }
                                        else
                                        {
                                            checked2Keep.add(c)
                                        }
                                    }
                                }
                            }

                            "Other" -> {
                                for (n in myDatasetOther)
                                {
                                    val checker: String = ("$" + n.prc.toString() + ", " + n.brnd.toString() + ", " + n.qty.toString() + ", " + n.clr.toString() + ", " + n.sz.toString())
                                    //Log.i("1st: ", n.name)
                                    for (c in checked2)
                                    {
                                        //Log.i("2nd: ", c)
                                        if (checker == c)
                                        {
                                            id = n.oth_id
                                            price = n.prc
                                            brand = n.brnd
                                            qty = n.qty
                                            descrip = n.descrip!!
                                            clr = n.clr
                                            size = n.sz
                                        }
                                        else
                                        {
                                            checked2Keep.add(c)
                                        }
                                    }
                                }
                            }
                        }

                        val ref = Intent(baseContext, AddClothingInfo::class.java) //dont forget to pass clothing type and locker name/id
                        var extras = Bundle()
                        extras.putLong("ClothingID", id)
                        extras.putDouble("ClothingPrice", price)
                        extras.putString("ClothingBrand", brand)
                        extras.putInt("ClothingQty", qty)
                        extras.putString("ClothingDescrip", descrip)
                        extras.putString("ClothingClr", clr)
                        extras.putString("ClothingSize", size)

                        extras.putString("LockerName", lockName)
                        extras.putLong("PassID", passID)
                        extras.putString("ClothingType", articleType)

                        extras.putBoolean("UpdatingClothing", true)
                        ref.putExtras(extras)
                        startActivity(ref)
                        finish()

                    } else{
                        recyclerView.findContainingViewHolder(child)?.itemView?.setBackgroundColor(Color.TRANSPARENT)
                        recyclerView.findContainingViewHolder(child)?.itemView?.isSelected = false
                        Toast.makeText(baseContext, "UNSELECTED: " + datasetStrings[recyclerView.indexOfChild(child)], Toast.LENGTH_LONG).show()
                        checked2.remove(datasetStrings[recyclerView.indexOfChild(child)])
                    }
                }
                return true
            }

            override fun onTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent)
            {
                Log.i("IS THIS CALLED", "QUE")
            }
        })
    }
}