package com.jackperryjr.mooglekt

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.widget.*
import android.graphics.Color
import android.view.Gravity

import java.net.*

import org.jetbrains.anko.*
import org.json.JSONObject
import org.jetbrains.anko.appcompat.v7.Appcompat

import com.squareup.picasso.Picasso

import jp.wasabeef.picasso.transformations.CropCircleTransformation

class MainActivity : AppCompatActivity() {
    //private var spinner: ProgressBar? = null // Spinner when pulling in a character; not currently in use.
    private var URL_RANDOM_CHARACTER = "https://www.moogleapi.com/api/v1/characters/random" //My API :)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Hiding the action bar.
        supportActionBar!!.hide()
        //spinner = findViewById<ProgressBar>(R.id.spinner) // Not currently using.
        moogleApi()
        //spinner!!.setVisibility(View.GONE) // Hiding the spinner that I'm not currently using.
    }

    private fun moogleApi() {
        doAsync {
            val apiURL = URL(URL_RANDOM_CHARACTER).readText()
            uiThread {
                // Convert JSON string back to JSON object.
                val character = JSONObject(apiURL)
                //
                val characterLike = findViewById<ImageView>(R.id.character_like_button)
                val characterInfo = findViewById<ImageView>(R.id.character_info_button)
                val characterName = findViewById<TextView>(R.id.character_name)
                val characterImageUrl = character.optString("picture")
                val characterAvatar = findViewById<ImageView>(R.id.character_avatar)
                characterName.text = character.optString("name")
                Picasso.with(applicationContext).load(characterImageUrl).into(characterAvatar) // Non-circular picture.
                //Picasso.with(applicationContext).load(characterImageUrl).transform(CropCircleTransformation()).into(characterAvatar) // Circle picture.

                val intent = Intent(this@MainActivity, BioActivity::class.java)
                intent.putExtra("character", character.toString())
                // Button to read information about character.
                characterInfo.setOnClickListener { view ->
                    view.context.startActivity(intent)
                }
                characterLike.setOnClickListener {
                    right(character)
                }
                // Swipe listener to swipe right or left on characters.
                val onSwipeTouchListener = OnSwipeTouchListener(this@MainActivity, findViewById(R.id.character_avatar))
                onSwipeTouchListener.setOnSwipeListener(object : OnSwipeTouchListener.onSwipeListener {
                    override fun swipeRight() {
                        right(character)
                    }
                    // If you don't want to match with the character.
                    override fun swipeLeft() {
                        toastMessage("You didn't like them?", 1)
                    }
                })
            }
        }
        //val handler = Handler() //Not currently using.
        //handler.postDelayed(Runnable {
            //spinner!!.setVisibility(View.GONE)
        //}, 2000)
    }
    // Random dice roll to determine if you match or not.
    private fun diceRoll(): Int {
        return (0..20).random()
    }
    private fun toastMessage(message: String, action: Int) {
        val toast = Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
        val view = toast.view
        view.setBackgroundColor(Color.TRANSPARENT)
        val text = view.findViewById(android.R.id.message) as TextView
        text.setTextColor(Color.BLACK)
        text.textSize = (24F)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
        if (action == 1) {
            val handler = Handler()
            handler.postDelayed(Runnable {
                moogleApi()
            }, 700)
        }
    }

    private fun right(character: JSONObject) {
        val chance= diceRoll()
        // If you randomly match with the character!
        if (chance >= 15) { // Just an easy random selection for now.
            val handler = Handler()
            handler.postDelayed(Runnable {
                alert(Appcompat) {
                    title = "You Matched!"
                    negativeButton("Nope!") { moogleApi() }
                    positiveButton("Yes!") {
                        val intent = Intent(this@MainActivity, ChatActivity::class.java)
                        intent.putExtra("character", character.toString())
                        startActivity(intent)
                        finish()
                    }
                    customView {
                        linearLayout {
                            textView("Would you like to send a message?")
                            padding = dip(20)
                            weightSum = 1.0f
                        }
                    }
                }.show()
                    .apply {
                        getButton(AlertDialog.BUTTON_POSITIVE)?.let { it.backgroundColor = Color.rgb(255,255,255); it.textColor = Color.rgb(33,38,43) }
                        getButton(AlertDialog.BUTTON_NEGATIVE)?.let { it.backgroundColor = Color.rgb(255,255,255); it.textColor = Color.rgb(33,38,43) }
                    }
            }, 700)
        }
        // If you don't randomly match with the character.
        else {
            toastMessage("You liked them!", 1)
        }
    }
}