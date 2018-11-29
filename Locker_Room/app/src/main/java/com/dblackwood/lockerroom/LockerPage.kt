package com.dblackwood.lockerroom

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.dblackwood.lockerroom.dbfiles.AppDataBase
import com.dblackwood.lockerroom.dbfiles.Locker

class LockerPage : AppCompatActivity() {

    private var cDb: AppDataBase? = null

    private lateinit var cDbWorkerThread: DbWorkerThread
    private val cUiHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locker_page)

        cDbWorkerThread = DbWorkerThread("dbWorkerThread")
        cDbWorkerThread.start()

        cDb = AppDataBase.getInstance(this)

        val doneBtn = findViewById<Button>(R.id.buttonCheckValid)
        doneBtn.setOnClickListener()
        {
            val task = Runnable { val checkData =  cDb?.lockerDAO()?.checkLockerExist("fall")
            cUiHandler.post {
                    if (checkData == null || checkData.isEmpty())
                    {
                        val lock = Locker(null, "fall")
                        insertLocker(lock)
                        Log.i("SIZE OF LIST: ", checkData?.size.toString())
                    }
                    else
                    {
                        for (n in checkData)
                        {
                            if (n.lckName == "fall")
                            Log.i("NAME OF LOCKERS: ", n.lckName)
                        }

                        Toast.makeText(baseContext, "This locker name already exists", Toast.LENGTH_LONG).show()

                    }
                }
            }
            cDbWorkerThread.postTask(task)
        }
    }

    private fun insertLocker(lock: Locker)
    {
        val task = Runnable { cDb?.lockerDAO()?.insertLocker(lock) }
        cDbWorkerThread.postTask(task)
        Toast.makeText(baseContext, "Locker has been added", Toast.LENGTH_LONG).show()

        val t2 = Runnable { cDb?.lockerDAO()?.getAll() } // TODO: CHECK IF THE AUTOGEN FOR PRIMARYKEY ID IS WORKING!
    }
}
