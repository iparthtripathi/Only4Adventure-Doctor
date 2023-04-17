package com.onlyforadventure.DocApp.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.onlyforadventure.DocApp.databinding.ActivitySignUpFirstBinding


class SignUp_First : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.nextButton.setOnClickListener {
            val age = binding.ageInput.text.toString().trim()
            val isDoctor = "Doctor"

            if ( Integer.parseInt(age) < 23) {
                Toast.makeText(baseContext, "23 is the minimum age of a Doctor", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (age.isEmpty()) {
                Toast.makeText(baseContext, "Enter age", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val intent = Intent(this, SignUp_Activity::class.java)
                intent.putExtra("isDoctor", isDoctor)
                intent.putExtra("age", age)
                startActivity(intent)
            }

        }

    }
}