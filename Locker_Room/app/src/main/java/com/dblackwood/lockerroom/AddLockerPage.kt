package com.dblackwood.lockerroom

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.dblackwood.lockerroom.dbfiles.AppDataBase
import com.dblackwood.lockerroom.dbfiles.Locker

class AddLockerPage : AppCompatActivity() {

    private var cDb: AppDataBase? = null

    private lateinit var cDbWorkerThread: DbWorkerThread
    private val cUiHandler = Handler()

    private lateinit var locName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locker_page)

        cDbWorkerThread = DbWorkerThread("dbWorkerThread")
        cDbWorkerThread.start()

        cDb = AppDataBase.getInstance(this)

        val lNameEdit: EditText = findViewById(R.id.editTextAddLocker)

        val addBtn = findViewById<Button>(R.id.buttonCheckValid)
        addBtn.setOnClickListener()
        {
            locName = lNameEdit.text.toString()
            val task = Runnable { val checkData =  cDb?.lockerDAO()?.checkLockerExist(locName)
            cUiHandler.post {
                    if ((checkData == null || checkData.isEmpty()) && locName != "")
                    {
                        val lock = Locker(null, locName)
                        insertLocker(lock)
                        Log.i("SIZE OF LIST: ", checkData?.size.toString())
                    }
                    else
                    {
                        if (locName == "")
                            Toast.makeText(baseContext, "A blank name is not allowed", Toast.LENGTH_LONG).show()
                        else
                            Toast.makeText(baseContext, "This locker name already exists", Toast.LENGTH_LONG).show()
                    }
                }
            }
            cDbWorkerThread.postTask(task)
        }

        /*val deleteBtn = findViewById<Button>(R.id.buttonDeleteLocker)
        deleteBtn.setOnClickListener()
        {
            locName = lNameEdit.text.toString()
            val task = Runnable { val checkData = cDb?.lockerDAO()?.checkLockerExist(locName)
            cUiHandler.post {
                if (checkData == null || checkData.isEmpty())
                {
                    Toast.makeText(baseContext, "This locker name does not exist", Toast.LENGTH_LONG).show()
                }
            }}
        }*/
    }

    private fun insertLocker(lock: Locker)
    {
        val task = Runnable { cDb?.lockerDAO()?.insertLocker(lock) }
        cDbWorkerThread.postTask(task)
        Toast.makeText(baseContext, "Locker has been added", Toast.LENGTH_LONG).show()

        val t2 = Runnable { val ins = cDb?.lockerDAO()?.getAll()
            if (ins != null) {
                for (a in ins)
                {
                    Log.i("ALL DATA: " + a.locker_id.toString(), a.name)
                }
            }
        } // TODO: CHECK IF THE AUTOGEN FOR PRIMARYKEY ID IS WORKING!
        cDbWorkerThread.postTask(t2)
    }
}
