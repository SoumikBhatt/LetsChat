package com.soumik.letschat

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.soumik.letschat.Apputils.showToast

class MainActivity : AppCompatActivity() {

    companion object{
        var SIGN_IN_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if(FirebaseAuth.getInstance().currentUser==null){
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE)
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

    }
}
