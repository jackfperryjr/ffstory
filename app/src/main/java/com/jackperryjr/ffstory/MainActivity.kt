package com.jackperryjr.ffstory

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.*
import android.util.*
import kotlinx.android.synthetic.main.activity_main.*
import java.net.*
import java.io.InputStream

import org.jetbrains.anko.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import android.graphics.BitmapFactory
import android.graphics.Bitmap

import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        doAsync {
            val randomCharacter = URL("https://www.moogleapi.com/api/characters/random").readText()
            //val characterList = URL("https://www.moogleapi.com/api/characters").readText()
            uiThread {
                //toast(result)

                //Display the random character name.
                val character = JSONObject(randomCharacter)
                character_list.text = character.optString("name")
                val characterImageUrl = character.optString("picture")
                val characterImage = findViewById(R.id.character_avatar) as ImageView

                Picasso.with(applicationContext).load(characterImageUrl).into(characterImage)
            }

            var nextButton = findViewById(R.id.next_button) as Button

            nextButton.setOnClickListener(View.OnClickListener { view ->
                val intent = Intent(view.context, MainActivity::class.java)
                view.context.startActivity(intent)
            })
        }
    }
}
