package com.jackperryjr.mooglekt

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.net.*

import org.jetbrains.anko.*
import org.json.JSONObject

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
                val characterImage = findViewById<ImageButton>(R.id.character_avatar)

                Picasso.with(applicationContext).load(characterImageUrl).into(characterImage)

                val characterIntent = Intent(this@MainActivity, Main2Activity::class.java)
                characterIntent.putExtra("character", character.toString())

                var info = findViewById<ImageButton>(R.id.character_avatar)

                info.setOnClickListener(View.OnClickListener { view ->
                    val intent = Intent(view.context, Main2Activity::class.java)
                    view.context.startActivity(characterIntent)
                })
            }
        }

        var reload = findViewById<Button>(R.id.reload_button)

        reload.setOnClickListener(View.OnClickListener { view ->
            val intent = Intent(view.context, MainActivity::class.java)
            view.context.startActivity(intent)
        })


    }
}
