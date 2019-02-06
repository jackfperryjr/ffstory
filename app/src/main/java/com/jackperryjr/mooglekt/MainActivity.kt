package com.jackperryjr.mooglekt

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import kotlinx.android.synthetic.main.activity_main.*
import java.net.*

import org.jetbrains.anko.*
import org.json.JSONObject

import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var titleBar = SpannableString("MoogleAPI")
        titleBar.setSpan(RelativeSizeSpan(1.5f), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(66,133,244)), 0, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(204,0,0)), 1, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(255,187,51)), 2, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(66,133,244)), 3, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(0,126,51)), 4, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(204,0,0)), 5, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(255,255,255)), 6, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(RelativeSizeSpan(.75f), 6, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(255,255,255)), 7, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(255,255,255)), 8, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        setTitle(titleBar)
        setContentView(R.layout.activity_main)

        doAsync {
            val randomCharacter = URL("https://www.moogleapi.com/api/characters/random").readText()
            //val characterList = URL("https://www.moogleapi.com/api/characters").readText()
            uiThread {
                //toast(randomCharacter)
                //warn(randomCharacter)
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
                    view.context.startActivity(characterIntent)
                })
            }
        }

        var reload = findViewById<Button>(R.id.reload_button)

        reload.setOnClickListener(View.OnClickListener { view ->
            val intent = Intent(view.context, MainActivity::class.java)
            finish()
            view.context.startActivity(intent)
        })


    }
}
