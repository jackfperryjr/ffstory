package com.jackperryjr.mooglekt

import android.widget.Toast
import android.view.MotionEvent
import android.view.GestureDetector
import android.content.Context
import android.view.View

internal class OnSwipeTouchListener(internal var context: Context, mainView: View) : View.OnTouchListener {

    private val gestureDetector: GestureDetector
    internal var onSwipe: onSwipeListener? = null

    init {
        gestureDetector = GestureDetector(context, GestureListener())
        mainView.setOnTouchListener(this)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                        result = true
                    }
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom()
                    } else {
                        onSwipeTop()
                    }
                    result = true
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return result
        }
    }
    companion object {

        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100
    }

    fun onSwipeRight() {
        //Toast.makeText(context, "Right", Toast.LENGTH_SHORT).show()
        this.onSwipe?.swipeRight()
    }

    fun onSwipeLeft() {
        //Toast.makeText(context, "Left", Toast.LENGTH_SHORT).show()
        this.onSwipe?.swipeLeft()
    }

    fun onSwipeTop() {}

    fun onSwipeBottom() {}

    internal interface onSwipeListener {
        fun swipeRight()

        fun swipeLeft()
    }

    fun setOnSwipeListener(onSwipeListener: onSwipeListener) {
        this.onSwipe = onSwipeListener
    }
}