package com.onlyforadventure.DocApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class displayDoctor : AppCompatActivity(),SelectListener {


    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: DatabaseReference
    lateinit var databaseReference: DatabaseReference
    lateinit var userAdapter: userAdapter
    lateinit var list: ArrayList<userModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_doctor)

        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        db = FirebaseDatabase.getInstance().reference
        val tag= Integer.parseInt(intent.extras!!.getString("tag"))
        val items = listOf("All","Cardiologist", "Dentist", "ENT specialist", "Obstetrician/Gynaecologist", "Orthopaedic surgeon","Psychiatrists", "Radiologist", "Pulmonologist", "Neurologist", "Allergists", "Gastroenterologists", "Urologists", "Otolaryngologists", "Rheumatologists", "Anesthesiologists")
        val userList=findViewById<RecyclerView>(R.id.userList)
        val specialization=items[tag]
        val spec=findViewById<TextView>(R.id.specTextView)

        databaseReference = FirebaseDatabase.getInstance().getReference("Doctor")
        // recyclerView?.setHasFixedSize(true)

        list = ArrayList<userModel>()

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (dataSnapshot in snapshot.children) {
                    if(specialization==dataSnapshot.child("specialist").getValue(String::class.java)){
                        val userModel = userModel(
                            dataSnapshot.child("name").getValue(String::class.java),
                            dataSnapshot.child("email").getValue(String::class.java),
                            dataSnapshot.child("specialist").getValue(String::class.java),
                            dataSnapshot.child("phone").getValue(String::class.java),
                            dataSnapshot.child("imgUrl").getValue(String::class.java),
                            dataSnapshot.child("uid").getValue(String::class.java)
                        )
                        list.add(userModel)
                        spec.setText("Specialization: "+specialization)
                    }

                }
                if(specialization=="All"){
                    for (dataSnapshot in snapshot.children) {
                        val userModel = userModel(
                            dataSnapshot.child("name").getValue(String::class.java),
                            dataSnapshot.child("email").getValue(String::class.java),
                            dataSnapshot.child("specialist").getValue(String::class.java),
                            dataSnapshot.child("phone").getValue(String::class.java),
                            dataSnapshot.child("imgUrl").getValue(String::class.java),
                            dataSnapshot.child("uid").getValue(String::class.java)

                        )
                        list.add(userModel)
                        spec.text = "Specialization: All"
                    }
                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        userList.layoutManager = LinearLayoutManager(this)

        userAdapter = userAdapter(this, list,this)
        userList.adapter = userAdapter
    }

    override fun onItemClicked(userModel: userModel?) {

    }
}