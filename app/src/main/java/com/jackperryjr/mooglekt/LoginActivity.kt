package com.jackperryjr.mooglekt

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.inputmethod.InputMethodManager

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
        //setTitle()
        //hiding the action bar
        supportActionBar!!.hide()

        // initializing the views
        initViews()

        // initializing the objects
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
    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
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
//            var snackBar = Snackbar.make(nestedScrollView, "Welcome! Start swiping!", Snackbar.LENGTH_LONG)
//            snackBar.setActionTextColor(getResources().getColor(R.color.colorText))
//            snackBar.view.setBackgroundColor(getResources().getColor(R.color.snackbarBackground))
//            snackBar.show()
            toast("Let's start swiping!").setGravity(Gravity.TOP, 0, 0)
            var handler = Handler()
            handler.postDelayed(Runnable {
                startActivity(intent)
                finish()
            }, 700)
        } else {
            // Snack Bar to show success message that record is wrong
            closeKeyboard()
//            var snackBar = Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG)
//            snackBar.setActionTextColor(getResources().getColor(R.color.colorText))
//            snackBar.view.setBackgroundColor(getResources().getColor(R.color.snackbarBackground))
//            snackBar.show()
            toast(R.string.error_valid_email_password).setGravity(Gravity.TOP, 0, 0)
        }
    }
    /**
     * This method is to empty all input edit text
     */
    private fun emptyInputEditText() {
        textInputEditTextEmail.text = null
        textInputEditTextPassword.text = null
    }

    private fun closeKeyboard() {
        val inputManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            this.getCurrentFocus().getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun setTitle() { //Used to color the title.
        getSupportActionBar()!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#ffffff")))
        val titleBar = SpannableString("Moogle Matchmaker")
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(66,133,244)), 0, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(204,0,0)), 1, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(255,187,51)), 2, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(66,133,244)), 3, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(0,126,51)), 4, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(204,0,0)), 5, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        titleBar.setSpan(ForegroundColorSpan(Color.rgb(0,0,0)), 6, titleBar.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        setTitle(titleBar)
    }
}
