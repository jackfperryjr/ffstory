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
import android.graphics.drawable.ColorDrawable

import kotlinx.android.synthetic.main.activity_chat.messages

import java.util.*

import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

import org.json.JSONObject

class ChatActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        //setTitle()
        //hiding the action bar
        supportActionBar!!.hide()
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        //Getting character data and setting it.
        val character = JSONObject(getIntent().getStringExtra("character"))
        val characterName = findViewById<TextView>(R.id.character_name)
        characterName.text = character.optString("name")

        //Setting avatar.
        val characterImageUrl = character.optString("picture")
        val characterAvatar = findViewById<ImageView>(R.id.character_avatar)
        Picasso.with(applicationContext).load(characterImageUrl).transform(CropCircleTransformation()).into(characterAvatar)

        //Setting intent to go to info screen.
        val intent = Intent(this@ChatActivity, BioActivity::class.java)
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
        listView = findViewById(R.id.messages)
        val listMessages = ArrayList<Message>()
        val adapter = MessageAdapter(this, listMessages)
        listView.adapter = adapter

        //Send message on key press enter.
        message.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                sendMessage(listMessages, messages, message)
                return@OnKeyListener true
            }
            false
        })

        //Send message on button click.
        val send = findViewById<Button>(R.id.send_button)
        send.setOnClickListener {
            sendMessage(listMessages, messages, message)
        }
    }

    private fun closeKeyboard() {
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            this.getCurrentFocus().getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun sendMessage(listMessages : ArrayList<Message>, messages : ListView, message : EditText) {
        var userMessage = Message(message.text.toString(), isUser = true)
        listMessages.add(userMessage)
        messages.invalidateViews()
        message.text.clear()
        closeKeyboard()
        val handler = Handler()
        val responseTime = responseDiceRoll()
        val responseIndex = diceRoll()
        val responseMessages = java.util.ArrayList<String>()
        responseMessages.add("Hey there!")
        responseMessages.add("How's the weather?")
        responseMessages.add("What's your favorite game?")
        responseMessages.add("Lovely.")
        responseMessages.add("Yeah!")
        responseMessages.add("Nope.")
        responseMessages.add("That's so nice.")
        responseMessages.add(";-)")
        responseMessages.add("Haha!")
        responseMessages.add("I don't think so.")
        responseMessages.add("Maybe.")
        responseMessages.add("I would never!")
        responseMessages.add("Maybe.")
        responseMessages.add("Yeah.")
        responseMessages.add("What?!")
        responseMessages.add("You're so funny.")
        responseMessages.add("Word.")
        responseMessages.add("I love what I do.")
        responseMessages.add("Final Fantasy is so cool.")
        responseMessages.add("This developer is so cool.")
        handler.postDelayed(Runnable {
            if (listMessages.size <= 1) {
                var theirMessage = Message(responseMessages[0], isUser = false)
                listMessages.add(theirMessage)
            } else {
                var theirMessage = Message(responseMessages[responseIndex], isUser = false)
                listMessages.add(theirMessage)
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
        getSupportActionBar()!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#ffffff")))
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
