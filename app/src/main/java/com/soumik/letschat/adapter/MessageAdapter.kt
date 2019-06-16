package com.soumik.letschat.adapter

import android.app.Activity
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseListAdapter
import com.google.firebase.database.DatabaseReference
import com.soumik.letschat.R
import com.soumik.letschat.activity.MainActivity
import com.soumik.letschat.model.Messages
import kotlinx.android.synthetic.main.item_send_msg.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat

class MessageAdapter(var activity: MainActivity,  modelLayout:Int,
                     modelClass: Class<Messages>?,
                     ref: DatabaseReference?
): FirebaseListAdapter<Messages>(activity, modelClass, modelLayout, ref) {

    override fun populateView(v: View?, model: Messages?, position: Int) {

        val messageBody = v?.tv_msg_body
        val messageUser = v?.tv_user
        val messageTime = v?.tv_message_time

        messageBody?.text = model?.messageBody
        messageUser?.text = model?.messageUser

        var currentDateTime = SimpleDateFormat("dd-MM-yyyy (HH:mm:ss)")

        messageTime?.text = currentDateTime.format(model?.messageTime)

    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
            var cMessages :Messages = getItem(position)
        var v = view

        if (cMessages.messageUserID.equals(activity.getCurrentUser())){
             v = activity.layoutInflater.inflate(R.layout.item_receive_msg,viewGroup,false)
        } else {
             v = activity.layoutInflater.inflate(R.layout.item_send_msg,viewGroup,false)
        }

        populateView(v,cMessages,position)

        return v!!
    }

    override fun getViewTypeCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        return position%2
    }
}