package com.jackperryjr.mooglekt

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.*
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.Gravity

import kotlinx.android.synthetic.main.activity_main.*

import java.net.*

import org.jetbrains.anko.*
import org.json.JSONObject
import org.jetbrains.anko.appcompat.v7.Appcompat

import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

//    private var spinner: ProgressBar? = null //Spinner when pulling in a character; not currently in use.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setTitle()
        var titleBar = SpannableString("Moogle Matcher")
        setTitle(titleBar)
//        spinner = findViewById<ProgressBar>(R.id.spinner) //Not currently using.
        moogleApi()

        var reload = findViewById<Button>(R.id.reload_button)
        spinner!!.setVisibility(View.GONE) //Hiding the spinner that I'm not currently using.
        reload.setVisibility(View.GONE) //Just hiding the reload button for now.

//        --- Old reload button ---
//
//        reload.setOnClickListener {
//            //spinner!!.setVisibility(View.VISIBLE)
//            moogleApi()
//        }
//
//        --------------------------
    }

    private fun moogleApi() {
        doAsync {
            val apiURL = URL(URL_RANDOM_CHARACTER).readText()
            uiThread {

                //Convert JSON string back to JSON object.
                val character = JSONObject(apiURL)

                character_list.text = character.optString("name")
                val characterImageUrl = character.optString("picture")
                val characterImage = findViewById<ImageButton>(R.id.character_avatar)

                Picasso.with(applicationContext).load(characterImageUrl).into(characterImage)

                val intent = Intent(this@MainActivity, Main2Activity::class.java)
                intent.putExtra("character", character.toString())

                var info = findViewById<ImageButton>(R.id.character_avatar)

                //Button to read information about character.
                info.setOnClickListener { view ->
                    view.context.startActivity(intent)
                }

                val onSwipeTouchListener = OnSwipeTouchListener(this@MainActivity, findViewById(R.id.character_list))
                onSwipeTouchListener.setOnSwipeListener(object : OnSwipeTouchListener.onSwipeListener {
                    override fun swipeRight() {

                        val chance= (0..10).random()

                        if (chance >= 7) { //Just an easy random selection for now.
                            alert(Appcompat, "It's a match! Send a message?"){
                                negativeButton("Nope!") { moogleApi() }
                                positiveButton("Yes!") {
                                    val intent = Intent(this@MainActivity, Main3Activity::class.java)
                                    intent.putExtra("character", character.toString())
                                    startActivity(intent)
                                    finish()
                                }
                            }.show().apply {
                                getButton(AlertDialog.BUTTON_POSITIVE)?.let { it.backgroundColor = Color.rgb(255,255,255); it.textColor = Color.rgb(33,38,43) }
                                getButton(AlertDialog.BUTTON_NEGATIVE)?.let { it.backgroundColor = Color.rgb(255,255,255); it.textColor = Color.rgb(33,38,43) }
                            }

//                            val intent = Intent(this@MainActivity, Main3Activity::class.java)
//                            intent.putExtra("character", character.toString())
//                            startActivity(intent)
//                            finish()
                        }
                        else {
                            toast("Bummer! No match.").setGravity(Gravity.CENTER, 0, 0)
                            val handler = Handler()
                            handler.postDelayed(Runnable {
                                moogleApi()
                            }, 1000)
                        }
                    }
                    override fun swipeLeft() {
                        moogleApi()
                    }
                })
            }
        }

//        val handler = Handler() //Not currently using.
//        handler.postDelayed(Runnable {
//            spinner!!.setVisibility(View.GONE)
//        }, 2000)
    }

    private fun setTitle() { //Used to color the title.
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
        val URL_RANDOM_CHARACTER = "https://www.moogleapi.com/api/characters/random" //My API :)
    }
}