package com.jackperryjr.mooglekt

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.widget.*
import com.squareup.picasso.Picasso

import org.json.JSONObject

class Main3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        setTitle()

        val character = JSONObject(getIntent().getStringExtra("character"))

        val characterName = findViewById<TextView>(R.id.character_name)
        characterName.text = character.optString("name")

        val characterAge = findViewById<TextView>(R.id.character_age)
        characterAge.text = character.optString("age")

        val characterGender = findViewById<TextView>(R.id.character_gender)
        characterGender.text = character.optString("gender")

        val characterRace = findViewById<TextView>(R.id.character_race)
        characterRace.text = character.optString("race")

        val characterJob = findViewById<TextView>(R.id.character_job)
        characterJob.text = character.optString("job")

        val characterImageUrl = character.optString("picture")
        val characterImage = findViewById<ImageView>(R.id.character_avatar)

        Picasso.with(applicationContext).load(characterImageUrl).into(characterImage)

        var back = findViewById<Button>(R.id.back_button)

        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }
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
