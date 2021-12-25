package com.info.notlaruygulamasi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    private lateinit var notlarListe:ArrayList<Notlar>
    private lateinit var adapter:NotlarAdapter
    lateinit var   database: DatabaseReference
    var toplam =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Notlar Uygulaması"

        setSupportActionBar(toolbar)

        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this)

        val db = Firebase.database
        database = db.getReference("users")

        notlarListe= ArrayList()
        adapter = NotlarAdapter(this,notlarListe)

        rv.adapter = adapter
tumNotlar()
        fab.setOnClickListener {

            startActivity(Intent(this@MainActivity,NotKayitActivity::class.java))

        }
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
    fun tumNotlar(){

database.addValueEventListener(object :ValueEventListener{

    override fun onDataChange(snapshot: DataSnapshot) {

notlarListe.clear()
      for (c in snapshot.children) {

          val not = c.getValue(Notlar::class.java)

          if (not != null) {
              not.not_id = c.key
              notlarListe.add(not)

          }


  }
 adapter.notifyDataSetChanged()

    }

    override fun onCancelled(error: DatabaseError) {

        Log.e("hata",error.toException().toString())
    }

})
    }
}



