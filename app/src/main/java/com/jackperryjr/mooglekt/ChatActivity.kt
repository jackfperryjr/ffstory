package com.jackperryjr.mooglekt

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.content.Context

import kotlinx.android.synthetic.main.activity_chat.messages

import java.util.*
import java.text.SimpleDateFormat

import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

import org.json.JSONObject

class ChatActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        // Hiding the action bar.
        supportActionBar!!.hide()
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        // Getting character data and setting it.
        val character = JSONObject(getIntent().getStringExtra("character"))
        val characterName = findViewById<TextView>(R.id.character_name)
        characterName.text = character.optString("name")
        // Setting avatar.
        val characterImageUrl = character.optString("picture")
        val characterAvatar = findViewById<ImageView>(R.id.character_avatar)
        Picasso.with(applicationContext).load(characterImageUrl).transform(CropCircleTransformation()).into(characterAvatar)
        // Setting intent to go to info screen.
        val intent = Intent(this@ChatActivity, BioActivity::class.java)
        intent.putExtra("character", character.toString())
        // Button to read information about character.
        characterAvatar.setOnClickListener { view ->
            view.context.startActivity(intent)
        }
        // Button to go back to home screen.
        val back = findViewById<Button>(R.id.back_button)
        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        // Setting up chat system.
        val message = findViewById<EditText>(R.id.compose_message)
        listView = findViewById(R.id.messages)
        val listMessages = ArrayList<Message>()
        val adapter = MessageAdapter(this, listMessages)
        listView.adapter = adapter
        // Send message on key press enter.
        message.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                sendMessage(listMessages, messages, message)
                return@OnKeyListener true
            }
            false
        })
        // Send message on button click.
        val send = findViewById<ImageButton>(R.id.send_button)
        send.setOnClickListener {
            sendMessage(listMessages, messages, message)
        }
    }
    // Close the keyboard.
    private fun closeKeyboard() {
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            this.getCurrentFocus().getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
    // Send that message.
    private fun sendMessage(listMessages : ArrayList<Message>, messages : ListView, message : EditText) {
        var date = Date()
        val formatter = SimpleDateFormat( "HH:mm a")
        var timeStamp = "Sent " + formatter.format(date).toString()
        var userMessage = Message(message.text.toString(), isUser = true, timeStamp = timeStamp)
        if (userMessage.text.trim().equals("")){
            closeKeyboard()
        }
        else {
            listMessages.add(userMessage)
            messages.invalidateViews()
            message.text.clear()
            closeKeyboard()
            val handler = Handler()
            val responseTime = responseDiceRoll()
            val responseIndex = diceRoll()
            // Temporary generic array of responses.
            val responseMessages = java.util.ArrayList<String>()
            responseMessages.add("Hey there! \uD83D\uDE01")
            responseMessages.add("How's the weather?")
            responseMessages.add("What's your favorite game?")
            responseMessages.add("Lovely.")
            responseMessages.add("Yeah!")
            responseMessages.add("Nope.")
            responseMessages.add("That's so nice.")
            responseMessages.add("\uD83D\uDE09")
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
            // Used to respond to your message, for now.
            handler.postDelayed(Runnable {
                var date = Date()
                val formatter = SimpleDateFormat("HH:mm a")
                var timeStamp = "Sent " + formatter.format(date).toString()
                if (listMessages.size <= 1) {
                    var theirMessage = Message(responseMessages[0], isUser = false, timeStamp = timeStamp)
                    listMessages.add(theirMessage)
                } else {
                    var theirMessage = Message(responseMessages[responseIndex], isUser = false, timeStamp = timeStamp)
                    listMessages.add(theirMessage)
                }
                messages.invalidateViews()
            }, responseTime)
        }
    }
    // Random dice roll to determine which generic response to send.
    private fun diceRoll(): Int {
        return (0..20).random()
    }
    // Random dice roll to determine the amount of time before response is sent.
    private fun responseDiceRoll(): Long {
        return (2000..90000).random().toLong()
    }
}
