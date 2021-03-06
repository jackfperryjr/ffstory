package com.jackperryjr.mooglekt

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.*
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

import org.json.JSONObject

class BioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bio)
        // Hiding the action bar.
        //supportActionBar!!.hide()
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#ffffff")))
        supportActionBar!!.setTitle("Profile")
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setElevation(0.toFloat())
        setCharacterData()

        val back = findViewById<Button>(R.id.back_button)
        back.setOnClickListener(View.OnClickListener { view ->
            finish()
        })
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the action bar menu from menu xml file.
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }
    // Setting the character data to the screen, this needs work.
    private fun setCharacterData() {
        val character = JSONObject(getIntent().getStringExtra("character"))
        val characterImageUrl = character.optString("picture")

        val characterName = findViewById<TextView>(R.id.character_name)
        val characterAge = findViewById<TextView>(R.id.character_age)
        val characterRace = findViewById<TextView>(R.id.character_race)
        val characterGender = findViewById<TextView>(R.id.character_gender)
        val characterJob = findViewById<TextView>(R.id.character_job)
        val characterAvatar = findViewById<ImageView>(R.id.character_avatar)
        //val characterOrigin = findViewById<TextView>(R.id.character_origin)
        //val characterDescription = findViewById<TextView>(R.id.character_description)

        Picasso.with(applicationContext).load(characterImageUrl).into(characterAvatar)

        characterName.text = "What people call me: " + character.optString("name")
        characterAge.text= "The age I tell people: " + character.optString("age")
        characterRace.text = "I am: " + character.optString("race")
        characterGender.text = "I identify as: " + character.optString("gender")
        characterJob.text = "What I do for work: " + character.optString("job")
        //characterOrigin.text = "Game Origin: " + character.optString("origin")
        //characterDescription.text = character.optString("description")
    }
}
