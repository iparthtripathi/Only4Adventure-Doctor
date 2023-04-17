package com.onlyforadventure.DocApp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.onlyforadventure.DocApp.auth.User

class waitPage : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var auth: FirebaseAuth
    private lateinit var dbreference : DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var user: FirebaseUser
    lateinit var verified: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait_page)

        sharedPreferences=getSharedPreferences("UserData", Context.MODE_PRIVATE)


        dbreference = FirebaseDatabase.getInstance().getReference("Users")
        user= FirebaseAuth.getInstance().currentUser!!
        val uID=user.uid
        dbreference.child(uID).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                verified= snapshot.child("verified").child("verfied").getValue(String::class.java).toString()
                if(verified=="true"){
                    sharedPreferences.edit().putBoolean("verified",true).apply()
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                }else{
                    Handler().postDelayed({
                        sharedPreferences.edit().putBoolean("verified",false).apply()
                        finish()
                    },3000)
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

    }
}