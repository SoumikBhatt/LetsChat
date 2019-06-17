package com.soumik.letschat.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.database.FirebaseListAdapter
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.soumik.letschat.Apputils.showToast
import com.soumik.letschat.R
import com.soumik.letschat.model.Messages
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_send_msg.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    companion object{
        var SIGN_IN_REQUEST_CODE = 1
    }

    var currentUserName : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        FirebaseApp.initializeApp(this)

        if(FirebaseAuth.getInstance().currentUser==null){
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),
                SIGN_IN_REQUEST_CODE
            )
        } else{
            showMessages()
        }

        fbtn_fab.setOnClickListener{
            if (TextUtils.isEmpty(et_input.text.toString())){
                showToast(applicationContext,"Please fill the field")
            } else {

                FirebaseDatabase.getInstance()
                    .reference
                    .push()
                    .setValue(
                        Messages(et_input.text.toString(),
                        FirebaseAuth.getInstance().currentUser?.displayName!!,
                        FirebaseAuth.getInstance().currentUser?.uid!!)
                    )
                et_input.setText("")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== SIGN_IN_REQUEST_CODE){
            if (resultCode== Activity.RESULT_OK){
               showToast(applicationContext,"Signed In Successfully!")
                showMessages()
            } else{
                showToast(applicationContext,"Sign In Failed!")
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_sign_out,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId==R.id.menu_sout){

            AuthUI.getInstance().signOut(this).addOnCompleteListener{
                showToast(applicationContext,"Signed Out Successfully")
                finish()
            }
        }

        return true
    }

    private fun showMessages() {

        currentUserName = FirebaseAuth.getInstance().currentUser?.displayName!!
        var image = FirebaseAuth.getInstance().currentUser?.photoUrl


        Log.i("AAASS",""+currentUserName)
        Log.i("AAAASSS",""+image)

        var adapter = object:  FirebaseListAdapter<Messages>(this,Messages::class.java,R.layout.item_send_msg,FirebaseDatabase.getInstance().reference){
            override fun populateView(v: View?, model: Messages?, position: Int) {
                val messageBody : TextView =  v?.findViewById(R.id.tv_msg_body)!!
                val messageUser = v?.tv_user
                val messageTime = v?.tv_message_time

                messageBody?.text = model?.messageBody
                messageUser?.text = model?.messageUser

                Log.i("AAASS",""+messageBody)

                messageTime?.text = android.text.format.DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model?.messageTime!!)
            }
        }
        lv_all_messages.adapter = adapter
    }

//    fun getCurrentUser():String{
//
//        return currentUserName
//    }
}
