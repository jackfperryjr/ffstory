package com.jackperryjr.ffstory

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var startButton = findViewById(R.id.start_button) as Button

        startButton.setOnClickListener(View.OnClickListener { view ->
            val intent = Intent(view.context, Main2Activity::class.java)
            view.context.startActivity(intent)
        })
    }
}
