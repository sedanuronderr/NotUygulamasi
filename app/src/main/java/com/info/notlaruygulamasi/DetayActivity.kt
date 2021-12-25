package com.info.notlaruygulamasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_detay.*
import kotlinx.android.synthetic.main.activity_detay.editTextDers
import kotlinx.android.synthetic.main.activity_detay.editTextNot1
import kotlinx.android.synthetic.main.activity_detay.editTextNot2
import kotlinx.android.synthetic.main.activity_not_kayit.*

class DetayActivity : AppCompatActivity() {
private lateinit var not:Notlar
    lateinit var   database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detay)

        val db = Firebase.database
        database = db.getReference("users")

        toolbarNotDetay.title = "Not Detay"
        setSupportActionBar(toolbarNotDetay)

        not = intent.getSerializableExtra("nesne") as Notlar

        editTextDers.setText(not.ders_adi)
        editTextNot1.setText((not.not1).toString())
        editTextNot2.setText((not.not2).toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.action_sil -> {
                Snackbar.make(toolbarNotDetay,"Silinsin mi?",Snackbar.LENGTH_SHORT)
                    .setAction("EVET"){
                        database.child(not.not_id!!).removeValue()
                        startActivity(Intent(this@DetayActivity,MainActivity::class.java))
                        finish()
                    }.show()

                return true
            }
            R.id.action_duzenle -> {
                val ders = editTextDers.text.toString()
                val not1 =editTextNot1.text.toString()
                val not2= editTextNot2.text.toString()

                if(TextUtils.isEmpty(ders)){
                    Snackbar.make(toolbarNotDetay,"Ders adını giriniz",Snackbar.LENGTH_SHORT).show()
                }
                else if(TextUtils.isEmpty(not1)){
                    Snackbar.make(toolbarNotDetay,"Ders not1 giriniz",Snackbar.LENGTH_SHORT).show()
                }
                else if (TextUtils.isEmpty(not2)){
                    Snackbar.make(toolbarNotDetay,"Ders not2 giriniz",Snackbar.LENGTH_SHORT).show()
                }
                else {
                    val bilgiler =HashMap<String,Any>()
                    bilgiler.put("ders_id",ders)
                    bilgiler.put("not1",not1.toInt())
                    bilgiler.put("not2",not2.toInt())
                    database.child(not.not_id!!).updateChildren(bilgiler)
                    startActivity(Intent(this@DetayActivity,MainActivity::class.java))
                    finish()

                }


                }

            }

        return true
    }
}
