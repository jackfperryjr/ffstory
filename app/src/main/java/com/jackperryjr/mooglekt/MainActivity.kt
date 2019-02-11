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
import android.view.Gravity

import kotlinx.android.synthetic.main.activity_main.*

import java.net.*

import org.jetbrains.anko.*
import org.json.JSONObject
import org.jetbrains.anko.appcompat.v7.Appcompat

import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

//    private var spinner: ProgressBar? = null //Spinner when pulling in a character; not currently in use.
    private var URL_RANDOM_CHARACTER = "https://www.moogleapi.com/api/characters/random" //My API :)
    private var chance: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle()
//        spinner = findViewById<ProgressBar>(R.id.spinner) //Not currently using.
        moogleApi()

        spinner!!.setVisibility(View.GONE) //Hiding the spinner that I'm not currently using.
    }

    private fun moogleApi() {
        doAsync {
            val apiURL = URL(URL_RANDOM_CHARACTER).readText()
            uiThread {

                //Convert JSON string back to JSON object.
                val character = JSONObject(apiURL)

                character_obj.text = character.optString("name")
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

                val onSwipeTouchListener = OnSwipeTouchListener(this@MainActivity, findViewById(R.id.character_obj))
                onSwipeTouchListener.setOnSwipeListener(object : OnSwipeTouchListener.onSwipeListener {
                    override fun swipeRight() {

                        val chance= diceRoll()

                        if (chance >= 15) { //Just an easy random selection for now.
                            val handler = Handler()
                            handler.postDelayed(Runnable {
                                alert(Appcompat, "You liked them! They like you! Send a message?"){
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
                            }, 700)
                        }
                        else if (chance in 7..14) {
                            toast("Fingers crossed!").setGravity(Gravity.CENTER, 0, 0)
                            val handler = Handler()
                            handler.postDelayed (Runnable {
                                moogleApi()
                            }, 700)
                        }
                        else if (chance == 3) {
                            toast("Oh. My. Gosh.").setGravity(Gravity.CENTER, 0, 0)
                            val handler = Handler()
                            handler.postDelayed (Runnable {
                                moogleApi()
                            }, 700)
                        }
                        else {
                            toast("You liked them!").setGravity(Gravity.CENTER, 0, 0)
                            val handler = Handler()
                            handler.postDelayed(Runnable {
                                moogleApi()
                            }, 700)
                        }
                    }
                    override fun swipeLeft() {
                        val chance= diceRoll()

                        if (chance >= 15) {
                            toast("They didn't like you either!").setGravity(Gravity.CENTER, 0, 0)
                        }
                        else if (chance in 7..14) {
                            toast("They really really liked you!").setGravity(Gravity.CENTER, 0, 0)
                        }
                        else {
                            toast("Psh. Let's move on.").setGravity(Gravity.CENTER, 0, 0)
                        }
                        val handler = Handler()
                        handler.postDelayed(Runnable {
                            moogleApi()
                        }, 700)
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
        var titleBar = SpannableString("Moogle Matchmaker")
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(66,133,244)), 0, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(204,0,0)), 1, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(255,187,51)), 2, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(66,133,244)), 3, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(0,126,51)), 4, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(204,0,0)), 5, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(255,255,255)), 6, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        setTitle(titleBar)
    }

    fun diceRoll(): Int {
        return (0..20).random()
    }
}