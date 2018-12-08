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
import android.widget.TextView
import android.widget.Toast
import com.dblackwood.lockerroom.dbfiles.AppDataBase
import com.dblackwood.lockerroom.dbfiles.Locker
import kotlinx.android.synthetic.main.activity_add_clothing_info.view.*
import kotlinx.android.synthetic.main.recyclerview_row.view.*

class ChooseLocker : AppCompatActivity() {

    private var cDb: AppDataBase? = null
    private var cDbWorkerThread: DbWorkerThread = DbWorkerThread("dbWorkerThread")

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_locker_page)

        cDbWorkerThread.start()

        cDb = AppDataBase.getInstance(this)

        val intent: Intent = intent
        val articleType: String? = intent.extras?.getString("ClothingType")
        val updating: Boolean? = intent.extras?.getBoolean("UpdatingClothing")
        val searching: Boolean? = intent.extras?.getBoolean("SearchingClothing")

        Log.i("CURRENT CLASS: ", localClassName)

        viewManager = LinearLayoutManager(this)
        var myDataset: MutableList<String> = arrayListOf()
        var ids: MutableList<Locker> = arrayListOf()

        val task = Runnable { val listOfLockers = cDb?.lockerDAO()?.getAll()

            if (listOfLockers != null) {
                for (n in listOfLockers)
                {
                    myDataset.add(n.name)
                    ids.add(n)
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

                        val pos = recyclerView.findContainingViewHolder(child)?.adapterPosition
                        val r = recyclerView.findViewHolderForAdapterPosition(pos!!)?.itemView?.findViewById<TextView>(R.id.deleteLockerNames)
                        Log.i("NAME: ", r?.text.toString())

                        if (updating!!)
                        {
                            var passID: Int = 0

                            for (i in ids)
                            {
                                if (r?.text.toString() == i.name.toString())
                                    passID = i.locker_id!!
                            }

                            val ref = Intent(baseContext, SearchClothing::class.java) //dont forget to pass clothing type and locker name/id
                            var extras = Bundle()
                            extras.putString("ClothingType", articleType)
                            extras.putString("LockerName", r?.text.toString())
                            extras.putInt("PassID", passID)
                            extras.putBoolean("UpdatingClothing", true)
                            ref.putExtras(extras)
                            startActivity(ref)
                            finish()
                        }

                        else if (searching!!)
                        {
                            var passID: Int = 0

                            for (i in ids)
                            {
                                if (r?.text.toString() == i.name.toString())
                                    passID = i.locker_id!!
                            }


                            val ref = Intent(baseContext, AddClothingInfo::class.java) //dont forget to pass clothing type and locker name/id
                            var extras = Bundle()
                            extras.putString("ClothingType", articleType)
                            extras.putString("LockerName", r?.text.toString())
                            extras.putInt("PassID", passID)
                            extras.putBoolean("SearchingClothing", true)
                            ref.putExtras(extras)
                            startActivity(ref)
                            finish()
                        }



                        else {

                            val ref = Intent(baseContext, AddClothingInfo::class.java) //dont forget to pass clothing type and locker name/id
                            var extras = Bundle()
                            extras.putString("ClothingType", articleType)
                            extras.putString("LockerName", r?.text.toString())
                            ref.putExtras(extras)
                            startActivity(ref)
                            finish()
                        }
                    }
                }

                finish()

                return true
            }

            override fun onTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent)
            {
                Log.i("IS THIS CALLED", "QUE")
            }
        })

    }
}

//val intent = Intent(this@AddingClothes, ChooseLocker::class.java)
//                        intent.putExtra("ClothingType", selected.text.toString())
//                        startActivity(intent) on click no check marks