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

class SignupActivity : AppCompatActivity() {

    private val activity = this@SignupActivity

    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputLayoutConfirmPassword: TextInputLayout
    private lateinit var textInputEditTextName: TextInputEditText
    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var textInputEditTextConfirmPassword: TextInputEditText
    private lateinit var appCompatButtonRegister: AppCompatButton
    private lateinit var appCompatTextViewLoginLink: AppCompatTextView
    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        // Hiding the action bar.
        supportActionBar!!.hide()

        initViews()
        initObjects()

        val signupButton = findViewById<View>(R.id.appCompatButtonRegister)
        signupButton.setOnClickListener { view ->
            postDataToSQLite()
        }

        val loginLink = findViewById<AppCompatTextView>(R.id.appCompatTextViewLoginLink)
        loginLink.setOnClickListener { view ->
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            view.context.startActivity(intent)
            finish()
        }
    }
    // Initialize views.
    private fun initViews() {
        nestedScrollView = findViewById<View>(R.id.nestedScrollView) as NestedScrollView
        textInputLayoutName = findViewById<View>(R.id.textInputLayoutName) as TextInputLayout
        textInputLayoutEmail = findViewById<View>(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword = findViewById<View>(R.id.textInputLayoutPassword) as TextInputLayout
        textInputLayoutConfirmPassword = findViewById<View>(R.id.textInputLayoutConfirmPassword) as TextInputLayout
        textInputEditTextName = findViewById<View>(R.id.textInputEditTextName) as TextInputEditText
        textInputEditTextEmail = findViewById<View>(R.id.textInputEditTextEmail) as TextInputEditText
        textInputEditTextPassword = findViewById<View>(R.id.textInputEditTextPassword) as TextInputEditText
        textInputEditTextConfirmPassword = findViewById<View>(R.id.textInputEditTextConfirmPassword) as TextInputEditText
        appCompatButtonRegister = findViewById<View>(R.id.appCompatButtonRegister) as AppCompatButton
        appCompatTextViewLoginLink = findViewById<View>(R.id.appCompatTextViewLoginLink) as AppCompatTextView
    }
    // Initialize objects.
    private fun initObjects() {
        inputValidation = InputValidation(activity)
        databaseHelper = DatabaseHelper(activity)
    }
    // Validate input text and verify login credentials.
    private fun postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return
        }

        if (!databaseHelper.checkUser(textInputEditTextEmail.text.toString().trim())) {

            var user = User(username = textInputEditTextName.text.toString().trim(),
                email = textInputEditTextEmail.text.toString().trim(),
                password = textInputEditTextPassword.text.toString().trim())

            databaseHelper.addUser(user)
            closeKeyboard()
            toastMessage(R.string.success_message.toString(), 1, intent)
        } else {
            closeKeyboard()
            toastMessage(R.string.error_email_exists.toString(), 0, intent)
        }
    }
    // Empty input text.
    private fun emptyInputEditText() {
        textInputEditTextName.text = null
        textInputEditTextEmail.text = null
        textInputEditTextPassword.text = null
        textInputEditTextConfirmPassword.text = null
    }
    // Close the keyboard.
    private fun closeKeyboard() {
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            this.getCurrentFocus().getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
    private fun toastMessage(message: String, action: Int, intent: Intent) {
        val toast = Toast.makeText(this@SignupActivity, message, Toast.LENGTH_SHORT)
        val view = toast.view
        view.setBackgroundColor(Color.TRANSPARENT)
        val text = view.findViewById(android.R.id.message) as TextView
        text.setTextColor(Color.BLACK)
        text.textSize = (24F)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
        if (action == 1) {
            var handler = Handler()
            handler.postDelayed(Runnable {
                startActivity(intent)
                finish()
            }, 700)
        }
    }
}
