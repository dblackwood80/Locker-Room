package com.dblackwood.lockerroom

import android.os.Handler
import android.os.HandlerThread

class DbWorkerThread(threadName: String) : HandlerThread(threadName) {

    private lateinit var mWorkerHandler: Handler

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        mWorkerHandler = Handler(looper)
    }

    fun postTask(task: Runnable) {
        if (!this::mWorkerHandler.isInitialized)
            onLooperPrepared()

        mWorkerHandler.post(task)
    }

}