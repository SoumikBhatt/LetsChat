package com.soumik.letschat.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.soumik.letschat.Apputils.showToast
import com.soumik.letschat.R
import com.soumik.letschat.adapter.MessageAdapter
import com.soumik.letschat.model.Messages
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        var SIGN_IN_REQUEST_CODE = 1
    }

    var currentUserName : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if(FirebaseAuth.getInstance().currentUser==null){
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),
                SIGN_IN_REQUEST_CODE
            )
        } else{
            showMessages()
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

    private fun showMessages() {

        currentUserName = FirebaseAuth.getInstance().currentUser!!.uid

        Log.i("11",""+currentUserName)

        var adapter = MessageAdapter(this,R.layout.item_send_msg,Messages::class.java,FirebaseDatabase.getInstance().reference)
        lv_all_messages.adapter = adapter
    }

    fun getCurrentUser():String{

        return currentUserName
    }
}
