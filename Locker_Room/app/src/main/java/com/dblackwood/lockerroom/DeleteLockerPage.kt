package com.dblackwood.lockerroom

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import com.dblackwood.lockerroom.dbfiles.AppDataBase
import com.dblackwood.lockerroom.dbfiles.Locker
import kotlinx.android.synthetic.main.recyclerview_row.*
import kotlinx.android.synthetic.main.recyclerview_row.view.*

class DeleteLockerPage : AppCompatActivity() {

    private var cDb: AppDataBase? = null
    private var cDbWorkerThread: DbWorkerThread = DbWorkerThread("dbWorkerThread")

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    var checked2: MutableList<String> = arrayListOf()
    var checked2Keep: MutableList<String> = arrayListOf()
    var fullInfo: MutableList<Locker> = arrayListOf()
   // lateinit var trashCan: ImageView

    var count: Int = 0

    fun pr()
    {

        if (!checked2.isEmpty() || checked2.size > 0)
        {
            for (n in checked2)
            {
                Log.i("IN LIST: ", n + checked2.size.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_locker_page)

        val actionBar: ActionBar? = supportActionBar
        //actionBar?.setCustomView(R.layout.activity_delete_locker_page)
        //actionBar?.setDisplayShowTitleEnabled(false)
        //actionBar?.setDisplayShowCustomEnabled(true)
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM

        var actionBarLayout: LinearLayout = layoutInflater.inflate(R.layout.activity_delete_locker_page, null) as LinearLayout


        var params: ActionBar.LayoutParams = ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.LEFT)

        var trashIco: ImageView = actionBarLayout.findViewById(R.id.imageViewTrash)

        trashIco.setOnClickListener(object: View.OnClickListener
        {
            override fun onClick(v: View?) {
                Log.i("DOGDOG", "HOG")
            }

        })

        actionBar?.setCustomView(actionBarLayout, params)
        actionBar?.setDisplayHomeAsUpEnabled(false)


        //trashCan = findViewById(R.id.imageViewTrash)
        //Log.i("THIS HAS BEEN INIT: ", trashCan?.id.toString())

        //cDbWorkerThread = DbWorkerThread("dbWorkerThread")
        cDbWorkerThread.start()

        cDb = AppDataBase.getInstance(this)

        viewManager = LinearLayoutManager(this)
        var myDataset: MutableList<String> = arrayListOf()

        val task = Runnable { val listOfLockers = cDb?.lockerDAO()?.getAll()

            if (listOfLockers != null) {
                for (n in listOfLockers)
                {
                    myDataset.add(n.name)
                    fullInfo.add(n)
                }
            }
        }
        cDbWorkerThread.postTask(task)

        viewAdapter = DataAdapter(myDataset)

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


            /*addOnItemTouchListener(object: RecyclerView.OnItemTouchListener
            {

                override fun onTouchEvent(p0: RecyclerView, p1: MotionEvent) {
                    Log.i("onTouchEvent", ": CALLED")
                    onInterceptTouchEvent(p0, p1)
                    //if (p1.action == 1)

                }

                override fun onInterceptTouchEvent(p0: RecyclerView, p1: MotionEvent): Boolean {

                    Log.i("onInterceptTouchEvent", ": CALLED " + p1.action)

                    return false
                }

                override fun onRequestDisallowInterceptTouchEvent(p0: Boolean) {
                    Log.i("onInterceptTouchEvent", ": CALLED")
                }

            })*/
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
                        Toast.makeText(baseContext, "SELECTED: " + myDataset[recyclerView.indexOfChild(child)], Toast.LENGTH_LONG).show()
                        checked2.add(myDataset[recyclerView.indexOfChild(child)])
                    } else{
                        recyclerView.findContainingViewHolder(child)?.itemView?.setBackgroundColor(Color.TRANSPARENT)
                        recyclerView.findContainingViewHolder(child)?.itemView?.isSelected = false
                        Toast.makeText(baseContext, "UNSELECTED: " + myDataset[recyclerView.indexOfChild(child)], Toast.LENGTH_LONG).show()
                        checked2.remove(myDataset[recyclerView.indexOfChild(child)])
                    }
                }

                if (checked2.size > 0)
                {
                    trashIco.visibility = View.VISIBLE
                }
                else
                {
                    trashIco.visibility = View.INVISIBLE
                }

                if (trashIco.visibility == View.VISIBLE)
                {
                    trashIco.setOnTouchListener(object: View.OnTouchListener
                    {
                        //@SuppressLint("ClickableViewAccessibility")
                        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                            if (event != null) {
                                if (event.action == MotionEvent.ACTION_DOWN)
                                {
                                    for (n in fullInfo)
                                    {
                                        Log.i("1st: ", n.name)
                                        for (c in checked2)
                                        {
                                            Log.i("2nd: ", c)
                                            if (n.name == c)
                                            {
                                                val taskRemove = Runnable { cDb?.lockerDAO()?.deleteLocker(n) }
                                                cDbWorkerThread.postTask(taskRemove)
                                                Log.i("DELETED: ", n.name)
                                            }
                                            else
                                            {
                                                checked2Keep.add(c)
                                            }
                                        }
                                    }
                                }
                            }
                            viewAdapter = DataAdapter(checked2Keep)
                            finish()
                            overridePendingTransition(0, 0)
                            val ref = Intent(baseContext, DeleteLockerPage::class.java)
                            ref.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                            startActivity(ref)
                            overridePendingTransition(0 ,0)
                            trashIco.visibility = View.INVISIBLE
                            return true
                        }

                    })
                }

                return true
            }

            override fun onTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent)
            {
                Log.i("IS THIS CALLED", "QUE")
            }
        }) //RecyclerTouchListener


        if (viewAdapter.itemCount > 0)
        {
            Log.i("SALVATION", "MAYBE")
        }
    }
}
//TODO: Add all lockers to ordered list for user to select from