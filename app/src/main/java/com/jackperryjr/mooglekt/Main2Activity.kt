package com.jackperryjr.mooglekt

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.View
import android.widget.*

import org.json.JSONObject

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setTitle()

        setCharacterData()

        var back = findViewById<Button>(R.id.back_button)

        back.setOnClickListener(View.OnClickListener { view ->
            val intent = Intent(view.context, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            view.context.startActivity(intent)
        })
    }

    private fun setCharacterData() {

        val character = JSONObject(getIntent().getStringExtra("character"))

        val characterName = findViewById<TextView>(R.id.character_name)
        val characterAge = findViewById<TextView>(R.id.character_age)
        val characterRace = findViewById<TextView>(R.id.character_race)
        val characterGender = findViewById<TextView>(R.id.character_gender)
        val characterJob = findViewById<TextView>(R.id.character_job)
        val characterOrigin = findViewById<TextView>(R.id.character_origin)
        val characterDescription = findViewById<TextView>(R.id.character_description)

        characterName.text = "Name: " + character.optString("name")
        characterAge.text= "Age: " + character.optString("age")
        characterRace.text = "Race: " + character.optString("race")
        characterGender.text = "Gender: " + character.optString("gender")
        characterJob.text = "Job Class: " + character.optString("job")
        characterOrigin.text = "Game Origin: " + character.optString("origin")
        characterDescription.text = character.optString("description")

//        --- Something I'm trying to figure out ---
//
//        var name = character.optString("name")
//        var age = character.optString("age")
//        var race = character.optString("race")
//        var gender = character.optString("gender")
//        var job = character.optString("job")
//        var origin = character.optString("origin")
//        var description = character.optString("description")
//
//        findViewById<TextView>(R.id.character_name).getString(R.id.character_name) + name
//        val characterAge = findViewById<TextView>(R.id.character_age)
//        val characterRace = findViewById<TextView>(R.id.character_race)
//        val characterGender = findViewById<TextView>(R.id.character_gender)
//        val characterJob = findViewById<TextView>(R.id.character_job)
//        val characterOrigin = findViewById<TextView>(R.id.character_origin)
//        val characterDescription = findViewById<TextView>(R.id.character_description)
//
//        __________________________________________
    }

    private fun setTitle() { //Used to color the title.
        var titleBar = SpannableString("Moogle Matcher")
        titleBar.setSpan(RelativeSizeSpan(1.5f), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
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
