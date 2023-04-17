package com.onlyforadventure.DocApp.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.chaos.view.PinView
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.onlyforadventure.DocApp.R
import java.util.concurrent.TimeUnit

class verifyOTP : AppCompatActivity() {

    lateinit var pinView: PinView
    lateinit var progressBar: ProgressBar
    lateinit var otp: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otp)

        progressBar = findViewById(R.id.progressBar)
        pinView = findViewById(R.id.firstPinView)
        val verifyOTP = findViewById<CardView>(R.id.verifyOTP)
        val mobileNo = findViewById<TextView>(R.id.mobileNo)
        mobileNo.text = String.format("Code sent to +91 %s", intent.getStringExtra("mobile"))
        otp = intent.getStringExtra("otp")!!
        verifyOTP.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            verifyOTP.visibility = View.GONE
            val phoneAuthCredential = PhoneAuthProvider.getCredential(otp, pinView.text.toString())
            FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener { task ->
                    progressBar.visibility = View.GONE
                    verifyOTP.visibility = View.VISIBLE
                    if (task.isSuccessful) {
                        val intent = Intent(applicationContext, SignIn_Activity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@verifyOTP, "Enter the correct OTP", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }

        findViewById<View>(R.id.requestOTP).setOnClickListener {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + intent.getStringExtra("mobile"),
                60,
                TimeUnit.SECONDS,
                this@verifyOTP,
                object : OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {}
                    override fun onVerificationFailed(e: FirebaseException) {
                        Toast.makeText(this@verifyOTP, e.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onCodeSent(
                        newOTP: String,
                        forceResendingToken: ForceResendingToken
                    ) {
                        super.onCodeSent(newOTP, forceResendingToken)
                        otp = newOTP
                    }
                }
            )
        }

    }
}