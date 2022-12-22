package com.onlyforadventure.DocApp.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.onlyforadventure.DocApp.R
import java.util.concurrent.TimeUnit

class enterMobile : AppCompatActivity() {

    lateinit var mobile: EditText
    lateinit var getOTP: CardView
    lateinit var linearLayout: RelativeLayout
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_mobile)


        mobile = findViewById(R.id.mobile)
        mobile.setText(intent.getStringExtra("mobile"))
        getOTP = findViewById(R.id.getOTP)
        linearLayout = findViewById(R.id.linearLayout)
        progressBar = findViewById(R.id.progressBar)
        getOTP.setOnClickListener(View.OnClickListener {
            if (mobile.getText().toString().trim { it <= ' ' }.length == 10) {
                progressBar.setVisibility(View.VISIBLE)
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+91" + mobile.getText().toString(),
                    60,
                    TimeUnit.SECONDS,
                    this@enterMobile,
                    object : OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                            progressBar.setVisibility(View.GONE)
                        }

                        override fun onVerificationFailed(e: FirebaseException) {
                            progressBar.setVisibility(View.GONE)
                            Toast.makeText(this@enterMobile, e.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onCodeSent(
                            otp: String,
                            forceResendingToken: ForceResendingToken
                        ) {
                            super.onCodeSent(otp, forceResendingToken)
                            val intent = Intent(applicationContext, verifyOTP::class.java)
                            intent.putExtra("mobile", mobile.getText().toString())
                            intent.putExtra("otp", otp)
                            startActivity(intent)
                            finish()
                        }
                    }
                )
            } else {
                val snackbar = Snackbar.make(
                    linearLayout,
                    "Enter Correct Mobile Number",
                    BaseTransientBottomBar.LENGTH_SHORT
                )
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(mobile.getWindowToken(), 0)
                snackbar.show()
            }
        })
    }
}