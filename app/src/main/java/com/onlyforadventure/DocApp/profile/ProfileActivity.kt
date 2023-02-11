package com.onlyforadventure.DocApp.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.onlyforadventure.DocApp.auth.SignIn_Activity
import com.onlyforadventure.DocApp.auth.User
import com.onlyforadventure.DocApp.databinding.ActivityProfileBinding
import com.squareup.picasso.Picasso
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var dbreference : DatabaseReference
    private lateinit var user: FirebaseUser
    private lateinit var sharedPreference : SharedPreferences
    private lateinit var userName : String
    private lateinit var userEmail : String
    private lateinit var userPhone : String
    private lateinit var userID: String
    private lateinit var age : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreference = baseContext.getSharedPreferences("UserData", Context.MODE_PRIVATE)

        getUserData()

        binding.ProfileToEdit.setOnClickListener {
            startActivity(Intent(baseContext, EditProfileActivity::class.java))
        }
        binding.logout.setOnClickListener{
            logoutFun()
        }

    }

    private fun logoutFun() {
        val editor = sharedPreference.edit()
        editor.clear()
        editor.apply()
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, SignIn_Activity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()

    }

    private fun getUserData() {

        getDataFromSharedPreference()
        binding.name.text = "Name :  $userName"
        binding.age.text = "Age : $age"
        binding.email.text = "Email : $userEmail"
        binding.phone.text = "Phone Number : $userPhone"
        dbreference = FirebaseDatabase.getInstance().getReference("Users")
        user= FirebaseAuth.getInstance().currentUser!!
        val uID=user.uid
        dbreference.child(uID).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                val imgUrl= user?.imgUrl
                Picasso.get().load(imgUrl).into(binding.profilePicture)
            }
            override fun onCancelled(error: DatabaseError) {}
        })



    }


    override fun onStart() {
        super.onStart()
        Handler().postDelayed({
            getDataFromSharedPreference()
        }, 1000)
    }

    @SuppressLint("SetTextI18n")
    private fun getDataFromSharedPreference() {
        userID = sharedPreference.getString("uid","Not found").toString()
        userName = sharedPreference.getString("name","Not found").toString()
        userEmail = sharedPreference.getString("email","Not found").toString()
        userPhone = sharedPreference.getString("phone","Not found").toString()
        age = sharedPreference.getString("age", "Not found").toString().trim()

    }
}