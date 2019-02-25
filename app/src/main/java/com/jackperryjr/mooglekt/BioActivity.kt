package com.jackperryjr.mooglekt

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

import org.json.JSONObject

class BioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bio)
        // Hiding the action bar.
        supportActionBar!!.hide()
        setCharacterData()

        val back = findViewById<Button>(R.id.back_button)
        back.setOnClickListener(View.OnClickListener { view ->
            finish()
        })
    }
    // Setting the character data to the screen, this needs work.
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
    }
}
