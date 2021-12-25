package com.info.notlaruygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_detay.*
import kotlinx.android.synthetic.main.activity_not_kayit.*
import kotlinx.android.synthetic.main.activity_not_kayit.editTextDers
import kotlinx.android.synthetic.main.activity_not_kayit.editTextNot1
import kotlinx.android.synthetic.main.activity_not_kayit.editTextNot2

class NotKayitActivity : AppCompatActivity() {
    var toplam =0

    lateinit var   database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_kayit)

        val db = Firebase.database
        database = db.getReference("users")


        toolbarNotKayit.title = "Not Kayıt"
        setSupportActionBar(toolbarNotKayit)




        buttonKaydet.setOnClickListener {

            val ders = editTextDers.text.toString()
            val not1 =editTextNot1.text.toString()
            val not2= editTextNot2.text.toString()

          if(TextUtils.isEmpty(ders)){
              Snackbar.make(toolbarNotKayit,"Ders adını giriniz",Snackbar.LENGTH_SHORT).show()
          }
           else if(TextUtils.isEmpty(not1)){
                Snackbar.make(toolbarNotKayit,"Ders not1 giriniz",Snackbar.LENGTH_SHORT).show()
            }
         else if (TextUtils.isEmpty(not2)){
                Snackbar.make(toolbarNotKayit,"Ders not2 giriniz",Snackbar.LENGTH_SHORT).show()
            }
           else {
               database.addListenerForSingleValueEvent(object:ValueEventListener{
                   override fun onDataChange(snapshot: DataSnapshot) {
                       var dersVarmi= false
                       if(snapshot.getValue()!= null || snapshot.getValue()== null){
                           for (c in snapshot.children){
                               val okunanders=c.getValue(Notlar::class.java)
                               if(okunanders!!.ders_adi!!.equals(ders,true)){
                                   Toast.makeText(this@NotKayitActivity,"Ders mevcut", Toast.LENGTH_SHORT).show()
                                   dersVarmi =true
                                   break

                               }


                           }
                           if(dersVarmi==false){
                               toplam = (not1.toInt() + not2.toInt()) / 2
                               val notlar = Notlar("", ders, not1.toInt(), not2.toInt(),toplam)
                               database.push().setValue(notlar)
                               startActivity(Intent(this@NotKayitActivity,MainActivity::class.java))
                               finish()
                           }

                       }

                   }

                   override fun onCancelled(error: DatabaseError) {
                       TODO("Not yet implemented")
                   }

               })



          }


        }
    }
}
