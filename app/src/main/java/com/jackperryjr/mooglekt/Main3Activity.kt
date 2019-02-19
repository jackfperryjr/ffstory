package com.jackperryjr.mooglekt

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.content.Context
import android.widget.ArrayAdapter

import java.util.*

import com.squareup.picasso.Picasso

import org.json.JSONObject

class Main3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        setTitle()
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        //Getting character data and setting it.
        val character = JSONObject(getIntent().getStringExtra("character"))
        val characterName = findViewById<TextView>(R.id.character_name)
        characterName.text = character.optString("name")
        val characterMatch = character.optString("name").split(" ")

        //Setting avatar.
        val characterImageUrl = character.optString("picture")
        val characterAvatar = findViewById<ImageView>(R.id.character_avatar)
        Picasso.with(applicationContext).load(characterImageUrl).into(characterAvatar)

        //Setting intent to go to info screen.
        val intent = Intent(this@Main3Activity, Main2Activity::class.java)
        intent.putExtra("character", character.toString())

        //Button to read information about character.
        characterAvatar.setOnClickListener { view ->
            view.context.startActivity(intent)
        }

        //Button to go back to home screen.
        val back = findViewById<Button>(R.id.back_button)
        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        //Setting up chat system.
        val message = findViewById<EditText>(R.id.compose_message)
        val messages = findViewById<ListView>(R.id.messages)
        val listMessages = ArrayList<String>()
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listMessages)
        messages.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL)
        messages.setAdapter(adapter)


        //val layout = LayoutInflater.from(applicationContext)
        //val view = layout.inflate(R.layout.rectangle_grey, null, false)
        //messages.addView(view)

        //Send message on key press enter.
        message.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                sendMessage(listMessages, messages, message, characterMatch)
                return@OnKeyListener true
            }
            false
        })

        //Send message on button click.
        val send = findViewById<Button>(R.id.send_button)
        send.setOnClickListener {
            sendMessage(listMessages, messages, message, characterMatch)
        }
    }

    private fun closeKeyboard() {
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            this.getCurrentFocus().getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun sendMessage(listMessages : ArrayList<String>, messages : ListView, message : EditText, characterMatch : List<String>) {
        listMessages.add("You: " + message.text)
        messages.invalidateViews()
        message.text.clear()
        closeKeyboard()
        val handler = Handler()
        val responseTime = responseDiceRoll()
        val responseIndex = diceRoll()
        val responseMessages = ArrayList<String>()
        responseMessages.add(characterMatch[0] + ": Hey there!")
        responseMessages.add(characterMatch[0] + ": How's the weather?")
        responseMessages.add(characterMatch[0] + ": What's your favorite game?")
        responseMessages.add(characterMatch[0] + ": Lovely.")
        responseMessages.add(characterMatch[0] + ": Yeah!")
        responseMessages.add(characterMatch[0] + ": Nope.")
        responseMessages.add(characterMatch[0] + ": That's so nice.")
        responseMessages.add(characterMatch[0] + ": ;-)")
        responseMessages.add(characterMatch[0] + ": Haha!")
        responseMessages.add(characterMatch[0] + ": I don't think so.")
        responseMessages.add(characterMatch[0] + ": Maybe.")
        responseMessages.add(characterMatch[0] + ": I would never!")
        responseMessages.add(characterMatch[0] + ": Maybe.")
        responseMessages.add(characterMatch[0] + ": Yeah.")
        responseMessages.add(characterMatch[0] + ": What?!")
        responseMessages.add(characterMatch[0] + ": You're so funny.")
        responseMessages.add(characterMatch[0] + ": Word.")
        responseMessages.add(characterMatch[0] + ": I love what I do.")
        responseMessages.add(characterMatch[0] + ": Final Fantasy is so cool.")
        responseMessages.add(characterMatch[0] + ": This developer is so cool.")
        handler.postDelayed(Runnable {
            if (listMessages.size <= 1) {
                listMessages.add(responseMessages[0])
            } else {
                listMessages.add(responseMessages[responseIndex])
            }
            messages.invalidateViews()
        }, responseTime)
    }

    private fun diceRoll(): Int {
        return (0..20).random()
    }

    private fun responseDiceRoll(): Long {
        return (2000..9000).random().toLong()
    }

    private fun setTitle() { //Used to color the title.
        val titleBar = SpannableString("Moogle Matchmaker")
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(66,133,244)), 0, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(204,0,0)), 1, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(255,187,51)), 2, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(66,133,244)), 3, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(0,126,51)), 4, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(204,0,0)), 5, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(255,255,255)), 6, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        setTitle(titleBar)
    }
}
