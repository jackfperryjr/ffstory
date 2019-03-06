package com.jackperryjr.mooglekt

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast

import org.jetbrains.anko.*

class LoginActivity : AppCompatActivity() {

    private val activity = this@LoginActivity

    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var appCompatButtonLogin: AppCompatButton
    private lateinit var textViewLinkRegister: AppCompatTextView
    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Hiding the action bar.
        supportActionBar!!.hide()

        initViews()
        initObjects()

        val loginButton = findViewById<View>(R.id.appCompatButtonLogin)
        loginButton.setOnClickListener { view ->
            verifyFromSQLite()
        }

        val signupLink = findViewById<AppCompatTextView>(R.id.textViewLinkRegister)
        signupLink.setOnClickListener { view ->
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            view.context.startActivity(intent)
            finish()
        }
    }

    private fun initViews() {
        nestedScrollView = findViewById<View>(R.id.nestedScrollView) as NestedScrollView
        textInputLayoutEmail = findViewById<View>(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword = findViewById<View>(R.id.textInputLayoutPassword) as TextInputLayout
        textInputEditTextEmail = findViewById<View>(R.id.textInputEditTextEmail) as TextInputEditText
        textInputEditTextPassword = findViewById<View>(R.id.textInputEditTextPassword) as TextInputEditText
        appCompatButtonLogin = findViewById<View>(R.id.appCompatButtonLogin) as AppCompatButton
        textViewLinkRegister = findViewById<View>(R.id.textViewLinkRegister) as AppCompatTextView
    }

    private fun initObjects() {
        databaseHelper = DatabaseHelper(activity)
        inputValidation = InputValidation(activity)
    }
    // Validate input text and verify login credentials.
    private fun verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return
        }

        if (databaseHelper.checkUser(textInputEditTextEmail.text.toString().trim { it <= ' ' }, textInputEditTextPassword.text.toString().trim { it <= ' ' })) {
            val intent = Intent(activity, MainActivity::class.java)
            //accountsIntent.putExtra("EMAIL", textInputEditTextEmail!!.text.toString().trim { it <= ' ' })
            emptyInputEditText()
            closeKeyboard()
            //toast("Let's start swiping!").setGravity(Gravity.TOP, 0, 0) // anko toast.
            val toast = Toast.makeText(this@LoginActivity, "Let's start swiping!", Toast.LENGTH_SHORT)
            val view = toast.view
            view.setBackgroundColor(Color.TRANSPARENT)
            val text = view.findViewById(android.R.id.message) as TextView
            text.setTextColor(Color.BLACK)
            text.textSize = (24F)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()
            var handler = Handler()
            handler.postDelayed(Runnable {
                startActivity(intent)
                finish()
            }, 700)
        } else {
            closeKeyboard()
            //toast(R.string.error_valid_email_password).setGravity(Gravity.TOP, 0, 0) // anko toast.
            val toast = Toast.makeText(this@LoginActivity, R.string.error_valid_email_password, Toast.LENGTH_SHORT)
            val view = toast.view
            view.setBackgroundColor(Color.TRANSPARENT)
            val text = view.findViewById(android.R.id.message) as TextView
            text.setTextColor(Color.RED)
            text.textSize = (24F)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()
        }
    }
    // Empty input text.
    private fun emptyInputEditText() {
        textInputEditTextEmail.text = null
        textInputEditTextPassword.text = null
    }
    // Close the keyboard.
    private fun closeKeyboard() {
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            this.getCurrentFocus().getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}
