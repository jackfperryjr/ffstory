package com.jackperryjr.ffstory

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Main3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        var prevButton = findViewById(R.id.prev_button) as Button

        prevButton.setOnClickListener(View.OnClickListener { view ->
            val intent = Intent(view.context, Main2Activity::class.java)
            view.context.startActivity(intent)
        })
    }

}
