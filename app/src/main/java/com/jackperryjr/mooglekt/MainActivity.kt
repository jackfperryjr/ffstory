package com.jackperryjr.mooglekt

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import kotlinx.android.synthetic.main.activity_main.*
import java.net.*

import org.jetbrains.anko.*
import org.json.JSONObject

import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private var spinner: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle()
        setContentView(R.layout.activity_main)
        spinner = findViewById<ProgressBar>(R.id.spinner)
        spinner!!.setVisibility(View.VISIBLE)

        moogleApi()

        var reload = findViewById<Button>(R.id.reload_button)

        reload.setOnClickListener {
            spinner!!.setVisibility(View.VISIBLE)
            moogleApi()
        }
    }

    private fun moogleApi() {
        doAsync {
            val apiURL = URL(URL_RANDOM_CHARACTER).readText()
            uiThread {

                //Convert JSON string back to JSON object
                val character = JSONObject(apiURL)

                //Toast the name upon receipt
                //toast(character.optString(("name"))).setGravity(Gravity.CENTER, 0, 0)

                character_list.text = character.optString("name")
                val characterImageUrl = character.optString("picture")
                val characterImage = findViewById<ImageButton>(R.id.character_avatar)

                Picasso.with(applicationContext).load(characterImageUrl).into(characterImage)

                val characterIntent = Intent(this@MainActivity, Main2Activity::class.java)
                characterIntent.putExtra("character", character.toString())

                var info = findViewById<ImageButton>(R.id.character_avatar)

                //Button to send data to second activity
                info.setOnClickListener{ view ->
                    view.context.startActivity(characterIntent)
                }
            }
        }
        spinner!!.setVisibility(View.GONE)
    }

    private fun setTitle() {
        var titleBar = SpannableString("MoogleAPI")
        titleBar.setSpan(RelativeSizeSpan(1.5f), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(66,133,244)), 0, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(204,0,0)), 1, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(255,187,51)), 2, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(66,133,244)), 3, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(0,126,51)), 4, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(204,0,0)), 5, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(255,255,255)), 6, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(RelativeSizeSpan(.75f), 6, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(255,255,255)), 7, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(255,255,255)), 8, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        setTitle(titleBar)
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
        val URL_RANDOM_CHARACTER = "https://www.moogleapi.com/api/characters/random"
        val URL_ALL_CHARACTERS = "https://www.moogleapi.com/api/characters"
    }
}